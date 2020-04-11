package me.skincraft.hardcoregames.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.skincraft.hardcoregames.customevents.PlayerDeathByMobEvent;
import me.skincraft.hardcoregames.customevents.PlayerDeathByPlayerEvent;
import me.skincraft.hardcoregames.customevents.PlayerDeathEvent;
import me.skincraft.hardcoregames.playerdeathevent.PlayerRespawnManager;
import me.skincraft.hardcoregames.timers.TimersManager;

public class PlayerDeathEvents implements Listener {
	
	@EventHandler
	public void onPlayerDeathbyPlayer(PlayerDeathByPlayerEvent e) {
		Player player = e.getPlayer();
		//Player killer = e.getKiller();
		
		if (!new TimersManager().isAndamento()) {
			//mandar para spawn;
			return;
		}
		
		player.setHealth(20);
		int timer = new TimersManager().getTimer();
		e.setRaio(true);
		
		if (e.hasVIP()) {
			if (timer <= 300) {
				new PlayerRespawnManager(player).respawnPlayer();
				return;
			}
			if (timer >= 300) {
				new PlayerRespawnManager(player).addSpectatorMode();
			}
		}
		
		Bukkit.broadcastMessage(e.getDeathAnnounce());
		Bukkit.broadcastMessage("§e" + e.getRemains() + " jogadores restantes.");
		
		if (!e.hasVIP()) {
			new PlayerRespawnManager(player).addDeathPlayer();
		}
	}
	
	@EventHandler
	public void onPlayerDeathbyMob(PlayerDeathByMobEvent e) {
		Player player = e.getPlayer();
		//Entity killer = e.getEntity();
		
		
		if (!new TimersManager().isAndamento()) {
			//mandar para spawn;
			return;
		}
		int timer = new TimersManager().getTimer();
		e.setRaio(true);
		
		if (e.hasVIP()) {
			if (timer <= 300) {
				new PlayerRespawnManager(player).respawnPlayer();
				return;
			}
			if (timer >= 300) {
				new PlayerRespawnManager(player).addSpectatorMode();
			}
		}
		
		Bukkit.broadcastMessage(e.getDeathAnnounce());
		Bukkit.broadcastMessage("§e" + e.getRemains() + " jogadores restantes.");
		
		if (!e.hasVIP()) {
			new PlayerRespawnManager(player).addDeathPlayer();
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player player = e.getPlayer();
		//Player killer = e.getKiller();
		
		if (!new TimersManager().isAndamento()) {
			//mandar para spawn;
			return;
		}
		
		int timer = new TimersManager().getTimer();
		e.setRaio(true);
		
		if (e.hasVIP()) {
			if (timer <= 300) {
				new PlayerRespawnManager(player).respawnPlayer();
				return;
			}
			if (timer >= 300) {
				new PlayerRespawnManager(player).addSpectatorMode();
			}
		}
		
		Bukkit.broadcastMessage(e.getDeathAnnounce());
		Bukkit.broadcastMessage("§e" + e.getRemains() + " jogadores restantes.");
		
		if (!e.hasVIP()) {
			new PlayerRespawnManager(player).addDeathPlayer();
		}
	}

}
