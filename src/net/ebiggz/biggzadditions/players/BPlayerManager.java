package net.ebiggz.biggzadditions.players;

import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.config.Config;

import java.util.HashMap;

public class BPlayerManager {

    private HashMap<String, BPlayer> bPlayersCache = new HashMap<>();

    public BPlayer getBPlayer(String playerName) {
        if(bPlayersCache.containsKey(playerName)) {
            return bPlayersCache.get(playerName);
        } else {
            return new BPlayer(playerName);
        }
    }

    public void addPlayerToCache(String playerName) {
        bPlayersCache.put(playerName, new BPlayer(playerName));
    }

    public void removePlayerFromCache(String playerName) {
        bPlayersCache.remove(playerName);
    }
}
