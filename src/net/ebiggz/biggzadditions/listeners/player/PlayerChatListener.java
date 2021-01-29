package net.ebiggz.biggzadditions.listeners.player;

import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.constants.WorldName;
import net.ebiggz.biggzadditions.players.BPlayer;
import net.ebiggz.biggzadditions.util.FireworkUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        if(event.getMessage().toLowerCase().contains("happy birthday")) {
            final Location location = event
                    .getPlayer()
                    .getLocation()
                    .add(0, 2, 0 );
            Bukkit.getScheduler().runTask(BiggzAdditions.getPlugin(), () -> {
                FireworkUtils.triggerRandomFirework(location);
            });
        }
    }
}
