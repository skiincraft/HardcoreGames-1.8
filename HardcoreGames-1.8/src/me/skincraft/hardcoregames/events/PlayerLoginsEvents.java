package me.skincraft.hardcoregames.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.api.CustomizationFile;
import me.skincraft.hardcoregames.api.UtilsAPI;
import me.skincraft.hardcoregames.managers.GroupsManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;
import me.skincraft.hardcoregames.mysql.SQLPlayers;
import me.skincraft.hardcoregames.playerdeathevent.PlayerRespawnManager;
import me.skincraft.hardcoregames.timers.TimersManager;

public class PlayerLoginsEvents implements Listener {

	UtilsAPI util = new UtilsAPI(Main.getMain());
	
	@EventHandler
	public void checkPlayerLogin(PlayerLoginEvent e) {
		Player player = e.getPlayer();
		GroupsManager group = new GroupsManager(new SQLPlayers(player));
		String[] str = new String[] 
				{"§c§lFAILED TO CONNECT","",
						"Esse usuário já está conectado no servidor."};
		
		if (player.isOnline()) {
			if (group.hasPermission(Cargos.VIP, "vip.espectar")) {
				e.disallow(Result.KICK_OTHER, StringUtils.join(str, "\n"));
			}
		}
	}
	
	public static Map<String, String> combatlog = new HashMap<>();
	public static List<String> deslogouCombat = new ArrayList<String>();
	public static Map<String, Boolean> relog = new HashMap<>();
	
	@EventHandler
	public void CombatLogEvent(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		
		Player player = (Player) e.getEntity();
		Player damager = (Player) e.getDamager();
		
		if (combatlog.containsKey(player.getName())) {
			if (combatlog.get(player.getName())
					.equalsIgnoreCase(damager.getName())) {
				return;
			}
			combatlog.remove(player.getName());
		}
		
		if (combatlog.containsKey(damager.getName())) {
			if (combatlog.get(damager.getName())
					.equalsIgnoreCase(player.getName())) {
				return;
			}
			combatlog.remove(damager.getName());
		}
		
		combatlog.put(player.getName(), damager.getName());
		combatlog.put(damager.getName(), player.getName());
		
		Bukkit.getScheduler().runTaskLater(Main.getMain(), new Runnable() {
			
			@Override
			public void run() {
				if (combatlog.get(player.getName()).equals(damager.getName())) {
				combatlog.remove(player.getName());
				combatlog.remove(damager.getName());
				}
				
			}
		}, 10*20L);
	}
	
	@EventHandler
	public void combatQuitEvent(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		if (!new TimersManager().isAndamento()) {
			return;
		}
		
		if (combatlog.containsKey(e.getPlayer().getName())) {
			deslogouCombat.add(e.getPlayer().getName());
			return;
		}
		String name = e.getPlayer().getName();
		
		relog.put(name, true);
		Bukkit.getScheduler().runTaskLater(Main.getMain(), new Runnable() {
			
			@Override
			public void run() {
				relog.put(name, false);
				if (!e.getPlayer().isOnline()) {
					Bukkit.broadcastMessage("§7" + name + " demorou muito para relogar.");
					PlayerRespawnManager mm = new PlayerRespawnManager(e.getPlayer());					
					mm.addDeathPlayer();
					if (PlayerHGManager.getList(PlayerState.ALIVE).contains(name)) {
						new PlayerHGManager(e.getPlayer()).addDeadState();
					}
				}
			}
		}, 30*20L);//(1*60+30)*20L);//1m 30s
	}
	
	@EventHandler
	public void progressMatchLoginEvent(PlayerLoginEvent e) {
		Player player = e.getPlayer();
		if (!new TimersManager().isAndamento()) {
			return;
		}
		PlayerRespawnManager mm = new PlayerRespawnManager(player);	
		GroupsManager group = new GroupsManager(new SQLPlayers(player));
		
		if (relog.containsKey(player.getName())) {
			if (relog.get(player.getName()) == true) {
				relog.remove(player.getName());
				e.allow();
				return;
			}
			
			if (relog.get(player.getName()) == false) {
				if (group.hasPermission(Cargos.VIP, "vip.espectar")) {
					e.allow();	
					util.SendTaskLaterMessage(player, 1, "§cVocê demorou muito para relogar e foi desqualificado.");
					
					if (new TimersManager().getTimer() >= 300) {
						player.eject();
						
						Bukkit.getScheduler().runTaskLater(Main.getMain(), new Runnable() {
							@Override
							public void run() {
								mm.addSpectatorMode();
							}
						}, 20L);
						return;
					}
					
					e.allow();
										
					Bukkit.getScheduler().runTaskLater(Main.getMain(), new Runnable() {
						@Override
						public void run() {
							mm.respawnPlayer();
						}
					}, 20L);
					return;
				}
				String[] str = new String[] {
						"§c§lFAILED TO CONNECT","",
						"§cVocê demorou muito para relogar e foi desqualificado","",
						"§ePara renascer nos primeiros 5 minutos", "§eAdquira vip na loja em: " + 
						new CustomizationFile().getWebsite()};
				new PlayerHGManager(player).addDeadState();
				e.disallow(Result.KICK_OTHER, StringUtils.join(str, "\n"));
				return;
			}
		}
		
		if (PlayerHGManager.getList(PlayerState.DEAD).contains(player.getName())) {
			if (group.hasPermission(Cargos.VIP, "vip.espectar")) {
				e.allow();
				util.SendTaskLaterMessage(player, 1, "§cVocê morreu e foi desqualificado!");
				if (new TimersManager().getTimer() >= 300) {
					player.eject();
					Bukkit.getScheduler().runTaskLater(Main.getMain(), new Runnable() {
						@Override
						public void run() {
							mm.addSpectatorMode();
						}
					}, 20L);
					deslogouCombat.remove(player.getName());
					return;
				}			
				Bukkit.getScheduler().runTaskLater(Main.getMain(), new Runnable() {
					@Override
					public void run() {
						mm.respawnPlayer();
					}
				}, 20L);
				return;
			}
			String[] str = new String[] {
					"§c§lFAILED TO CONNECT","",
					"§cVocê morreu e foi desqualificado!","",
					"§ePara renascer nos primeiros 5 minutos", "§eAdquira vip na loja em: " + 
					new CustomizationFile().getWebsite()};
			new PlayerHGManager(player).addDeadState();
			e.disallow(Result.KICK_OTHER, StringUtils.join(str, "\n"));
			return;
		}

		if (deslogouCombat.contains(player.getName())) {
			if (group.hasPermission(Cargos.VIP, "vip.espectar")) {
				e.allow();
				util.SendTaskLaterMessage(player, 1, "§cVocê deslogou em combate, e foi desqulificado.");
				if (new TimersManager().getTimer() >= 300) {
					player.eject();
					Bukkit.getScheduler().runTaskLater(Main.getMain(), new Runnable() {
						@Override
						public void run() {
							mm.addSpectatorMode();
						}
					}, 20L);
					deslogouCombat.remove(player.getName());
					return;
				}			
				Bukkit.getScheduler().runTaskLater(Main.getMain(), new Runnable() {
					@Override
					public void run() {
						mm.respawnPlayer();
					}
				}, 20L);
				return;
			}
			String[] str = new String[] {
					"§c§lFAILED TO CONNECT","",
					"§cVocê deslogou em combate e foi desqualificado.","",
					"§ePara renascer nos primeiros 5 minutos", "§eAdquira vip na loja em: " + 
					new CustomizationFile().getWebsite()};
			new PlayerHGManager(player).addDeadState();
			e.disallow(Result.KICK_OTHER, StringUtils.join(str, "\n"));
			return;
		}
		
		if (new TimersManager().getTimer() >=300) {
			if (group.hasPermission(Cargos.VIP, "vip.espectar")) {
				player.eject();
				util.SendTaskLaterMessage(player, 1, "§cVocê conectou em uma partida em andamento");
				Bukkit.getScheduler().runTaskLater(Main.getMain(), new Runnable() {
					@Override
					public void run() {
						mm.addSpectatorMode();
					}
				}, 20L);
				return;
			}			
			Bukkit.getScheduler().runTaskLater(Main.getMain(), new Runnable() {
				@Override
				public void run() {
					mm.respawnPlayer();
				}
			}, 15L);
			return;
		}
	}
	
	@EventHandler
	public void serverFullLogin(PlayerLoginEvent e) {
		Player player = e.getPlayer();
		if (!new TimersManager().isIniciando()) {
			return;
		}
		String[] str = new String[] 
				{"§c§lFAILED TO CONNECT","",
						"§cEsse servidor está lotado!","",
				        "§ePara ter acesso a servidores lotados,",
				        "§eAcesse a loja em: " + new CustomizationFile().getWebsite().replace("&", "§")};
		GroupsManager group = new GroupsManager(new SQLPlayers(player));
		if (e.getResult() == Result.KICK_FULL) {
			if (group.hasPermission(Cargos.VIP, "vip.serverfull")) {
				util.SendTaskLaterMessage(player, 1, "§bVocê conectou em uma servidor lotado.");
				e.allow();
				return;
			}
			
			String full = StringUtils.join(str, "\n");
			
			e.setKickMessage(full);
			e.disallow(PlayerLoginEvent.Result.KICK_OTHER, e.getKickMessage());
		}
		
	}
	
}
