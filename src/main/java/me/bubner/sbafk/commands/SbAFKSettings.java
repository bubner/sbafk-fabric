package me.bubner.sbafk.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.bubner.sbafk.utils.ModConfig;
import me.bubner.sbafk.utils.Utils;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

/**
 * Commands for SbAFK
 * /sbafk <active | wh | discordid | identifier | maxtries> <value | clear>
 */
public class SbAFKSettings {
    private static final String USE_COMMAND =
            "/sbafk <active | invasive | wh | discordid | identifier | maxtries> <value | clear>";

    public static void register(ModConfig config, CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("sbafk")
                .then(literal("debug")
                        .executes(ctx -> {
                            Utils.sendMsg("isOnPrivateIsland: " + Utils.isOnPrivateIsland());
                            Utils.sendMsg("isInSkyblock: " + Utils.isInSkyblock());
                            return 1;
                        })
                )
                .then(literal("active")
                        .executes(ctx -> {
                            Utils.sendMsg("active: " + config.isActive());
                            return 1;
                        })
                        .then(argument("value", BoolArgumentType.bool())
                                .executes(ctx -> {
                                    boolean value = BoolArgumentType.getBool(ctx, "value");
                                    config.properties.setProperty("active", String.valueOf(value));
                                    Utils.sendMsg("Set active to " + value + "!");
                                    config.save();
                                    return 1;
                                })
                        )
                        .then(literal("clear")
                                .executes(ctx -> {
                                    config.properties.setProperty("active", "true");
                                    Utils.sendMsg("Reset active!");
                                    config.save();
                                    return 1;
                                })
                        )
                )
                .then(literal("invasive")
                        .executes(ctx -> {
                            Utils.sendMsg("invasive: " + config.isInvasive());
                            return 1;
                        })
                        .then(argument("value", BoolArgumentType.bool())
                                .executes(ctx -> {
                                    boolean value = BoolArgumentType.getBool(ctx, "value");
                                    config.properties.setProperty("invasive", String.valueOf(value));
                                    Utils.sendMsg("Set invasive to " + value + "!");
                                    config.save();
                                    return 1;
                                })
                        )
                        .then(literal("clear")
                                .executes(ctx -> {
                                    config.properties.setProperty("invasive", "true");
                                    Utils.sendMsg("Reset invasive!");
                                    config.save();
                                    return 1;
                                })
                        )
                )
                .then(literal("wh")
                        .executes(ctx -> {
                            Utils.sendMsg("webhook: " + config.getWebhook());
                            return 1;
                        })
                        .then(argument("value", StringArgumentType.greedyString())
                                .executes(ctx -> {
                                    String value = StringArgumentType.getString(ctx, "value");
                                    // Legacy: chat character limit so we append the webhook url before if not given
                                    String webhook = value.startsWith("https://discord.com/api/webhooks/")
                                            ? value
                                            : "https://discord.com/api/webhooks/" + value;

                                    config.properties.setProperty("webhook", webhook);
                                    Utils.sendMsg("Set webhook!");
                                    config.save();
                                    return 1;
                                })
                        )
                        .then(literal("clear")
                                .executes(ctx -> {
                                    config.properties.setProperty("webhook", "");
                                    Utils.sendMsg("Reset webhook!");
                                    config.save();
                                    return 1;
                                })
                        )
                )
                .then(literal("discordid")
                        .executes(ctx -> {
                            Utils.sendMsg("discordid: " + config.getDiscordId());
                            return 1;
                        })
                        .then(argument("value", StringArgumentType.string())
                                .executes(ctx -> {
                                    String value = StringArgumentType.getString(ctx, "value");
                                    config.properties.setProperty("discordid", value);
                                    Utils.sendMsg("Set discordid to " + value + "!");
                                    config.save();
                                    return 1;
                                })
                        )
                        .then(literal("clear")
                                .executes(ctx -> {
                                    config.properties.setProperty("discordid", "");
                                    Utils.sendMsg("Reset discordid!");
                                    config.save();
                                    return 1;
                                })
                        )
                )
                .then(literal("identifier")
                        .executes(ctx -> {
                            Utils.sendMsg("identifier: " + config.getIdentifier());
                            return 1;
                        })
                        .then(argument("value", StringArgumentType.string())
                                .executes(ctx -> {
                                    String value = StringArgumentType.getString(ctx, "value");
                                    config.properties.setProperty("identifier", value);
                                    Utils.sendMsg("Set identifier to " + value + "!");
                                    config.save();
                                    return 1;
                                })
                        )
                        .then(literal("clear")
                                .executes(ctx -> {
                                    config.properties.setProperty("identifier", "");
                                    Utils.sendMsg("Reset identifier!");
                                    config.save();
                                    return 1;
                                })
                        )
                )
                .then(literal("maxtries")
                        .executes(ctx -> {
                            Utils.sendMsg("maxtries: " + config.getMaxTries());
                            return 1;
                        })
                        .then(argument("value", IntegerArgumentType.integer(0))
                                .executes(ctx -> {
                                    int value = IntegerArgumentType.getInteger(ctx, "value");
                                    config.properties.setProperty("maxtries", String.valueOf(value));
                                    Utils.sendMsg("Set maxtries to " + value + "!");
                                    config.save();
                                    return 1;
                                })
                        )
                        .then(literal("clear")
                                .executes(ctx -> {
                                    config.properties.setProperty("maxtries", "2");
                                    Utils.sendMsg("Reset maxtries!");
                                    config.save();
                                    return 1;
                                })
                        )
                )
                .executes(ctx -> {
                    Utils.sendMsg(USE_COMMAND);
                    return 1;
                })
        );
    }
}

