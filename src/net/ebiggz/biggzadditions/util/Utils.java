package net.ebiggz.biggzadditions.util;

import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.players.BPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SoundGroup;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Locale;
import java.util.regex.Pattern;

public class Utils {

    private static final Pattern NON_ALPHA_NUMERIC = Pattern.compile("[^a-zA-Z0-9]");

    public static String simplifyString(String str) {
        if (str == null) {
            return null;
        }
        return NON_ALPHA_NUMERIC
                .matcher(str.toLowerCase(Locale.ENGLISH))
                .replaceAll("");
    }

    public static String updatePlayerDisplayName(Player player, String displayName) {
        if(player == null) return null;
        BPlayer bPlayer = BiggzAdditions.getBPlayerManager().getBPlayer(player.getName());
        if(displayName == null) {
            bPlayer.setNickname(null);
            player.setDisplayName(null);
            player.setPlayerListName(null);
            return null;
        } else {
            bPlayer.setNickname(displayName);
            String translatedNick = ChatColor.translateAlternateColorCodes('&', displayName);
            player.setDisplayName(translatedNick);
            player.setPlayerListName(ChatColor.stripColor(translatedNick));
            return translatedNick;
        }
    }

    public static void blindPlayer(Player player, int seconds) {

    }

}
