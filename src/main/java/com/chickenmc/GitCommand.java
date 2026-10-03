package com.chickenmc;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class GitCommand {
    public static final Duration TIMEOUT = Duration.ofMinutes(10);
    public static int fetch(String extraArgs, File workingDirectory) throws IOException {
        return Util.execShellCommand(((extraArgs == null) ? "git fetch" : "git fetch" + extraArgs), TIMEOUT, workingDirectory);
    }
    public static int pull(String extraArgs, File workingDirectory) throws IOException {
        return Util.execShellCommand(((extraArgs == null) ? "git pull" : "git pull " + extraArgs), TIMEOUT, workingDirectory);
    }
    public static int add(String inputParam, String extraArgs, File workingDirectory) throws IOException {
        return Util.execShellCommand(((extraArgs == null) ? "git add " + inputParam : "git add " + inputParam + " " + extraArgs), TIMEOUT, workingDirectory);
    }
    public static int commit(String message, String extraArgs, File workingDirectory) throws IOException {
        return Util.execShellCommand(((extraArgs == null) ? "git commit -m \"" + message + "\"" : "git commit -m \"" + message + "\" " + extraArgs), TIMEOUT, workingDirectory);
    }
    public static int push(String extraArgs, File workingDirectory) throws IOException {
        return Util.execShellCommand(((extraArgs == null) ? "git push" : "git push " + extraArgs), TIMEOUT, workingDirectory);
    }
    public static int diff_index(String commit, String extraArgs, File workingDirectory) throws IOException {
        return Util.execShellCommand(((extraArgs == null) ? "git diff-index --quiet " + commit : "git diff-index --quiet " + commit + " " + extraArgs));
    }

    public static int fetch() throws IOException {
        return fetch(null, Gitup.GAME_DIR_FILE);
    }
    public static int pull() throws IOException {
        return pull(null, Gitup.GAME_DIR_FILE);
    }
    public static int add(String inputParam) throws IOException {
        return add(inputParam, null, Gitup.GAME_DIR_FILE);
    }
    public static int commit(String message) throws IOException {
        return commit(message, null, Gitup.GAME_DIR_FILE);
    }
    public static int push() throws IOException {
        return push(null, Gitup.GAME_DIR_FILE);
    }
    public static int diff_index(String commit) throws IOException {
        return diff_index(commit, null, Gitup.GAME_DIR_FILE);
    }

    public static int fetch(String extraArgs) throws IOException {
        return fetch(extraArgs, Gitup.GAME_DIR_FILE);
    }
    public static int pull(String extraArgs) throws IOException {
        return pull(extraArgs, Gitup.GAME_DIR_FILE);
    }
    public static int add(String inputParam, String extraArgs) throws IOException {
        return add(inputParam, extraArgs, Gitup.GAME_DIR_FILE);
    }
    public static int commit(String message, String extraArgs) throws IOException {
        return commit(message, extraArgs, Gitup.GAME_DIR_FILE);
    }
    public static int push(String extraArgs) throws IOException {
        return push(extraArgs, Gitup.GAME_DIR_FILE);
    }
    public static int diff_index(String commit, String extraArgs) throws IOException {
        return diff_index(commit, extraArgs, Gitup.GAME_DIR_FILE);
    }
}
