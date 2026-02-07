package me.bubner.sbafk.listeners;

import me.bubner.sbafk.actions.CommandAction;
import me.bubner.sbafk.utils.Events;
import me.bubner.sbafk.utils.FlagTrigger;
import me.bubner.sbafk.utils.ModConfig;
import me.bubner.sbafk.utils.Utils;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

/**
 * Chat detection for kick events.
 */
public class ChatListener {
    public ChatListener(ModConfig config) {
        ClientReceiveMessageEvents.CHAT.register((message, signedMessage, sender, params, receptionTimestamp) -> {
            if (!config.isActive()) {
                return;
            }

            String msg = message.getString().toLowerCase();
            for (String key : Events.FLAGGED_MSGS.keySet()) {
                if (msg.contains(key)) {
                    FlagTrigger trigger = new FlagTrigger(msg, key);
                    CommandAction.runRecovery(config, trigger, Events.FLAGGED_MSGS.get(key), false);

                    if (config.getWebhook().isEmpty()) {
                        Utils.sendMsg("Cannot notify! Webhook is not set! Set it with /sbafk wh <webhook link>");
                    }
                    return;
                }
            }
        });
    }
}
