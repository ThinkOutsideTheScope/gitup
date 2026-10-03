package com.chickenmc;

import net.minecraft.server.MinecraftServer;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Saver {
    private long lastExecutionTime = 0L;
    public void doServerBackup(MinecraftServer server) {
        GitupConfig config = Gitup.CONFIG;
        CompletableFuture<Boolean> serverSavedFuture = Util.saveServer(server);
        boolean serverSaved = serverSavedFuture.join();
        if (config.requireLevelSavedForCommit && !serverSaved) return;
        try {
            int status = GitCommand.fetch(config.extraArgs.fetch);
            if (status != 0) {
                Gitup.LOGGER.error("'git fetch' exited with error code {}. Treating this as non-fatal", status);
            }
            status = GitCommand.add(Gitup.GAME_DIR_FILE.toString(), config.extraArgs.add);
            if (status != 0) {
                Gitup.LOGGER.error("'git add' exited with error code {}. Not backing up.", status);
                return;
            }
            boolean changes_made = (GitCommand.diff_index("HEAD", config.extraArgs.diff_index) == 1);
            if (changes_made) {
                status = GitCommand.commit(CommitMessageFormatter.format(config.commitMessageFormat), config.extraArgs.commit);
                if (status != 0) {
                    Gitup.LOGGER.error("'git commit' exited with error code {}. Not backing up.", status);
                    return;
                }
                status = GitCommand.push(config.extraArgs.push);
                if (status != 0) {
                    Gitup.LOGGER.error("'git push' exited with error code {}. Changes might not have been pushed to remote.", status);
                    return;
                }
            }
        } catch (IOException e) {
            Gitup.LOGGER.error("Git command failed with IOException", e);
            return;
        }
    }
    public void serverBackupCheckTick(MinecraftServer server) {
        long currentTime = net.minecraft.util.Util.getMillis();
        if (lastExecutionTime == 0L) {
            lastExecutionTime = currentTime;
            return;
        }
        if (currentTime - lastExecutionTime >= Gitup.CONFIG_PUSH_INTERVAL_MS) {
            lastExecutionTime += Gitup.CONFIG_PUSH_INTERVAL_MS;
            doServerBackup(server);
        }
    }
}
