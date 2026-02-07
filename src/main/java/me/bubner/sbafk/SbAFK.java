package me.bubner.sbafk;

import me.bubner.sbafk.commands.SbAFKSettings;
import me.bubner.sbafk.listeners.ChatListener;
import me.bubner.sbafk.listeners.ConnectionListener;
import me.bubner.sbafk.utils.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SbAFK implements ClientModInitializer {
    public static final String MOD_ID = "sbafk";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        System.out.println("Lachlan Paul CSS");
        final ModConfig MOD_CONFIG = new ModConfig();
        MOD_CONFIG.load();
        ClientCommandRegistrationCallback.EVENT.register(((commandDispatcher, registryAccess) ->
                SbAFKSettings.register(MOD_CONFIG, commandDispatcher)));
        new ChatListener(MOD_CONFIG);
        new ConnectionListener(MOD_CONFIG);
    }
}