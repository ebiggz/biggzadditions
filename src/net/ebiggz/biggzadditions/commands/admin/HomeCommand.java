package net.ebiggz.biggzadditions.commands.admin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.players.BPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

@CommandAlias("home")
@Description("Lets set and tp to a home")
@CommandPermission("booty.admin")
public class HomeCommand extends BaseCommand {

    @Default
    public static void goHome(Player player) {
        BPlayer bPlayer = BiggzAdditions.getBPlayerManager().getBPlayer(player.getName());

        Location home = bPlayer.getHomeLoc();

        if(home == null) {
            player.sendMessage(ChatColor.RED + "You don't have a home set.");
            return;
        }

        player.teleport(home, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    @Subcommand("set")
    public static void setHome(Player player) {
        BPlayer bPlayer = BiggzAdditions.getBPlayerManager().getBPlayer(player.getName());
        bPlayer.setHomeLoc(player.getLocation());
        player.sendMessage(ChatColor.AQUA + "Home set to your current location!");
    }

}