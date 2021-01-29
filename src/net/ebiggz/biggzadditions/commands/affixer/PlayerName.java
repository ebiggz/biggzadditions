package net.ebiggz.biggzadditions.commands.affixer;

import net.ebiggz.biggzadditions.BiggzAdditions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerName {

	private Player player;

	public PlayerName(Player player) {
		this.player = player;
	}

	public String getName() {
		return player.getDisplayName();
	}

	public String getFullName() {
		return getPrefix() + player.getDisplayName() + getSuffix();
	}

	public String getPreview() {
		String rawPreview = getPrefix() + player.getDisplayName() + ChatColor.RESET + getSuffix();
		String translatedPreview = ChatColor.translateAlternateColorCodes('&', rawPreview);
		return translatedPreview;
	}

	public void showPreviewToPlayer() {
		player.sendMessage(ChatColor.AQUA + "Your name now looks like: " + ChatColor.RESET + getPreview());
	}

	public boolean hasPrefix() {
		return !getPrefix().isEmpty();
	}

	public String getPrefix() {
		return BiggzAdditions.getChatAPI().getPlayerPrefix(player);
	}

	public PlayerName setPrefix(String prefix) {
		BiggzAdditions.getChatAPI().setPlayerPrefix(null, player, prefix);
		return this;
	}

	public boolean hasSuffix() {
		return !getSuffix().isEmpty();
	}

	public String getSuffix() {
		return BiggzAdditions.getChatAPI().getPlayerSuffix(player);
	}

	public PlayerName setSuffix(String suffix) {
		BiggzAdditions.getChatAPI().setPlayerSuffix(null, player, suffix);
		return this;
	}
}
