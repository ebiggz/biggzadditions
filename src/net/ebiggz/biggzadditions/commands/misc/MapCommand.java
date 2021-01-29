package net.ebiggz.biggzadditions.commands.misc;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("map")
@Description("Get the online map link")
public class MapCommand extends BaseCommand {

    @Default
    public static void onMap(Player player) {
        player.sendMessage(ChatColor.YELLOW + "Online Map: " + ChatColor.AQUA + "http://mc.enclavecraft.net:8123");
    }

}
