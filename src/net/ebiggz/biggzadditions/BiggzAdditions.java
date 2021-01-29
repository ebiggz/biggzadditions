package net.ebiggz.biggzadditions;

import co.aikar.commands.BukkitCommandManager;
import net.ebiggz.biggzadditions.commands.CommandsSetup;
import net.ebiggz.biggzadditions.commands.compass.CompassTimer;
import net.ebiggz.biggzadditions.listeners.ListenersSetup;
import net.ebiggz.biggzadditions.players.BPlayerManager;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class BiggzAdditions extends JavaPlugin {
	
	private static Plugin plugin;
    private static BukkitCommandManager commandManager;
	private static Chat chat = null;
	private static BPlayerManager bPlayerManager;
	
	public static HashMap<Player, Integer> pendingDreamers = new HashMap<Player, Integer>();
	public static ArrayList<UUID> compassUsers = new ArrayList<UUID>();
	public static HashMap<Player, Location> lastTpLocation = new HashMap<Player, Location>();
	public static HashMap<String, UUID> recentResidences = new HashMap<String, UUID>();
	public static boolean spawnFireworksDisabled = false;

	public void onDisable() {
		getLogger().info("Disabled!");
	}

	public void onEnable() {
		plugin = this;

		PluginManager pluginManager = getServer().getPluginManager();
		
		//setup vault
		if(!setupVault()) {
			pluginManager.disablePlugin(this);
			return;
		}

		bPlayerManager = new BPlayerManager();

		commandManager = new BukkitCommandManager(this);
		commandManager.enableUnstableAPI("help");

		CommandsSetup.registerCommandCompletions(commandManager);
		CommandsSetup.registerCommandConditions(commandManager);
		CommandsSetup.registerCommands(commandManager);

		ListenersSetup.setupListeners(pluginManager, this);

		CompassTimer.run(this);
	}

	public static Plugin getPlugin() {
		return plugin;
	}
	
	public static Chat getChatAPI() {
		return chat;
	}
	
	public static BPlayerManager getBPlayerManager() {
		return bPlayerManager;
	}
	
	public static BukkitCommandManager getCommandManager() {
		return commandManager;
	}
	
	private boolean setupVault() {
		Plugin vault =  getServer().getPluginManager().getPlugin("Vault");
		
		// check that vault exists	
		if(vault == null) {
			getLogger().severe("Vault plugin not found!");
			return false;
		}
		
		getLogger().info("Hooked into Vault v" + vault.getDescription().getVersion());
		
		// check for a plugin to handle chat
		if(!setupChat()) { 
			getLogger().severe("No chat plugin to handle prefix/suffix!");
			return false;
		}
		
		return true;
	}

	private boolean setupChat()
	{
		RegisteredServiceProvider<Chat> chatProvider = 
				getServer()
				.getServicesManager()
				.getRegistration(Chat.class);
		
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}
		
		return (chat != null);
	}
}
