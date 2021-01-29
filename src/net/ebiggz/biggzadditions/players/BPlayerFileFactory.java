package net.ebiggz.biggzadditions.players;

import net.ebiggz.biggzadditions.BiggzAdditions;
import net.ebiggz.biggzadditions.config.Config;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class BPlayerFileFactory {

    public static Config loadPlayerFile(String playerName) {
        String pluginFolder = BiggzAdditions.getPlugin().getDataFolder().getAbsolutePath() + File.separator;
        String playerDataFolderPath = "data" + File.separator + "playerdata" + File.separator;
        String playerFileName = playerName + ".yml";

        ensureFileExists(pluginFolder + playerDataFolderPath, playerFileName);

        return new Config(BiggzAdditions
                .getPlugin(), playerDataFolderPath + playerFileName);
    }

    private static void ensureFileExists(String path, String fileName) {
        (new File(path)).mkdirs();
        File file = new File(path + File.separator + fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
                BiggzAdditions
                        .getPlugin()
                        .getLogger()
                        .info("[BiggzAdditions] Created '" + fileName + "'");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
