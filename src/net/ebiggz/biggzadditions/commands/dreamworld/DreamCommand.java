package net.ebiggz.biggzadditions.commands.dreamworld;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Conditions;
import co.aikar.commands.annotation.Default;
import net.ebiggz.biggzadditions.BiggzAdditions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

@CommandAlias("dream")
public class DreamCommand extends BaseCommand {

	@Default
	@Conditions("mainworld")
	public static void onDream(Player player) {
		schedulePendingDreamer(player);
		player.sendMessage(ChatColor.AQUA + "" + ChatColor.ITALIC + "Interact with a bed to begin dreaming...");
	}

	private static void schedulePendingDreamer(Player player) {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		if(BiggzAdditions.pendingDreamers.containsKey(player)) {
			int taskToCancel = BiggzAdditions.pendingDreamers.remove(player);
			scheduler.cancelTask(taskToCancel);
		}
		
		int taskId = scheduler.scheduleSyncDelayedTask(BiggzAdditions.getPlugin(), () -> {
			if(BiggzAdditions.pendingDreamers.containsKey(player)) {
				BiggzAdditions.pendingDreamers.remove(player);
				player.sendMessage(ChatColor.AQUA + "Dreaming canceled. Type " +
						ChatColor.YELLOW + "/dream" +
						ChatColor.AQUA + " again to dream.");
			}
		}, 100L);
		
		BiggzAdditions.pendingDreamers.put(player, taskId);
	}
}
