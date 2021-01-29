package net.ebiggz.biggzadditions.listeners.misc;

import com.bekvon.bukkit.residence.event.ResidenceCreationEvent;
import com.bekvon.bukkit.residence.event.ResidenceRenameEvent;
import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.players.BPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Optional;
import java.util.UUID;

public class ResidenceListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onResCreate(ResidenceCreationEvent event) {
        final UUID resUUID = UUID.randomUUID();

        BiggzAdditions.recentResidences.put(event.getResidenceName(), resUUID);

        Bukkit.getScheduler().runTaskLaterAsynchronously(BiggzAdditions.getPlugin(), () -> {
            if(BiggzAdditions.recentResidences.containsValue(resUUID)) {
                String resNameKey = getRecentResNameKey(resUUID);
                if(resNameKey != null) {
                    BiggzAdditions.recentResidences.remove(resNameKey);
                }
            }
        }, 5 * 60 * 20);
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onResRename(ResidenceRenameEvent event) {

        final String oldResName = event.getOldResidenceName();
        final String newResName = event.getNewResidenceName();

        if(BiggzAdditions.recentResidences.containsKey(oldResName)) {
            UUID uuid = BiggzAdditions.recentResidences.remove(oldResName);
            BiggzAdditions.recentResidences.put(newResName, uuid);
        }
    }

    private String getRecentResNameKey(UUID uuid) {
        Optional<String> resNameKeyOptional = BiggzAdditions.recentResidences
                .entrySet()
                .stream()
                .filter(e -> e.getValue().equals(uuid))
                .map(e -> e.getKey())
                .findFirst();
        return resNameKeyOptional.orElse(null);
    }
}
