package net.ebiggz.biggzadditions.commands.misc;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

@CommandAlias("editsign")
@Description("Lets you edit a sign")
public class EditSignCommand extends BaseCommand {

	@Default
	@CommandCompletion("@range:1-4 @signtext")
	public static void editSign(Player player, int lineNumber, @Default("") String text) {

		Block block = player.getTargetBlock(null, 5);
		if(block == null) {
			player.sendMessage(ChatColor.RED + "You must be looking at a Sign to use this command.");
			return;
		}

		BlockState blockState = block.getState();
		if(!(blockState instanceof Sign)) {
			player.sendMessage(ChatColor.RED + "You must be looking at a Sign to use this command.");
			return;
		}

		BlockBreakEvent blockBreakEvent = new BlockBreakEvent(block, player);
		Bukkit.getServer().getPluginManager().callEvent(blockBreakEvent);
		if (blockBreakEvent.isCancelled()) {
			player.sendMessage(ChatColor.RED + "You do not have permission to edit this sign.");
			return;
		}

		Sign sign = (Sign) blockState;

		sign.setLine(lineNumber-1, ChatColor.translateAlternateColorCodes('&', text));

		sign.update();
	}
}
