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
            Scoreboard scoreboard = Minecraft.getInstance().level.getScoreboard();
            Objective objective = scoreboard.getDisplayObjective(DisplaySlot.SIDEBAR);
            if (objective == null) return false;
            var scores = scoreboard.listPlayerScores(objective);
            for (PlayerScoreEntry score : scores) {
                var team = scoreboard.getPlayersTeam(score.owner());
                if (team == null) continue;
                // Prefix will only contain a certain amount of the phrase Your Island (previously separated by a
                // soccer ball for whatever reason)
                if (team.getPlayerPrefix().getString().contains("Your Isla"))
                    return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }
}
