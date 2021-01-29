package net.ebiggz.biggzadditions.commands.misc;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.players.BPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("phantomreset")
@Description("Toggles whether or not your insomnia gets reset when anyone sleeps")
public class PhantomResetCommand extends BaseCommand {

    @Default
    public static void onCommand(Player player) {
        BPlayer bPlayer = BiggzAdditions.getBPlayerManager().getBPlayer(player.getName());
        boolean shouldResetPhantom = !bPlayer.shouldResetPhantomTime();

        if(shouldResetPhantom) {
            player.sendMessage(ChatColor.AQUA + "Your phantom timer will now reset when any player on the server sleeps.");
        } else {
            player.sendMessage(ChatColor.AQUA + "Your phantom timer will NOT reset when any player on the server sleeps.");
        }

        bPlayer.setResetPhantomTime(shouldResetPhantom);
    }
}
