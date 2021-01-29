package net.ebiggz.biggzadditions.commands.dreamworld;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Conditions;
import co.aikar.commands.annotation.Default;
import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.constants.WorldName;
import net.ebiggz.biggzadditions.players.BPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

@CommandAlias("wake")
public class WakeupCommand extends BaseCommand {

	@Default
	@Conditions("dreamworld")
	public static void onCommand(Player player) {

		player.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Waking up...");

		BPlayer bPlayer = BiggzAdditions.getBPlayerManager().getBPlayer(player.getName());

		Location teleportLocation = bPlayer.getDreamBedLoc();

		// if bed location isn't saved, use main spawn
		if(teleportLocation == null) {
			teleportLocation = Bukkit.getWorld(WorldName.MAIN_WORLD).getSpawnLocation();
		}

		player.teleport(teleportLocation, TeleportCause.PLUGIN);
	}
}
