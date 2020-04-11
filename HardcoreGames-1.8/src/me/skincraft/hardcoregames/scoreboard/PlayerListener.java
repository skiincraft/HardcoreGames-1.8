package me.skincraft.hardcoregames.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.api.EntenAPI;
import me.skincraft.hardcoregames.playerdeathevent.PlayerRespawnManager;
import me.skincraft.hardcoregames.timers.TimersManager;

public class PlayerListener implements Listener {
	@EventHandler(priority = EventPriority.HIGH)
	public void onJoin(final PlayerJoinEvent e) {
		SManager.getPlayers().put(e.getPlayer().getUniqueId(), new SPlayer(e.getPlayer().getName()));
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onKick(final PlayerKickEvent e) {
		SManager.getPlayers().remove(e.getPlayer().getUniqueId());
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerChangeWorld(final PlayerChangedWorldEvent e) {
		e.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onQuit(final PlayerQuitEvent e) {
		e.setQuitMessage(null);
		SManager.getPlayers().remove(e.getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void precommand(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().toLowerCase().startsWith("/ola")) {
			Player player = e.getPlayer();
			@SuppressWarnings("unused")
			String[] message = new String[] {"§3§lIsso é um holograma","		","§aParabéns §c@SkiinCraft §aVocê se tornará um desemvolvedor!","		",
					"§bOlá mundo!"};
			new EntenAPI(Main.getMain()).spawnFlyingItem(new ItemStack(Material.APPLE), player.getLocation().add(1, 0.5, 0));
			new TimersManager().changeNextState();
			player.eject();
			e.setCancelled(true);
		}
		
		if (e.getMessage().toLowerCase().startsWith("/desisto")) {
			new PlayerRespawnManager(e.getPlayer()).addSpectatorMode();
		}
	}
}
