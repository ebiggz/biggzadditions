package net.ebiggz.biggzadditions.commands.res;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.entity.Player;

@CommandAlias("trust")
@Description("Alias for /res pset")
public class TrustCommand extends BaseCommand {

    @Default
    @CommandCompletion("@allnames")
    public static void trustUser(Player player, String playerName) {
        player.performCommand("res pset " + playerName + " trusted true");
    }

}
