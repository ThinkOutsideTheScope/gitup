package com.chickenmc;

import net.minecraft.server.MinecraftServer;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public class Saver {
    public void doServerBackup(MinecraftServer server) {
        Gitup.LOGGER.info("Attempting to start a server backup");
        GitupConfig config = Gitup.CONFIG;
        if (server != null) {
            CompletableFuture<Boolean> serverSavedFuture = Util.saveServer(server);
            boolean serverSaved = serverSavedFuture.join();
            if (config.requireLevelSavedForCommit && !serverSaved) return;
        }
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
            boolean changesMade = (GitCommand.diff_index("HEAD", config.extraArgs.diff_index) == 1);
            if (changesMade) {
                status = GitCommand.commit(Gitup.commitFormatter.format(LocalDateTime.now()), config.extraArgs.commit);
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
        } catch (DateTimeException e) {
            Gitup.LOGGER.error("Parsing commit message format failed with a DateTimeException", e);
            return;
        }
    }
    public void serverBackupCheckTick(MinecraftServer server) {
        long currentTime = Instant.now().getEpochSecond();
        if (Gitup.CONFIG.lastPushTimestamp == 0L) {
            Gitup.CONFIG.lastPushTimestamp = currentTime;
            return;
        }
        if (currentTime >= Gitup.NEXT_SAVE_TIMESTAMP) {
            Gitup.CONFIG.lastPushTimestamp = Gitup.NEXT_SAVE_TIMESTAMP;
            Gitup.NEXT_SAVE_TIMESTAMP += Gitup.CONFIG_PUSH_INTERVAL_S;
            doServerBackup(server);
        }
    }
}
