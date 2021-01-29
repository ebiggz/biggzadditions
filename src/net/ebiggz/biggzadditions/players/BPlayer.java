package net.ebiggz.biggzadditions.players;

import net.ebiggz.biggzadditions.config.Config;
import net.ebiggz.biggzadditions.commands.admin.deathledger.DeathLog;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BPlayer {

    private String playerName;

    private boolean resetPhantomTime;
    private boolean hideWeather;
    private Location dreamBedLoc;
    private Location lastDeathLoc;
    private Location homeLoc;
    private String nickname;
    private List<DeathLog> deathLogList;

    public BPlayer(String playerName) {
        this.playerName = playerName;

        FileConfiguration config = BPlayerFileFactory.loadPlayerFile(playerName).getConfig();
        this.resetPhantomTime = config.getBoolean("resetPhantomTimeOnServerSleep", false);
        this.hideWeather = config.getBoolean("autoHideWeather", false);
        this.dreamBedLoc = config.getLocation("dreamBedLocation", null);
        this.lastDeathLoc = config.getLocation("lastDeathLocation", null);
        this.homeLoc = config.getLocation("home", null);
        this.nickname = config.getString("nickname", null);

        // death logs
        ConfigurationSection cs = config.getConfigurationSection("deathLogs");
        List<DeathLog> logList = new ArrayList<DeathLog>();
        if(cs != null) {
            logList = cs.getKeys(false)
                    .stream()
                    .map(k -> {
                        ConfigurationSection deathDropData = config
                                .getConfigurationSection("deathLogs." + k);

                        return deathDropData != null ? new DeathLog(playerName, deathDropData) : null;
                    })
                    .sorted()
                    .collect(Collectors.toList());
        }
        this.deathLogList = logList;
    }

    public boolean shouldResetPhantomTime() {
        return resetPhantomTime;
    }

    public void setResetPhantomTime(boolean resetPhantomTime) {
        this.resetPhantomTime = resetPhantomTime;
        saveToFile("resetPhantomTimeOnServerSleep", resetPhantomTime);
    }

    public boolean shouldHideWeather() {
        return hideWeather;
    }

    public void setHideWeather(boolean hideWeather) {
        this.hideWeather = hideWeather;
        saveToFile("autoHideWeather", hideWeather);
    }

    public Location getDreamBedLoc() {
        return dreamBedLoc;
    }

    public void setDreamBedLoc(Location dreamBedLoc) {
        this.dreamBedLoc = dreamBedLoc;
        saveToFile("dreamBedLocation", dreamBedLoc);
    }

    public Location getLastDeathLoc() {
        return lastDeathLoc;
    }

    public void setLastDeathLoc(Location lastDeathLoc) {
        this.lastDeathLoc = lastDeathLoc;
        saveToFile("lastDeathLocation", lastDeathLoc);
    }

    public Location getHomeLoc() {
        return homeLoc;
    }

    public void setHomeLoc(Location homeLoc) {
        this.homeLoc = homeLoc;
        saveToFile("home", homeLoc);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        saveToFile("nickname", nickname);
    }

    public List<DeathLog> getDeathLogList() {
        return deathLogList;
    }

    public void addNewDeathLog(DeathLog newDeathLog) {
        Config bPlayerConfigData = BPlayerFileFactory.loadPlayerFile(playerName);
        FileConfiguration config = bPlayerConfigData.getConfig();

        deathLogList.add(newDeathLog);

        deathLogList = deathLogList
                .stream()
                .filter(dl -> dl != null)
                .limit(18)
                .collect(Collectors.toList());

        int counter = 1;
        for(DeathLog dl: deathLogList) {
            config.set("deathLogs." + counter + ".Drops", dl.getDrops());
            if(dl.hasEquipment()) {
                config.set("deathLogs." + counter + ".Equipment.head",
                        dl.getEquipment().getEquipmentSlot(EquipmentSlot.HEAD));
                config.set("deathLogs." + counter + ".Equipment.chest",
                        dl.getEquipment().getEquipmentSlot(EquipmentSlot.CHEST));
                config.set("deathLogs." + counter + ".Equipment.legs",
                        dl.getEquipment().getEquipmentSlot(EquipmentSlot.LEGS));
                config.set("deathLogs." + counter + ".Equipment.feet",
                        dl.getEquipment().getEquipmentSlot(EquipmentSlot.FEET));
                config.set("deathLogs." + counter + ".Equipment.offHand",
                        dl.getEquipment().getEquipmentSlot(EquipmentSlot.OFF_HAND));
            }
            config.set("deathLogs."  + counter + ".Time", dl.getDeathTime());
            config.set("deathLogs."  + counter + ".Reason", dl.getReason());
            config.set("deathLogs." + counter + ".Location", dl.getDeathLoc());
            config.set("deathLogs."  + counter + ".World", dl.getWorld());
            bPlayerConfigData.save();
            counter++;
        }
    }

    private <E> void saveToFile(String path, E data) {
        FileConfiguration config = BPlayerFileFactory.loadPlayerFile(playerName).getConfig();
        config.set(path, data);
    }

}
