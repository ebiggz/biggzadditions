package net.ebiggz.biggzadditions.commands.misc;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import net.ebiggz.biggzadditions.BiggzAdditions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("breed")
@Description("Sets a 5m breed reminder timer")
public class BreedCommand extends BaseCommand {

    final static int FIVE_MINUTES_IN_TICKS = 20 * 60 * 5;

    @Default
    public static void startTimer(Player player) {
        player.sendMessage(ChatColor.GREEN + "You will get a breed reminder in " + ChatColor.GOLD + "5 minutes");
        Bukkit.getScheduler().runTaskLater(BiggzAdditions.getPlugin(), () -> {
            player.sendMessage(ChatColor.GREEN + "Your 5 minute breed timer has finished!");
        }, FIVE_MINUTES_IN_TICKS);
    }
}
