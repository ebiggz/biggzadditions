package net.ebiggz.biggzadditions.listeners.player;

import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.constants.WorldName;
import net.ebiggz.biggzadditions.players.BPlayer;
import net.ebiggz.biggzadditions.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.block.data.type.Bed;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerInteractListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(!event.hasBlock())
            return;

        if(event.getAction() == Action.LEFT_CLICK_BLOCK ||
                event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if(event.getClickedBlock().getBlockData() instanceof Bed) {
                Player player = event.getPlayer();

                if(BiggzAdditions.pendingDreamers.containsKey(player)) {
                    event.setCancelled(true);

                    int taskId = BiggzAdditions.pendingDreamers.remove(player);
                    Bukkit.getServer().getScheduler().cancelTask(taskId);

                    World dreamWorld = Bukkit.getWorld(WorldName.DREAM_WORLD);
                    Location dreamSpawnLocation = new Location(dreamWorld, 0, 4, 0);

                    BPlayer bPlayer = BiggzAdditions.getBPlayerManager().getBPlayer(player.getName());
                    bPlayer.setDreamBedLoc(player.getLocation());

                    player.sendMessage(ChatColor.AQUA + "Drifting off to dream land...");

                    Utils.blindPlayer(player, 2);

                    Bukkit.getScheduler().runTaskLater(BiggzAdditions.getPlugin(), () -> {

                        player.teleport(dreamSpawnLocation, TeleportCause.PLUGIN);

                        Utils.blindPlayer(player, 1);

                        player.sendMessage(ChatColor.AQUA + "You are now dreaming. Type " +
                                ChatColor.YELLOW + "/wakeup" +
                                ChatColor.AQUA + " at any time to wake up.");
                    }, 20);
                }
            }
        }
    }
}
