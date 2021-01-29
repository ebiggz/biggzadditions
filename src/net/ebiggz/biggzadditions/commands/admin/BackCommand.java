package net.ebiggz.biggzadditions.commands.admin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import net.ebiggz.biggzadditions.BiggzAdditions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

@CommandAlias("back")
@Description("Lets you go back")
@CommandPermission("biggz.admin")
public class BackCommand extends BaseCommand {
    @Default
    public static void goBack(Player player) {
        if (BiggzAdditions.lastTpLocation.containsKey(player)) {
            player.teleport(BiggzAdditions.lastTpLocation.get(player), PlayerTeleportEvent.TeleportCause.PLUGIN);
        } else {
            player.sendMessage(ChatColor.RED + "You have no previous teleport locations yet.");
        }
    }
}
