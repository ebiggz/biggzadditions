package net.ebiggz.biggzadditions.commands.misc;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.players.BPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CommandAlias("death")
public class LastDeathCommand extends BaseCommand {

	@Default
	public static void onCommand(Player player) {
		BPlayer bPlayer = BiggzAdditions.getBPlayerManager().getBPlayer(player.getName());

		Location lastDeathLocation = bPlayer.getLastDeathLoc();
		if(lastDeathLocation == null) {
			player.sendMessage(ChatColor.YELLOW + "Previous death location not found.");
			return;
		}

		player.sendMessage(ChatColor.UNDERLINE + "Last Death Location:");
		player.sendMessage("X: " + ChatColor.YELLOW + lastDeathLocation.getBlockX());
		player.sendMessage("Y: " + ChatColor.YELLOW + lastDeathLocation.getBlockY());
		player.sendMessage("Z: " + ChatColor.YELLOW + lastDeathLocation.getBlockZ());
		player.sendMessage("World Name: " + ChatColor.YELLOW + lastDeathLocation.getWorld().getName());
	}
}
