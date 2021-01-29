package net.ebiggz.biggzadditions.commands.admin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import net.ebiggz.biggzadditions.BiggzAdditions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("togglefireworks")
@Description("Lets you toggle spawn fireworks")
@CommandPermission("biggz.admin")
public class ToggleFireworksCommand extends BaseCommand {
    @Default
    public static void toggleFireworks(Player player) {
        BiggzAdditions.spawnFireworksDisabled = !BiggzAdditions.spawnFireworksDisabled;

        player.sendMessage(ChatColor.AQUA + "Toggled spawn fireworks to: " +
                ChatColor.GOLD + (BiggzAdditions.spawnFireworksDisabled ? "Off" : "On"));
    }
}
