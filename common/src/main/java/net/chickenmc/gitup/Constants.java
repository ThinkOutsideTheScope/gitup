package net.chickenmc.gitup;

import net.chickenmc.gitup.platform.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;

public class Constants {

    public static final String MOD_ID = "gitup";
    public static final String MOD_NAME = "Gitup";
    public static final String DEFAULT_COMMIT_FORMAT = "yyyy-MM-dd hh:mm:ss a z";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final Path GAME_DIR =  Services.PLATFORM.getGameDir();
    public static final Path CONFIG_DIR = Services.PLATFORM.getConfigDir();

    public static final File GAME_DIR_FILE = GAME_DIR.toFile();
    public static final File CONFIG_DIR_FILE = CONFIG_DIR.toFile();
}