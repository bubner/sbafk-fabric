package me.bubner.sbafk.utils;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static com.mojang.text2speech.Narrator.LOGGER;

/**
 * Utility getter/setter class for mod configuration.
 */
public class ModConfig {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("sbafk.properties");
    public final Properties properties = new Properties();

    public void load() {
        if (Files.exists(CONFIG_PATH)) {
            try (var reader = Files.newBufferedReader(CONFIG_PATH)) {
                properties.load(reader);
            } catch (IOException e) {
                LOGGER.error("Failed to load config", e);
            }
        } else {
            save();
        }
    }

    public void save() {
        try (var writer = Files.newBufferedWriter(CONFIG_PATH)) {
            properties.store(writer, "SbAFK configuration");
        } catch (IOException e) {
            LOGGER.error("Failed to save config", e);
        }
    }

    public boolean isActive() {
        return Boolean.parseBoolean(properties.getProperty("active", "false"));
    }

    public boolean isInvasive() {
        return Boolean.parseBoolean(properties.getProperty("invasive", "true"));
    }

    public String getWebhook() {
        return properties.getProperty("webhook", "");
    }

    public int getMaxTries() {
        return Integer.parseInt(properties.getProperty("maxtries", "2"));
    }

    public String getDiscordId() {
        return properties.getProperty("discordid", "");
    }

    public String getIdentifier() {
        return properties.getProperty("identifier", "");
    }
}
