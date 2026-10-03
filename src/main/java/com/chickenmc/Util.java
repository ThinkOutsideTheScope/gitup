package com.chickenmc;

import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Util {
    public static CompletableFuture<Boolean> saveServer(MinecraftServer server) {
        return server.submit(() -> server.saveAllChunks(false, true, true));
    }
    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
    public static int execShellCommand(String cmd, Duration timeout, File workingDirectory) throws IOException {
        ProcessBuilder pb;
        Process p;
        if (isWindows()) {
            pb = new ProcessBuilder(new String[]{"cmd.exe", "/c", cmd});
        } else {
            pb = new ProcessBuilder(new String[]{"sh", "-c", cmd});
        }
        if (workingDirectory != null) {
            pb.directory(workingDirectory);
        }
        p = pb.start();
        int exitCode;
        try {
            if (timeout.isNegative()) {
                p.waitFor();
            } else {
                boolean completedInTime = p.waitFor(timeout);
                if (!completedInTime) p.destroyForcibly();
            }
            exitCode = p.exitValue();
        } catch (InterruptedException e) {
            p.destroyForcibly();
            exitCode = p.exitValue();
            Thread.currentThread().interrupt();
        }
        return exitCode;
    }
    public static int execShellCommand(String cmd, File workingDirectory) throws IOException {
        return execShellCommand(cmd, Duration.ofSeconds(-1), workingDirectory);
    }
    public static int execShellCommand(String cmd) throws IOException {
        return execShellCommand(cmd, Duration.ofSeconds(-1), null);
    }
    public static int execShellCommand(String cmd, Duration duration) throws IOException {
        return execShellCommand(cmd, duration, null);
    }
    private static boolean verifyConfigPushInterval(GitupConfig cfg) {
        return switch (cfg.pushIntervalUnit) {
            case SECONDS -> true;
            case MINUTES -> (cfg.pushInterval < 153722867280912L);
            case HOURS   -> (cfg.pushInterval < 2562047788015L);
            case DAYS    -> (cfg.pushInterval < 106751991167L);
            case WEEKS   -> (cfg.pushInterval < 15250284452L);
            case YEARS   -> (cfg.pushInterval < 292277024L);
        };
    }
    public static void verifyConfig(GitupConfig cfg) throws IllegalArgumentException {
        boolean configPushIntervalIsValue = verifyConfigPushInterval(cfg);
        if (!configPushIntervalIsValue) throw new IllegalArgumentException("Error in Gitup config: pushInterval exceeds the maximum value for its unit");
    }
    public static long convertToMs(long pushInterval, GitupConfig.DurationType durationType) {
        return switch (durationType) {
            case SECONDS -> (pushInterval * 1000L);
            case MINUTES -> (pushInterval * 60000L);
            case HOURS   -> (pushInterval * 3600000L);
            case DAYS    -> (pushInterval * 86400000L);
            case WEEKS   -> (pushInterval * 604800000L);
            case YEARS   -> (pushInterval * 31556952000L);
        };
    }
}
