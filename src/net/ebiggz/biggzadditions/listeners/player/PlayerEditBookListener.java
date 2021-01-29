package net.ebiggz.biggzadditions.listeners.player;

import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.players.BPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

import java.util.stream.Collectors;

public class PlayerEditBookListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onBookSave(PlayerEditBookEvent event) {
        if(!event.isCancelled() && event.isSigning()) {
            BookMeta bookMeta = event.getNewBookMeta();
            bookMeta.setPages(
                    bookMeta
                            .getPages()
                            .stream()
                            .map(p -> ChatColor.translateAlternateColorCodes('&', p))
                            .collect(Collectors.toList())
            );
            event.setNewBookMeta(bookMeta);
        }
    }

}
