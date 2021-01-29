package net.ebiggz.biggzadditions.listeners.player;

import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.players.BPlayer;
import net.ebiggz.biggzadditions.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Random;

public class PlayerJoinListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {

        final Player player = event.getPlayer();

        BPlayer bPlayer = BiggzAdditions.getBPlayerManager().getBPlayer(player.getName());

        /*
            Set nickname
         */
        String nickname = bPlayer.getNickname();
        if(nickname != null) {
            Utils.updatePlayerDisplayName(player, nickname);
        }

        /*
            Magus greeting
         */
        final String[] MAGUS_GREETINGS = {
                "o/",
                "\\o",
                "Welcome back, Adepti playerName",
        };
        final String playerName = nickname != null ? nickname : player.getDisplayName();

        Bukkit.getScheduler().runTaskLater(BiggzAdditions.getPlugin(), () -> {

            final String name = ChatColor.DARK_GRAY + "" + ChatColor.MAGIC + "x" +
                    ChatColor.ITALIC + "" + ChatColor.DARK_PURPLE + "The Archmagus" +
                    ChatColor.DARK_GRAY + "" + ChatColor.MAGIC + "x";

            Random random = new Random();
            int greetingIndex = random.nextInt(MAGUS_GREETINGS.length);

            String greeting = MAGUS_GREETINGS[greetingIndex];

            Bukkit.broadcastMessage(name + ChatColor.RESET + ": " + greeting.replaceAll("playerName", playerName));
        }, 32);

    }
}
