package net.ebiggz.biggzadditions.listeners.misc;

import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.constants.WorldName;
import net.ebiggz.biggzadditions.players.BPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class DreamworldListener implements Listener {

    @EventHandler (priority = EventPriority.NORMAL)
    public void bedrockBreak(BlockBreakEvent event) {
        World dreamWorld = BiggzAdditions.getPlugin().getServer().getWorld(WorldName.DREAM_WORLD);
        Player player = event.getPlayer();
        if (player.getWorld() != dreamWorld) {
            return;
        }
        if (event.getBlock().getType() == Material.BEDROCK) {
            event.setCancelled(true);
        }
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void bedrockPlace(BlockPlaceEvent event) {
        World dreamWorld = BiggzAdditions.getPlugin().getServer().getWorld(WorldName.DREAM_WORLD);
        Player player = event.getPlayer();
        if (player.getWorld() != dreamWorld) {
            return;
        }
        if (event.getBlock().getType() == Material.BEDROCK) {
            event.setCancelled(true);
        }
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onEntityPortal(EntityPortalEvent e) {
        World dreamWorld = BiggzAdditions.getPlugin().getServer().getWorld(WorldName.DREAM_WORLD);
        if(e.getEntity().getWorld() != dreamWorld) return;
        if(!(e.getEntity() instanceof Player)) {
            e.setCancelled(true);
        }
    }
}
