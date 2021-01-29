package net.ebiggz.biggzadditions.commands.admin.deathledger;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane.Priority;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.players.BPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

@CommandAlias("dl")
@CommandPermission("biggz.deathledger")
@Description("Lets you see death logs for a given player")
public class DeathLedgerCommand extends BaseCommand {

	@Default
	@CommandCompletion("@allnames")
	public static void onPlayerSelect(CommandSender sender, String playerName) {

		BPlayer bPlayer = BiggzAdditions.getBPlayerManager().getBPlayer(playerName);

		showDeathLogListGui(sender, playerName);
	}

	private static void showDeathLogListGui(CommandSender sender, String playerName) {

		BPlayer bPlayer = BiggzAdditions.getBPlayerManager().getBPlayer(playerName);

		ChestGui gui = new ChestGui(2, playerName + "s Death Logs");

		gui.setOnGlobalClick(event -> event.setCancelled(true));

		OutlinePane logs = new OutlinePane(0, 0, 9, 2, Priority.LOWEST);

		int count = 1;
		for(DeathLog log : bPlayer.getDeathLogList()) {
			ItemStack book = new ItemStack(Material.WRITABLE_BOOK);
			ItemMeta bookMeta = book.getItemMeta();
			bookMeta.setDisplayName(ChatColor.YELLOW + "Death Log #" + count);
			bookMeta.setLore(Arrays.asList(ChatColor.GRAY + log.getDeathTime()));
			book.setItemMeta(bookMeta);

			final int logNumber = count;
			logs.addItem(new GuiItem(book, event -> {
					showDeathLogGui(sender, log, logNumber);
			}));

			count++;
		}

		gui.addPane(logs);

		gui.show((HumanEntity) sender);
	}

	private static void showDeathLogGui(CommandSender sender, DeathLog deathLog, int number) {
		ChestGui gui = new ChestGui(6, deathLog.getPlayerName() + " Death Log #" + number);
		gui.setOnGlobalClick(event -> event.setCancelled(true));

		Bukkit.getLogger().info(Boolean.toString(deathLog == null));

		OutlinePane drops = new OutlinePane(0, 0, 9, 4, Priority.LOWEST);
		OutlinePane armor = new OutlinePane(0, 4, 9, 1, Priority.LOWEST);

		for(ItemStack item : deathLog.getDropsWithoutEquipment())  {
			if(item == null) continue;
			drops.addItem(new GuiItem(item));
		}

		if(deathLog.hasEquipment()) {
			for(ItemStack item : deathLog.getEquipment().getAll())  {
				if(item == null) continue;
				armor.addItem(new GuiItem(item));
			}
		}

		StaticPane controls = new StaticPane(0, 5, 9, 1);

		ItemStack book = new ItemStack(Material.WRITABLE_BOOK);
		ItemMeta bookMeta = book.getItemMeta();
		bookMeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "Details");
		bookMeta.setLore(Arrays.asList(
				ChatColor.GOLD + "When: " + ChatColor.GRAY + deathLog.getDeathTime(),
				ChatColor.GOLD + "Where: " + ChatColor.GRAY + deathLog.getDeathLoc(),
				ChatColor.GOLD + "Why: " + ChatColor.GRAY + deathLog.getReason(),
				"*Click to go back to log list*")
		);
		book.setItemMeta(bookMeta);

		controls.addItem(new GuiItem(book, (event) -> {
			showDeathLogListGui(sender, deathLog.getPlayerName());
		}), 4, 0);

//		ItemStack enderChest = new ItemStack(Material.ENDER_CHEST);
//		ItemMeta ecIM = enderChest.getItemMeta();
//		ecIM.setDisplayName(ChatColor.YELLOW + "Set " + deathDrop.getPlayer()+  "'s Inventory");
//		ecIM.setLore(Arrays.asList("*Click to set " + deathDrop.getPlayer() + "'s","inventory to this death log*"));
//		enderChest.setItemMeta(ecIM);
//		inventory.setItem(multOf9-5,enderChest);
//
//		ItemStack chest = new ItemStack(Material.CHEST);
//		ItemMeta cIM = chest.getItemMeta();
//		cIM.setDisplayName(ChatColor.YELLOW + "Set Your Inventory");
//		cIM.setLore(Arrays.asList("*Click to set your inventory","to this death log*"));
//		chest.setItemMeta(cIM);
//		inventory.setItem(multOf9-4,chest);

		gui.addPane(drops);
		gui.addPane(armor);
		gui.addPane(controls);

		gui.show((HumanEntity) sender);
	}
}
