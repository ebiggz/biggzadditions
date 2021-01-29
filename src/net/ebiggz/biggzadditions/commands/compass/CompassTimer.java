package net.ebiggz.biggzadditions.commands.compass;

import net.ebiggz.biggzadditions.BiggzAdditions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class CompassTimer {
	
	public static void run(final Plugin plugin) {

		Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
			public void run() {
				
				Bukkit.getOnlinePlayers().stream().forEach(p -> {
					if(!BiggzAdditions.compassUsers.contains(p.getUniqueId())) return;
					
					Location l = p.getLocation();
				
					
					p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
							(new ComponentBuilder(l.getBlockX() + " "
									+ l.getBlockY() + " " + l.getBlockZ() + " " + ChatColor.GOLD
									+ getPlayerDirection(p)).create()));
					
				});
			}
		}, 0L, 10L);
	}
	
	private static String getPlayerDirection(Player playerSelf) {
	    String dir = "";
	    float y = playerSelf.getLocation().getYaw();
	    if (y < 0.0F)
	      y += 360.0F; 
	    y %= 360.0F;
	    int i = (int)((y + 8.0F) / 22.5D);
	    if (i == 4) {
	      dir = "W";
	    } else if (i == 5) {
	      dir = "WNW";
	    } else if (i == 6) {
	      dir = "NW";
	    } else if (i == 7) {
	      dir = "NNW";
	    } else if (i == 8) {
	      dir = "N";
	    } else if (i == 9) {
	      dir = "NNE";
	    } else if (i == 10) {
	      dir = "NE";
	    } else if (i == 11) {
	      dir = "ENE";
	    } else if (i == 12) {
	      dir = "E";
	    } else if (i == 13) {
	      dir = "ESE";
	    } else if (i == 14) {
	      dir = "SE";
	    } else if (i == 15) {
	      dir = "SSE";
	    } else if (i == 0) {
	      dir = "S";
	    } else if (i == 1) {
	      dir = "SSW";
	    } else if (i == 2) {
	      dir = "SW";
	    } else if (i == 3) {
	      dir = "WSW";
	    } else {
	      dir = "S";
	    } 
	    return dir;
	  }
}
