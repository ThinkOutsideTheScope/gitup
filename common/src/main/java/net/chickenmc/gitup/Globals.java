package net.chickenmc.gitup;

import java.time.format.DateTimeFormatter;

public class Globals {
    public static DateTimeFormatter commitFormatter;
    public static GitupConfig CONFIG;
    public static Saver saver = new Saver();

    public static long CONFIG_PUSH_INTERVAL_S = 0L;
    public static long NEXT_SAVE_TIMESTAMP = 0L;
}
