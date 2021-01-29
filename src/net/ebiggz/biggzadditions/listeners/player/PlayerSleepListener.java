package net.ebiggz.biggzadditions.listeners.player;

import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.players.BPlayer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.util.HashMap;
import java.util.Random;

public class PlayerSleepListener implements Listener {

    private final String[] SLEEP_MESSAGES = {
            "Shh! %PLAYER% is sleeping...",
            "%PLAYER% has gone to sleep. Sweet dreams!",
            "%PLAYER% is looking to catch some Zs...",
            "%PLAYER% is in bed and needs a bedtime story!",
            "%PLAYER% snores loudly as they fall into a deep slumber...",
            "%PLAYER% is in bed and needs someone to tuck them in!",
            "%PLAYER% is in bed and needs to be sung a lullaby!",
            "%PLAYER% nods off as they count sheep...",
    };

    private HashMap<Player, Integer> _sleepyTime = new HashMap<Player, Integer>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void sleep(PlayerBedEnterEvent event) {
        if(event.isCancelled() || event.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK)
            return;

        if(Bukkit.getOnlinePlayers().size() == 1) {
            Bukkit.getLogger().info("Only one player online. Let normal behavior happen");
            return;
        }

        Player p = event.getPlayer();

        int taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(BiggzAdditions.getPlugin(), () -> {
            Bukkit.getLogger().info("Running skip night command.");

            _sleepyTime.remove(p);

            World world = p.getWorld();
            if (world.isThundering() || world.hasStorm())
            {
                world.setThundering(false);
                world.setStorm(false);
            }
            world.setTime(0);
            for(Player player : world.getPlayers()) {
                BPlayer bPlayer = BiggzAdditions.getBPlayerManager().getBPlayer(player.getName());
                if(bPlayer.shouldResetPhantomTime()) {
                    player.setStatistic(Statistic.TIME_SINCE_REST, 0);
                }
            }
            for(int pendingTaskId : _sleepyTime.values()) {
                Bukkit.getScheduler().cancelTask(pendingTaskId);
            }
        }, 160L);


        _sleepyTime.put(p, taskId);

        Random random = new Random();
        int randomIndex = random.nextInt(SLEEP_MESSAGES.length);

        String message = SLEEP_MESSAGES[randomIndex];
        Bukkit.broadcastMessage(message
                .replace("%PLAYER%", ChatColor.YELLOW + p.getDisplayName() + ChatColor.RESET)
        );
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void wake(PlayerBedLeaveEvent event) {
        if(_sleepyTime.containsKey(event.getPlayer())) {
            int taskId = _sleepyTime.remove(event.getPlayer());
            Bukkit.getScheduler().cancelTask(taskId);
        }
    }

}
