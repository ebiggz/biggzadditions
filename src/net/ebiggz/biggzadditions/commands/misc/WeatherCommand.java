package net.ebiggz.biggzadditions.commands.misc;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.players.BPlayer;
import org.bukkit.ChatColor;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;

@CommandAlias("wt")
@Description("Manage weather hide settings")
public class WeatherCommand extends BaseCommand {

    @Subcommand("forecast")
    public static void onForecast(Player player) {
        player.sendMessage(getForecast(player));
    }

    @Subcommand("hide")
    public static void onHide(Player player) {
        player.setPlayerWeather(WeatherType.CLEAR);
        player.sendMessage(ChatColor.AQUA + "Hiding weather...");
    }

    @Subcommand("show")
    public static void onShow(Player player) {
        player.sendMessage(ChatColor.AQUA + "Showing weather...");
        player.sendMessage(getForecast(player));
        player.resetPlayerWeather();
    }

    @Subcommand("autohide")
    public static void onAutohide(Player player) {
        BPlayer bPlayer = BiggzAdditions.getBPlayerManager().getBPlayer(player.getDisplayName());

        boolean oldAutohideSetting = bPlayer.shouldHideWeather();
        bPlayer.setHideWeather(!oldAutohideSetting);

        if(oldAutohideSetting) {
            player.resetPlayerWeather();
        } else {
            player.setPlayerWeather(WeatherType.CLEAR);
        }

        String autoHideState = oldAutohideSetting ?
                ChatColor.RED + "off" :
                ChatColor.GREEN + "on";

        player.sendMessage(ChatColor.AQUA + "Weather auto hide toggled " + autoHideState);
        player.sendMessage(getForecast(player));
    }

    @HelpCommand
    public static void onHelp(Player player, CommandHelp help) {
        player.sendMessage(ChatColor.AQUA + "[Weather Help]");
        help.showHelp();
    }

    private static String getForecast(Player player) {
        return ChatColor.AQUA + (player.getWorld().hasStorm() ?
                "There is currently a storm in your world." :
                "The sky's are clear!");
    }

}
