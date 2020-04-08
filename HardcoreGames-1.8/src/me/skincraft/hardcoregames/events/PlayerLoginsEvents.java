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
import me.skincraft.hardcoregames.api.EntenAPI;
import me.skincraft.hardcoregames.managers.GroupsManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;
import me.skincraft.hardcoregames.mysql.SQLPlayers;
import me.skincraft.hardcoregames.timers.TimersManager;
import me.skincraft.hardcoregames.utils.SpectatorUtils;

public class PlayerLoginsEvents implements Listener {

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
			}
		}, (2*60)*20L);
	}
	
	@EventHandler
	public void progressMatchLoginEvent(PlayerLoginEvent e) {
		Player player = e.getPlayer();
		if (!new TimersManager().isAndamento()) {
			return;
		}
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
					player.sendMessage("§cVocê demorou muito para relogar e foi desqualificado.");
					if (new TimersManager().getTimer() >= 300) {
						player.eject();
						SpectatorUtils.addPlayerSpectator(player);
						return;
					}
					
					e.allow();
					player.teleport(PlayerRespawnEvents.randomSpawnLocation());
					new EntenAPI(Main.getMain()).refreshPlayer(player);
					String[] a = new String[] {"","§cVocê acabou de renascer",
							"§eAgora você tem que se arrumar novamente!"};
					//Dar itens do kit
					player.sendMessage(a);
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
				player.sendMessage("§cVocê morreu e foi desqualificado!");
				if (new TimersManager().getTimer() >= 300) {
					player.eject();
					SpectatorUtils.addPlayerSpectator(player);
					deslogouCombat.remove(player.getName());
					return;
				}
				player.teleport(PlayerRespawnEvents.randomSpawnLocation());
				new EntenAPI(Main.getMain()).refreshPlayer(player);
				String[] a = new String[] {"","§cVocê acabou de renascer",
						"§eAgora você tem que se arrumar novamente!"};
				//Dar itens do kit
				player.sendMessage(a);
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
			if (group.hasPermission(Cargos.VIP, "vip.serverfull")) {
				e.allow();
				player.sendMessage("§cVocê deslogou em combate, e foi desqulificado.");
				if (new TimersManager().getTimer() >= 300) {
					player.eject();
					SpectatorUtils.addPlayerSpectator(player);
					deslogouCombat.remove(player.getName());
					return;
				}
				player.teleport(PlayerRespawnEvents.randomSpawnLocation());
				new EntenAPI(Main.getMain()).refreshPlayer(player);
				String[] a = new String[] {"","§cVocê acabou de renascer",
						"§eAgora você tem que se arrumar novamente!"};
				//Dar itens do kit
				player.sendMessage(a);
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
				SpectatorUtils.addPlayerSpectator(player);
				return;
			}
			player.teleport(PlayerRespawnEvents.randomSpawnLocation());
			new EntenAPI(Main.getMain()).refreshPlayer(player);
			String[] a = new String[] {"","§cVocê acabou de renascer",
					"§eAgora você tem que se arrumar novamente!"};
			//Dar itens do kit
			player.sendMessage(a);
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
						"§cEsse servidor está lotado!.","",
				        "§ePara ter acesso a servidores lotados,", "",
				        "§eAcesse a loja em: " + new CustomizationFile().getWebsite()};
		GroupsManager group = new GroupsManager(new SQLPlayers(player));
		if (e.getResult() == Result.KICK_FULL) {
			if (group.hasPermission(Cargos.VIP, "vip.serverfull")) {
				e.allow();
				return;
			}
			
			e.setKickMessage(StringUtils.join(str, "\n"));
			e.disallow(PlayerLoginEvent.Result.KICK_OTHER, e.getKickMessage());
		}
		
	}
	
}
