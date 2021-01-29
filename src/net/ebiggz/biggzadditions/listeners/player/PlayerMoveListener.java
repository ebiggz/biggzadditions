package net.ebiggz.biggzadditions.listeners.player;

import dev.esophose.playerparticles.api.PlayerParticlesAPI;
import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.commands.misc.HearthCommand;
import net.ebiggz.biggzadditions.players.BPlayer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent event) {
        if(!HearthCommand.pendingHearth.containsKey(event.getPlayer())) return;
        if(event.getFrom().getBlockX() == event.getTo().getBlockX() &&
                event.getFrom().getBlockY() == event.getTo().getBlockY() &&
                event.getFrom().getBlockZ() == event.getTo().getBlockZ()) return;
        int taskId = HearthCommand.pendingHearth.remove(event.getPlayer());
        Bukkit.getScheduler().cancelTask(taskId);
        PlayerParticlesAPI.getInstance().resetActivePlayerParticles(event.getPlayer());
        event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,
                new ComponentBuilder("").create());
        event.getPlayer().sendMessage(ChatColor.GREEN + "Hearth cancelled.");
    }

}
