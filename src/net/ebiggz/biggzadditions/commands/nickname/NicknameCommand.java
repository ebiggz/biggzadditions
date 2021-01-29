package net.ebiggz.biggzadditions.commands.nickname;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.ebiggz.biggzadditions.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("nickname")
@Description("Set a nickname for yourself")
@CommandPermission("biggz.nickname.self")
public class NicknameCommand extends BaseCommand {

    @Default
    public static void onCommand(Player player, @Optional @Single String nickname) {
        if(nickname != null) {
            String nick = Utils.updatePlayerDisplayName(player, nickname);
            player.sendMessage(ChatColor.AQUA + "Set nickname to: " + nick);
        } else {
            Utils.updatePlayerDisplayName(player, null);
            player.sendMessage(ChatColor.AQUA + "Cleared nickname.");
        }
    }

}
