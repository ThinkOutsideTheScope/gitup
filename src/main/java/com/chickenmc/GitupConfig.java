package com.chickenmc;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "gitup")
public class GitupConfig implements ConfigData {
    public static class ExtraArgsConfig {
        public String fetch      =  "";
        public String pull       =  "";
        public String add        =  "";
        public String commit     = "";
        public String push       = "";
        public String diff_index = "";
    }

    @ConfigEntry.Gui.CollapsibleObject
    public ExtraArgsConfig extraArgs = new ExtraArgsConfig();

    @ConfigEntry.BoundedDiscrete(min = 0L, max = 9223372036854775807L)
    public long pushInterval = 0L; // 0 = disable

    public enum DurationType {
        SECONDS,
        MINUTES,
        HOURS,
        DAYS,
        WEEKS,
        YEARS
    }

    public DurationType pushIntervalUnit = DurationType.SECONDS;

    public boolean requireLevelSavedForCommit = false;

    public String commitMessageFormat = Gitup.DEFAULT_COMMIT_FORMAT;

    @ConfigEntry.BoundedDiscrete(min = 0L, max = Long.MAX_VALUE)
    public long lastPushTimestamp = 0L;
}
