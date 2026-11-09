package net.chickenmc.gitup;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import static net.chickenmc.gitup.Constants.MOD_ID;

public class Gitup implements ModInitializer {
	@Override
	public void onInitialize() {
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
