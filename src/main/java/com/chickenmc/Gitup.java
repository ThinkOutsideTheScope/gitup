package com.chickenmc;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;

public class Gitup implements ModInitializer {
	public static final String MOD_ID = "gitup";
	public static final Path GAME_DIR = FabricLoader.getInstance().getGameDir();
	public static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir();

	public static final File GAME_DIR_FILE = GAME_DIR.toFile();
	public static final File CONFIG_DIR_FILE = CONFIG_DIR.toFile();

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static GitupConfig CONFIG;
	public static long CONFIG_PUSH_INTERVAL_MS = 0L;

	public Saver saver = new Saver();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		AutoConfig.register(GitupConfig.class, GsonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(GitupConfig.class).getConfig();
		try {
			Util.verifyConfig(CONFIG);
		} catch (IllegalArgumentException e) {
			LOGGER.error("Config is invalid", e);
		}
		CONFIG_PUSH_INTERVAL_MS = Util.convertToMs(CONFIG.pushInterval, CONFIG.pushIntervalUnit);
		if (CONFIG.pushInterval != 0L) ServerTickEvents.END_SERVER_TICK.register(saver::serverBackupCheckTick); // no need to register if saving is disabled
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
