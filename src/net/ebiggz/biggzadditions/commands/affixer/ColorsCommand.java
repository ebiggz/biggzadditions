package net.ebiggz.biggzadditions.commands.affixer;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("colors|formats")
@Description("See available color and format codes")
public class ColorsCommand extends BaseCommand {

    @Default
    public static void onColors(Player player) {

        player.sendMessage(ChatColor.WHITE + "Colors:"
                + ChatColor.BLACK + " &0"
                + ChatColor.DARK_BLUE + " &1"
                + ChatColor.DARK_GREEN + " &2"
                + ChatColor.DARK_AQUA + " &3"
                + ChatColor.DARK_RED + " &4"
                + ChatColor.DARK_PURPLE + " &5"
                + ChatColor.GOLD + " &6"
                + ChatColor.GRAY + " &7"
                + ChatColor.DARK_GRAY + " &8"
                + ChatColor.BLUE + " &9"
                + ChatColor.GREEN + " &a"
                + ChatColor.AQUA + " &b"
                + ChatColor.RED + " &c"
                + ChatColor.LIGHT_PURPLE + " &d"
                + ChatColor.YELLOW + " &e"
                + ChatColor.WHITE + " &f");

        player.sendMessage(ChatColor.WHITE + "Formats:"
                + ChatColor.BOLD + " &l" + ChatColor.RESET + " "
                + ChatColor.STRIKETHROUGH + "&m" + ChatColor.RESET + " "
                + ChatColor.UNDERLINE + "&n" + ChatColor.RESET + " "
                + ChatColor.ITALIC + "&o"
                + ChatColor.RESET + " &r(reset)");
    }
}
