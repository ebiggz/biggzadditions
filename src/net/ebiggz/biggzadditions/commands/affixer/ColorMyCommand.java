package net.ebiggz.biggzadditions.commands.affixer;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum NamePart {
    PREFIX,
    NAME,
    SUFFIX
}

@CommandAlias("colormy")
@Description("Lets you edit your prefix")
public class ColorMyCommand extends BaseCommand {

    final static String COLOR_CODE_REGEX = "&[a-fA-F0-9]";

    @Default
    @CommandCompletion("prefix|name|suffix @textcolors")
    public static void colorMy(Player player, NamePart part, @Conditions("colorsonly") @Flags("colorsonly") ChatColor color) {
        PlayerName playerName = new PlayerName(player);

        String colorCode = "&" + color.getChar();

        switch(part) {
            case PREFIX: {
                String newPrefix;
                String currentPrefix = playerName.getPrefix();
                if (currentPrefix.length() >= 2 &&
                        currentPrefix.substring(0, 2).matches(COLOR_CODE_REGEX)) {
                    newPrefix = currentPrefix.replaceFirst(COLOR_CODE_REGEX, colorCode);
                } else {
                    newPrefix = colorCode + currentPrefix;
                }
                playerName.setPrefix(newPrefix);
                break;
            }
            case NAME: {
                String newPrefix;
                String currentPrefix = playerName.getPrefix();
                if(currentPrefix.length() >= 4) {
                    if(Pattern.compile(COLOR_CODE_REGEX).matcher(currentPrefix.substring(2)).find()) {
                        String lastColorCodeRegex = "(?s)&[a-fA-f0-9](?!.*?&[a-fA-f0-9])";
                        newPrefix = currentPrefix.replaceFirst(lastColorCodeRegex, colorCode);
                    } else {
                        newPrefix = insertColorCodeBeforeFormatCode(currentPrefix, colorCode);
                    }
                } else {
                    newPrefix = currentPrefix + colorCode;
                }
                playerName.setPrefix(newPrefix);
                break;
            }
            case SUFFIX: {
                String newSuffix;
                String currentSuffix = playerName.getSuffix();
                if (currentSuffix.length() >= 2 &&
                        currentSuffix.substring(0, 2).matches(COLOR_CODE_REGEX)) {
                    newSuffix = currentSuffix.replaceFirst(COLOR_CODE_REGEX, colorCode);
                } else {
                    newSuffix = colorCode + currentSuffix;
                }
                playerName.setSuffix(newSuffix);
                break;
            }
            default:
                player.sendMessage(ChatColor.RED + "Sorry, that is an unknown name part.");
        }

        playerName.showPreviewToPlayer();
    }

    /**
     * Inserts a Color Code into a string before any present Format Code
     */
    private static String insertColorCodeBeforeFormatCode(String input, String colorCode) {
        Pattern formatCodeRegex = Pattern.compile("&[l-oL-ORrKk]");
        Matcher regexMatcher = formatCodeRegex.matcher(input);
        if(regexMatcher.find()) {
            if(regexMatcher.group().length() <= 2){
                int startIndex = regexMatcher.start();
                int endIndex = input.length();
                String output = input.substring(0, startIndex) +
                                colorCode +
                                input.substring(startIndex, endIndex);
                return output;
            }
        }
        return input + colorCode;
    }
}
