package net.ebiggz.biggzadditions.commands.misc;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@CommandAlias("portal")
@Description("Calculate coords for Nether portals")
public class PortalCommand extends BaseCommand {

    @Default
    @CommandCompletion("nether|overworld")
    public static void startTimer(Player player, @Optional @Values("nether|overworld") String worldType, @Optional Location location) {
        if(worldType == null) {
            // use the opposite world type based on the players current world
            worldType = player.getLocation().getWorld().getEnvironment() == World.Environment.NETHER
                    ? "overworld"
                    : "nether";
        } else {
            if(location == null) {
                boolean currentWorldIsNether = player.getLocation().
                        getWorld()
                        .getEnvironment() == World.Environment.NETHER;

                if((worldType.equals("nether") && currentWorldIsNether) ||
                        (worldType.equals("overworld") && !currentWorldIsNether)) {
                    player.sendMessage(ChatColor.RED + "Cannot generate portal coords for the " + worldType
                        + " with your current location because you are currently in the " + worldType);
                    return;
                }

            }
        }
        if(location == null) {
            location = player.getLocation();
        }

        int x;
        int y;
        int z;

        if(worldType.equals("nether")) {
            x = location.getBlockX() / 8;
            y = 119;
            z = location.getBlockZ() / 8;
        } else if(worldType.equals("overworld")) {
            x = location.getBlockX() * 8;
            y = location.getBlockY();
            z = location.getBlockZ() * 8;
        } else {
            player.sendMessage(ChatColor.RED + "Cannot get portal coords for world type '" + worldType + "'");
            return;
        }

        player.sendMessage(ChatColor.AQUA + "A matching portal in the " + worldType + " should be placed at: "
                + ChatColor.GOLD
                + x + ", "
                + y + ", "
                + z);
    }
}
