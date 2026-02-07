package me.bubner.sbafk.listeners;

import me.bubner.sbafk.actions.SendAlert;
import me.bubner.sbafk.actions.threads.ReconnectAction;
import me.bubner.sbafk.utils.Events;
import me.bubner.sbafk.utils.FlagTrigger;
import me.bubner.sbafk.utils.ModConfig;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

/**
 * Server disconnect detection for kick events.
 */
public class ConnectionListener {
    private ServerData lastConnected;

    public ConnectionListener(ModConfig config) {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            this.lastConnected = Minecraft.getInstance().getCurrentServer();
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            if (!config.isActive()) {
                return;
            }

            if (lastConnected != null) {
                if (config.isInvasive()) {
                    new ReconnectAction(lastConnected, config).start();
                } else {
                    FlagTrigger nonInvasiveTrigger = new FlagTrigger("<account disconnect detection>", null);
                    nonInvasiveTrigger.setSuccess(false);
                    new SendAlert(config, nonInvasiveTrigger, Events.AlertPriority.HIGH).start();
                }
            }
        });
    }
}
