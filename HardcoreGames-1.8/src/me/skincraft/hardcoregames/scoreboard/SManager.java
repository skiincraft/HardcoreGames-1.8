package me.skincraft.hardcoregames.scoreboard;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.skincraft.hardcoregames.Main;

@SuppressWarnings("all")
public class SManager {
	private static ConcurrentHashMap<UUID, SPlayer> players;

	static {
		SManager.players = new ConcurrentHashMap<UUID, SPlayer>();
	}

	public static ConcurrentHashMap<UUID, SPlayer> getPlayers() {
		return SManager.players;
	}

	public static void onEnable() {
		for (final Player player : Bukkit.getOnlinePlayers()) {
			SManager.players.put(player.getUniqueId(), new SPlayer(player.getName()));
			player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
		Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin) Main.getMain(), (Runnable) new Runnable() {
			@Override
			public synchronized void run() {
				for (final SPlayer player : SManager.players.values()) {
					player.updateScoreboard();
				}
			}
		}, 10L, 10L);
	}
}
