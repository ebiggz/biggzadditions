package net.ebiggz.biggzadditions.commands.res;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.entity.Player;

@CommandAlias("untrust")
@Description("Alias for /res pset")
public class UntrustCommand extends BaseCommand {

    @Default
    @CommandCompletion("@allnames")
    public static void untrustUser(Player player, String playerName) {
        player.performCommand("res pset " + playerName + " trusted false");
    }

}
