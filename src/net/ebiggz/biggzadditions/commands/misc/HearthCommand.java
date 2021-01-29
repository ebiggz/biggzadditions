package net.ebiggz.biggzadditions.commands.misc;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import dev.esophose.playerparticles.api.PlayerParticlesAPI;
import dev.esophose.playerparticles.particles.ParticleEffect;
import dev.esophose.playerparticles.particles.data.OrdinaryColor;
import dev.esophose.playerparticles.styles.DefaultStyles;
import net.ebiggz.biggzadditions.BiggzAdditions;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@CommandAlias("hearth")
@Description("Let's you go to spawn")
public class HearthCommand extends BaseCommand {
    public static HashMap<String, Date> hearthCooldowns = new HashMap<String, Date>();
    public static Map<Player, Integer> pendingHearth = new HashMap<Player, Integer>();

    @Default
    public static void hearth(Player player) {

        final String playerName = player.getName();

        if(hearthCooldowns.containsKey(playerName)) {
            Date now = new Date();
            Date cooldownDate = hearthCooldowns.get(playerName);
            long timeDiff =  cooldownDate.getTime() - now.getTime();
            if(timeDiff < 0) {
                hearthCooldowns.remove(playerName);
            } else {
                player.sendMessage(ChatColor.RED + "Your hearth is on cooldown for: " + getTimeRemaining(timeDiff));
                return;
            }
        }

        boolean inOwnedResidence = Residence
                .getInstance()
                .getResidenceManager()
                .isOwnerOfLocation(player, player.getLocation());

        if(!inOwnedResidence) {
            player.sendMessage(ChatColor.RED + "You can only use this command while standing in a Residence you own.");
            return;
        }

        ClaimedResidence res = Residence.getInstance().getResidenceManager().getByLoc(player);
        if(BiggzAdditions.recentResidences.containsKey(res.getResidenceName())) {
            player.sendMessage(ChatColor.RED + "You can't hearth from a res that was created less than 5 minutes ago.");
            return;
        }

        PlayerParticlesAPI
                .getInstance()
                .addActivePlayerParticle(player, ParticleEffect.DUST, DefaultStyles.ORBIT, OrdinaryColor.RAINBOW);

        PlayerParticlesAPI
                .getInstance()
                .addActivePlayerParticle(player, ParticleEffect.ENTITY_EFFECT, DefaultStyles.FEET, OrdinaryColor.RAINBOW);

        BukkitTask task = Bukkit.getScheduler().runTaskLater(BiggzAdditions.getPlugin(), () -> {
            if(pendingHearth.containsKey(player)) {
                Location spawn = Bukkit.getWorld("world").getSpawnLocation();
                player.teleport(spawn, PlayerTeleportEvent.TeleportCause.PLUGIN);

                pendingHearth.remove(player);

                PlayerParticlesAPI.getInstance().resetActivePlayerParticles(player);

                hearthCooldowns.put(playerName, getCooldownDate());

                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + "The Archmagus has teleported "
                        + ChatColor.AQUA + ChatColor.ITALIC + playerName + ChatColor.LIGHT_PURPLE + ChatColor.ITALIC + " to Spawn.");
            }
        }, 20 * 10);

        pendingHearth.put(player, task.getTaskId());

        displaySecondsRemaining(player, 10);
    }

    private static void displaySecondsRemaining(Player player, int seconds) {
        if(seconds == 0) {
            return;
        }

        if(!pendingHearth.containsKey(player)) {
            return;
        }

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                (new ComponentBuilder(net.md_5.bungee.api.ChatColor.GREEN +
                        "The Archmagus will hearth you in " +  seconds + "s").create()));

        Bukkit.getScheduler().runTaskLaterAsynchronously(BiggzAdditions.getPlugin(), () -> {
            displaySecondsRemaining(player, seconds - 1);
        }, 20);
    }

    private static Date getCooldownDate() {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MINUTE, 30);
        return cal.getTime();
    }

    private static String getTimeRemaining(long milliseconds) {
        int totalSeconds = (int) milliseconds / 1000;

        int remainingMinutes = totalSeconds / 60;
        int remainingSeconds = totalSeconds % 60;

        String output = "";

        if(remainingMinutes > 0) {
            output += remainingMinutes + "m ";
        }

        if(remainingSeconds > 0) {
            output += remainingSeconds + "s";
        }

        return output;
    }
}