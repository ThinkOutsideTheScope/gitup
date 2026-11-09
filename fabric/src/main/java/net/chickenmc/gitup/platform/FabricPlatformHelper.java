package net.chickenmc.gitup.platform;

import net.chickenmc.gitup.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;

import java.nio.file.Path;
import java.util.function.Consumer;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
    @Override
    public Path getGameDir() {
        return FabricLoader.getInstance().getGameDir();
    }
    @Override
    public Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public void registerServerEndTickEvent(Consumer<MinecraftServer> event) {
        ServerTickEvents.END_SERVER_TICK.register(event::accept);
    }
    @Override
    public void registerServerStoppingEvent(Consumer<MinecraftServer> event) {
        ServerLifecycleEvents.SERVER_STOPPING.register(event::accept);
    }
}
