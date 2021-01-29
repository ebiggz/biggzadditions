package net.ebiggz.biggzadditions.commands.dreamworld;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Conditions;
import co.aikar.commands.annotation.Default;
import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.constants.WorldName;
import net.ebiggz.biggzadditions.players.BPlayer;
import net.ebiggz.biggzadditions.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@CommandAlias("wakeup")
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

		final Location tpTarget = teleportLocation;

		LivingEntity entity = player;

		Utils.blindPlayer(player, 2);

		Bukkit.getScheduler().runTaskLater(BiggzAdditions.getPlugin(), () -> {
			player.teleport(tpTarget, TeleportCause.PLUGIN);
			Utils.blindPlayer(player, 1);
		}, 20);

	}
}
