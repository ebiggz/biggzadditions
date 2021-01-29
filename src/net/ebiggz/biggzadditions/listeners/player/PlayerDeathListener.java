package net.ebiggz.biggzadditions.listeners.player;

import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.commands.admin.deathledger.DeathLog;
import net.ebiggz.biggzadditions.commands.admin.deathledger.DeathLogEquipment;
import net.ebiggz.biggzadditions.players.BPlayer;
import net.ebiggz.biggzadditions.util.TimeUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.List;

public class PlayerDeathListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        BPlayer bPlayer = BiggzAdditions.getBPlayerManager().getBPlayer(player.getName());
        bPlayer.setLastDeathLoc(player.getLocation());

        String playerName = player.getName();

        List<ItemStack> drops = event.getDrops();
        if(drops.size() > 2) {

            Location death = event.getEntity().getLocation();
            String deathWorld = event.getEntity().getWorld().getName();

            String deathLoc =
                    death.getBlockX() + ","
                            + death.getBlockY() + ","
                            + death.getBlockZ() + ","
                            + death.getWorld().getName();

            event.getEntity().getInventory();

            String reason = event.getDeathMessage().replace(playerName, "Player").trim();
            String time = TimeUtils.dateAndTimeFromMills(TimeUtils.timeInMillis());

            PlayerInventory inventory = event.getEntity().getInventory();

            DeathLogEquipment equipment = new DeathLogEquipment(
                    inventory.getItem(EquipmentSlot.HEAD),
                    inventory.getItem(EquipmentSlot.CHEST),
                    inventory.getItem(EquipmentSlot.LEGS),
                    inventory.getItem(EquipmentSlot.FEET),
                    inventory.getItem(EquipmentSlot.OFF_HAND)
            );

            DeathLog log = new DeathLog(playerName, drops, equipment, deathLoc, deathWorld, reason, time);

            bPlayer.addNewDeathLog(log);
        }
    }
}
