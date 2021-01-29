package net.ebiggz.biggzadditions.commands.affixer;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.entity.Player;

@CommandAlias("prefix")
@Description("Lets you edit your prefix")
public class PrefixCommand extends BaseCommand {

    @Default
    @CommandCompletion("@prefix --addspace")
    public static void setPrefix(Player player, @Default("") String prefix) {
        boolean shouldAddSpace = prefix.endsWith(" --addspace");

        if(shouldAddSpace) {
            prefix = prefix.replaceAll(" --addspace", "") + " ";
        }

        new PlayerName(player)
                .setPrefix(prefix)
                .showPreviewToPlayer();
    }
}
