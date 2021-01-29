package net.ebiggz.biggzadditions.commands.nickname;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.ebiggz.biggzadditions.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("nicknameother")
@Description("Set a nickname for someone else")
@CommandPermission("biggz.nickname.other")
public class NicknameOtherCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    public static void onCommand(Player player, Player target, @Optional @Single String nickname) {

        if(target == null) {
            player.sendMessage(ChatColor.RED + "Unable to find an online player with that name.");
            return;
        }

        if(nickname != null) {
            String nick = Utils.updatePlayerDisplayName(target, nickname);
            player.sendMessage(ChatColor.AQUA + "Set " + target.getPlayer() + "'s nickname to: " + nick);
        } else {
            Utils.updatePlayerDisplayName(target, null);
            player.sendMessage(ChatColor.AQUA + "Cleared " + target.getPlayer() + "'s nickname.");
        }
    }

}
