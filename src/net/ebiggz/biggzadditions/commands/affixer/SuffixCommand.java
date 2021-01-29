package net.ebiggz.biggzadditions.commands.affixer;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import org.bukkit.entity.Player;

@CommandAlias("suffix")
@Description("Lets you edit your suffix")
public class SuffixCommand extends BaseCommand {

    @Default
    @CommandCompletion("@suffix --addspace")
    public static void setPrefix(Player player, @Default("") String suffix) {
        boolean shouldAddSpace = suffix.endsWith(" --addspace");

        if(shouldAddSpace) {
            suffix = " " + suffix.replaceAll(" --addspace", "");
        }

        new PlayerName(player)
                .setSuffix(suffix)
                .showPreviewToPlayer();
    }
}
