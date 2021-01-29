package net.ebiggz.biggzadditions.listeners.player;

import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.players.BPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTp(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("biggz.admin")) {
            BiggzAdditions.lastTpLocation.put(player, event.getFrom());
        }
    }

}
