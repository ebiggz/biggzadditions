package net.ebiggz.biggzadditions.commands;

import co.aikar.commands.BukkitCommandIssuer;
import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.CommandCompletionContext;
import co.aikar.commands.ConditionFailedException;
import net.ebiggz.biggzadditions.commands.admin.BackCommand;
import net.ebiggz.biggzadditions.commands.admin.HomeCommand;
import net.ebiggz.biggzadditions.commands.admin.ToggleFireworksCommand;
import net.ebiggz.biggzadditions.commands.admin.deathledger.DeathLedgerCommand;
import net.ebiggz.biggzadditions.commands.affixer.*;
import net.ebiggz.biggzadditions.commands.compass.CompassCommand;
import net.ebiggz.biggzadditions.commands.dreamworld.DreamCommand;
import net.ebiggz.biggzadditions.commands.dreamworld.WakeupCommand;
import net.ebiggz.biggzadditions.commands.misc.*;
import net.ebiggz.biggzadditions.commands.nickname.NicknameCommand;
import net.ebiggz.biggzadditions.commands.nickname.NicknameOtherCommand;
import net.ebiggz.biggzadditions.commands.res.TrustCommand;
import net.ebiggz.biggzadditions.commands.res.UntrustCommand;
import net.ebiggz.biggzadditions.constants.WorldName;
import net.ebiggz.biggzadditions.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandsSetup {

    public static void registerCommands(BukkitCommandManager commandManager) {
        commandManager.registerCommand(new DeathLedgerCommand());
        commandManager.registerCommand(new EditSignCommand());
        commandManager.registerCommand(new BackCommand());
        commandManager.registerCommand(new HomeCommand());
        commandManager.registerCommand(new ToggleFireworksCommand());
        commandManager.registerCommand(new HearthCommand());
        commandManager.registerCommand(new ColorsCommand());
        commandManager.registerCommand(new PrefixCommand());
        commandManager.registerCommand(new SuffixCommand());
        commandManager.registerCommand(new ColorMyCommand());
        commandManager.registerCommand(new CompassCommand());
        commandManager.registerCommand(new LastDeathCommand());
        commandManager.registerCommand(new DreamCommand());
        commandManager.registerCommand(new WakeupCommand());
        commandManager.registerCommand(new TrustCommand());
        commandManager.registerCommand(new UntrustCommand());
        commandManager.registerCommand(new BreedCommand());
        commandManager.registerCommand(new PortalCommand());
        commandManager.registerCommand(new MapCommand());
        commandManager.registerCommand(new PhantomResetCommand());
        commandManager.registerCommand(new NicknameCommand());
        commandManager.registerCommand(new NicknameOtherCommand());
        commandManager.registerCommand(new WeatherCommand());
    }

    private final static List<String> VALID_TEXT_COLORS = Arrays.stream(ChatColor.values())
            .filter(color -> color.isColor())
            .map(color -> Utils.simplifyString(color.name()))
            .collect(Collectors.toList());

    private final static List<String> VALID_TEXT_FORMATS = Arrays.stream(ChatColor.values())
            .filter(color -> color.isFormat() && color != ChatColor.MAGIC)
            .map(color -> Utils.simplifyString(color.name()))
            .collect(Collectors.toList());

    public static void registerCommandCompletions(BukkitCommandManager commandManager) {

        commandManager.getCommandCompletions().registerCompletion("prefix", c ->
                Arrays.asList(new PlayerName(c.getPlayer()).getPrefix()));

        commandManager.getCommandCompletions().registerCompletion("suffix", c ->
                Arrays.asList(new PlayerName(c.getPlayer()).getSuffix()));

        commandManager.getCommandCompletions().registerCompletion("textcolors", c -> VALID_TEXT_COLORS);
        commandManager.getCommandCompletions().registerCompletion("textformats", c -> VALID_TEXT_FORMATS);

        commandManager.getCommandConditions().addCondition(ChatColor.class, "colorsonly", (c, exec, value) -> {
            if (value == null) {
                return;
            }
            if (value.isFormat()) {
                throw new ConditionFailedException(ChatColor.YELLOW + "Colors must be one of: " + ChatColor.RESET +
                        Arrays.stream(ChatColor.values())
                                .filter(color -> color.isColor())
                                .map(color -> color + Utils.simplifyString(color.name()))
                                .collect(Collectors.joining(ChatColor.RESET + ", ")));
            }
        });

        commandManager.getCommandConditions().addCondition(ChatColor.class, "formatsonly", (c, exec, value) -> {
            if (value == null) {
                return;
            }
            if (value.isColor()) {
                throw new ConditionFailedException(ChatColor.YELLOW + "Format must be one of: " + ChatColor.RESET + VALID_TEXT_FORMATS
                        .stream()
                        .collect(Collectors.joining(ChatColor.RESET + ", ")));
            }
        });

        commandManager.getCommandCompletions().registerCompletion("suffix", c ->
                Arrays.asList(new PlayerName(c.getPlayer()).getSuffix()));

        commandManager.getCommandCompletions().registerCompletion("allnames", c -> {
            return Stream.concat(
                    Bukkit.getOnlinePlayers()
                            .stream()
                            .map(Player::getName),
                    Arrays.asList(Bukkit.getOfflinePlayers())
                            .stream()
                            .map(OfflinePlayer::getName)
            ).collect(Collectors.toList());
        });

        commandManager.getCommandCompletions().registerCompletion("signtext", c -> {
            Block block = c.getPlayer().getTargetBlock(null, 5);
            if(block != null) {
                BlockState blockState = block.getState();
                if(blockState instanceof Sign) {
                    Sign sign = (Sign) blockState;

                    List<String> args = getCompletionContextArgs(c);
                    if(args.size() > 0) {
                        String firstArg = args.get(0);
                        if(firstArg.matches("\\d+")) {
                            int lineNumber = Integer.parseInt(firstArg);
                            if(lineNumber >= 1 & lineNumber <= 4) {
                                String text = sign.getLine(lineNumber - 1);
                                if(text != null) {
                                    return Arrays.asList(text);
                                }
                            }
                        }
                    }

                    return Arrays.asList(sign.getLines())
                            .stream()
                            .map(s -> s.replaceAll("§", "&"))
                            .collect(Collectors.toList());
                }
            }
            return new ArrayList<>();
        });
    }

    public static void registerCommandConditions(BukkitCommandManager commandManager) {
        commandManager.getCommandConditions().addCondition("dreamworld", (context) -> {
            BukkitCommandIssuer issuer = context.getIssuer();
            if(!issuer.getPlayer().getWorld().getName().equals(WorldName.DREAM_WORLD)) {
                throw new ConditionFailedException("You must be in the Dreamworld to use this command.");
            }
        });

        commandManager.getCommandConditions().addCondition("mainworld", (context) -> {
            BukkitCommandIssuer issuer = context.getIssuer();
            if(!issuer.getPlayer().getWorld().getName().equals(WorldName.MAIN_WORLD)) {
                throw new ConditionFailedException("You must be in the main overworld to use this command.");
            }
        });
    }

    @SuppressWarnings("unchecked")
    private static List<String> getCompletionContextArgs(CommandCompletionContext context) {
        List<String> args = new ArrayList<String>();
        try {
            Field argsField = CommandCompletionContext.class.
                    getDeclaredField("args");

            argsField.setAccessible(true);

            args = (List<String>) argsField.get(context);
        } catch(NoSuchFieldException exception) {
            Bukkit.getLogger().info("No such field exception");
        } catch (IllegalAccessException exception) {
            Bukkit.getLogger().info("Illegal access exception");
        }
        return args;
    }
}
