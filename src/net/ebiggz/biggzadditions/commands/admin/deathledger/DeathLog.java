package net.ebiggz.biggzadditions.commands.admin.deathledger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class DeathLog implements Comparable<Object>{

    private String playerName;
    private String deathTime;
    private List<ItemStack> drops;
    private DeathLogEquipment equipment;
    private String deathLoc;
    private String world;
    private String reason;

    @SuppressWarnings("unchecked")
    public DeathLog(String playerName, ConfigurationSection deathLogData) {
        this.playerName = playerName;

        if(deathLogData.contains("Drops")) {
            this.drops = (List<ItemStack>) deathLogData.getList("Drops");
        }

        this.equipment = new DeathLogEquipment(deathLogData.getConfigurationSection("Equipment"));

        if(deathLogData.contains("Time")) {
            this.deathTime = deathLogData.getString("Time");
        }
        if(deathLogData.contains("Location")) {
            this.deathLoc = deathLogData.getString("Location");
        }
        if(deathLogData.contains("World")) {
            this.setWorld(deathLogData.getString("World"));
        }
        if(deathLogData.contains("Reason")) {
            this.reason = deathLogData.getString("Reason");
        }
    }

    public DeathLog(String playerName, List<ItemStack> drops, DeathLogEquipment equipment,
                    String deathLoc, String world, String reason, String time) {
        this.playerName = playerName;
        this.drops = drops;
        this.equipment = equipment;
        this.deathLoc = deathLoc;
        this.world = world;
        this.reason = reason;
        this.deathTime = time;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getDeathTime() {
        return deathTime;
    }

    public String getReason() {
        return reason;
    }

    public void setDeathTime(String time) {
        this.deathTime = time;
    }

    public List<ItemStack> getDrops() {
        return drops;
    }

    public List<ItemStack> getDropsWithoutEquipment() {
        return drops
                .stream()
                .filter(i -> equipment == null || !equipment.itemIsEquipment(i))
                .collect(Collectors.toList());
    }

    public DeathLogEquipment getEquipment() {
        return equipment;
    }

    public boolean hasEquipment() {
        return equipment != null &&
                equipment.hasEquipment();
    }

    public String getDeathLoc() {
        return deathLoc;
    }

    public void setDeathLoc(String deathLoc) {
        this.deathLoc = deathLoc;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    @Override
    public int compareTo(Object o) {
        DateFormat formatter;
        Date date1 = null;
        Date date2 = null;
        formatter = new SimpleDateFormat("M/d/yy h:mm a");
        try {
            date1 = (Date) formatter.parse(deathTime);
            DeathLog other = (DeathLog) o;
            date2 = (Date) formatter.parse(other.getDeathTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        catch(NullPointerException npe){
            System.out.println("Exception thrown " + npe.getMessage() + " date1 is " + date1 + " date2 is " + date2);
        }
        return date2.compareTo(date1);
    }
}
