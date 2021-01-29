package net.ebiggz.biggzadditions.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.logging.Level;

public class Config {

    private Plugin plugin;

    private final String fileName;
    private File configFile;
    private FileConfiguration fileConfiguration;
    private String folderPath;

    public Config(Plugin plugin, String fileName) {
        if (plugin == null)
            throw new IllegalArgumentException("plugin cannot be null");

        this.plugin = plugin;

        this.fileName = fileName;

        this.folderPath = plugin.getDataFolder().getAbsolutePath();
        this.configFile = new File(folderPath + File.separator + fileName);
    }

    public void reload() {
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

        // Look for defaults in the jar
        InputStream defaultConfigStream = plugin.getResource(fileName);
        if (defaultConfigStream != null) {
            Reader defaultConfigReader = new InputStreamReader(defaultConfigStream);
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defaultConfigReader);
            fileConfiguration.setDefaults(defConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (fileConfiguration == null) {
            this.reload();
        }
        return fileConfiguration;
    }

    public void save() {
        if (fileConfiguration == null || configFile == null) {
            return;
        } else {
            try {
                getConfig().save(configFile);
            } catch (IOException ex) {
                plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
            }
        }
    }

    public void saveDefault() {
        if (!configFile.exists()) {
            this.plugin.saveResource(fileName, false);
        }
    }
}