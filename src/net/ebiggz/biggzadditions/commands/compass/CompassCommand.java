package net.ebiggz.biggzadditions.commands.compass;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import net.ebiggz.biggzadditions.BiggzAdditions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("c")
public class CompassCommand extends BaseCommand {

	@Default
	public static void compass(Player player) {
		if(BiggzAdditions.compassUsers.contains(player.getUniqueId())) {
			BiggzAdditions.compassUsers.remove(player.getUniqueId());
			player.sendMessage(ChatColor.AQUA + "Compass HUD disabled.");
		} else {
			BiggzAdditions.compassUsers.add(player.getUniqueId());
			player.sendMessage(ChatColor.AQUA + "Compass HUD enabled.");
		}
	}
}
