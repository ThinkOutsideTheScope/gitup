package net.chickenmc.gitup.platform;

import net.chickenmc.gitup.platform.services.IPlatformHelper;
import net.minecraft.server.MinecraftServer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.nio.file.Path;
import java.util.function.Consumer;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.getCurrent().isProduction();
    }
    @Override
    public Path getGameDir() {
        return FMLPaths.GAMEDIR.get();
    }
    @Override
    public Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public void registerServerEndTickEvent(Consumer<MinecraftServer> event) {
        NeoForge.EVENT_BUS.addListener((ServerTickEvent.Post tickEvent) -> {
            event.accept(tickEvent.getServer());
        });
    }
    @Override
    public void registerServerStoppingEvent(Consumer<MinecraftServer> event) {
        NeoForge.EVENT_BUS.addListener((ServerStoppingEvent stopEvent) -> {
            event.accept(stopEvent.getServer());
        });
    }
}