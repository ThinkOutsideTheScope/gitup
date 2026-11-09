package net.chickenmc.gitup;

import net.chickenmc.gitup.platform.Services;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as NeoForge events
// however it will be compatible with all supported mod loaders.
public class CommonClass {

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {

        Constants.LOG.info("Hello from Common init on {}! we are currently in a {} environment!", Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
        Constants.LOG.info("The ID for diamonds is {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND));

        // It is common for all supported loaders to provide a similar feature that can not be used directly in the
        // common code. A popular way to get around this is using Java's built-in service loader feature to create
        // your own abstraction layer. You can learn more about this in our provided services class. In this example
        // we have an interface in the common code and use a loader specific implementation to delegate our call to
        // the platform specific approach.
        if (Services.PLATFORM.isModLoaded("examplemod")) {

            Constants.LOG.info("Hello to examplemod");
        }

        AutoConfig.register(GitupConfig.class, GsonConfigSerializer::new);
        Globals.CONFIG = AutoConfig.getConfigHolder(GitupConfig.class).getConfig();
        try {
            Util.verifyConfig(Globals.CONFIG);
        } catch (IllegalArgumentException e) {
            Constants.LOG.error("Config is invalid", e);
        }
        Globals.CONFIG_PUSH_INTERVAL_S = Util.convertToSeconds(Globals.CONFIG.pushInterval, Globals.CONFIG.pushIntervalUnit);
        if (Globals.CONFIG.pushInterval != 0L) Services.PLATFORM.registerServerEndTickEvent(Globals.saver::serverBackupCheckTick); // no need to register if saving is disabled
        try {
            Globals.commitFormatter = DateTimeFormatter.ofPattern(Globals.CONFIG.commitMessageFormat).withZone(ZoneId.systemDefault());
        } catch (DateTimeException e) {
            Constants.LOG.error("commitMessageFormat was invalid. Falling back to default commit message format", e);
            Globals.commitFormatter = DateTimeFormatter.ofPattern(Constants.DEFAULT_COMMIT_FORMAT).withZone(ZoneId.systemDefault());
        }
        Globals.NEXT_SAVE_TIMESTAMP = (Globals.CONFIG.lastPushTimestamp == 0 ? Instant.now().getEpochSecond() + Globals.CONFIG_PUSH_INTERVAL_S : Globals.CONFIG.lastPushTimestamp + Globals.CONFIG_PUSH_INTERVAL_S);

        Services.PLATFORM.registerServerStoppingEvent(_ -> {
            AutoConfig.getConfigHolder(GitupConfig.class).save();
        });
    }
}