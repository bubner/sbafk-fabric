package me.bubner.sbafk.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.scores.*;

public class Utils {
    /**
     * Send a formatted message to the player.
     */
    public static void sendMsg(String message) {
        Component text = Component.literal("")
                .append(Component.literal("[").withStyle(ChatFormatting.DARK_RED))
                .append(Component.literal("sbafk").withStyle(ChatFormatting.RED))
                .append(Component.literal("] ").withStyle(ChatFormatting.DARK_RED))
                .append(Component.literal(message).withStyle(ChatFormatting.GRAY));

        Minecraft.getInstance().gui.getChat().addMessage(text);
    }

    public static boolean isInSkyblock() {
        try {
            if (Minecraft.getInstance().level == null) return false;
            Objective obj = Minecraft.getInstance().level.getScoreboard().getDisplayObjective(DisplaySlot.SIDEBAR);
            return obj != null && obj.getDisplayName().getString().contains("SKYBLOCK");
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static boolean isOnPrivateIsland() {
        try {
            if (Minecraft.getInstance().level == null) return false;
            Scoreboard sc = Minecraft.getInstance().level.getScoreboard();
            Objective sidebar = sc.getDisplayObjective(DisplaySlot.SIDEBAR);
            if (sidebar == null) return false;
            for (PlayerScoreEntry line : sc.listPlayerScores(sidebar)) {
                Component playerName = line.ownerName();
                PlayerTeam team = sc.getPlayersTeam(playerName.toString());
                if (PlayerTeam.formatNameForTeam(team, playerName).toString().trim().contains("Your Isla")) {
                    return true;
                }
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }
}
