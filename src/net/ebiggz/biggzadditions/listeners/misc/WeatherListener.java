package net.ebiggz.biggzadditions.listeners.misc;

import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.players.BPlayer;
import org.bukkit.ChatColor;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener  implements Listener {

    //Listener for when a player joins the game
    @EventHandler(priority = EventPriority.MONITOR)
    public void onLogin(PlayerJoinEvent event) {

        // get player associated to this event
        Player player = event.getPlayer();

        // handle weather state for them
        handleWeather(player, false);
    }

    //Listener for when a player switches to a different world
    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldChange(PlayerChangedWorldEvent event) {

        // get player associated to this event
        Player player = event.getPlayer();

        // handle weather state for them
        handleWeather(player, false);
    }

    //Listener for weather changes
    @EventHandler(priority = EventPriority.MONITOR)
    public void onStorm(WeatherChangeEvent event) {

        // check if it has started raining
        if(event.toWeatherState()) {

            // loop through all players in the world it began to rain in and
            // handle the weather state for them
            event.getWorld()
                    .getPlayers()
                    .forEach((player) -> handleWeather(player, true));

        }
    }

    private void handleWeather(Player player, boolean justStarted) {

        BiggzAdditions.getPlugin().getLogger().info("Handling weather for " + player.getName());

        // Check if the world the player is in is storming
        if(player.getWorld().hasStorm() || justStarted) {

            String messagePrefix = justStarted ? "It started" : "It's currently";

            // load daddy preferences file for player
            BPlayer bPlayer = BiggzAdditions.getBPlayerManager().getBPlayer(player.getName());

            // check if this daddy has auto hide weather turned on
            if(bPlayer.shouldHideWeather()) {
                // Daddy wants weather hidden.
                // Make the skies clear for daddy
                player.setPlayerWeather(WeatherType.CLEAR);
                player.sendMessage(ChatColor.AQUA + messagePrefix + " storming in this world " +
                        "but the weather has been automatically hidden.");
            } else {
                // Inform daddy of commands to hide if they wish.
                player.sendMessage(ChatColor.AQUA + messagePrefix + " storming in this world. Type " +
                        ChatColor.YELLOW + "/wt hide" +
                        ChatColor.AQUA +  " or " +
                        ChatColor.YELLOW + "/wt autohide" +
                        ChatColor.AQUA + " if you wish to hide it.");
            }
        }
    }
}
