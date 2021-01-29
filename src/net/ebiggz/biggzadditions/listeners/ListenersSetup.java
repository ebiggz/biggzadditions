package net.ebiggz.biggzadditions.listeners;

import net.ebiggz.biggzadditions.listeners.misc.DreamworldListener;
import net.ebiggz.biggzadditions.listeners.misc.ResidenceListener;
import net.ebiggz.biggzadditions.listeners.misc.WeatherListener;
import net.ebiggz.biggzadditions.listeners.player.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class ListenersSetup {
    public static void setupListeners(PluginManager pluginManager, Plugin plugin) {
        pluginManager.registerEvents(new WeatherListener(), plugin);
        pluginManager.registerEvents(new DreamworldListener(), plugin);
        pluginManager.registerEvents(new ResidenceListener(), plugin);
        pluginManager.registerEvents(new PlayerChatListener(), plugin);
        pluginManager.registerEvents(new PlayerDeathListener(), plugin);
        pluginManager.registerEvents(new PlayerEditBookListener(), plugin);
        pluginManager.registerEvents(new PlayerInteractListener(), plugin);
        pluginManager.registerEvents(new PlayerJoinListener(), plugin);
        pluginManager.registerEvents(new PlayerMoveListener(), plugin);
        pluginManager.registerEvents(new PlayerSleepListener(), plugin);
        pluginManager.registerEvents(new PlayerTeleportListener(), plugin);
    }
}
