package me.skincraft.hardcoregames.events;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.api.CustomizationFile;
import me.skincraft.hardcoregames.api.EntenAPI;
import me.skincraft.hardcoregames.commands.AdminCommand;
import me.skincraft.hardcoregames.managers.GroupsManager;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;
import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.managers.RanksManager;
import me.skincraft.hardcoregames.managers.StatusManagers;
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;
import me.skincraft.hardcoregames.mysql.SQLPlayers;
import me.skincraft.hardcoregames.timers.State;
import me.skincraft.hardcoregames.timers.TimersManager;

public class PlayerJoinsEvents implements Listener {
	
	@EventHandler
	public void staffJoinEvent(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (new GroupsManager(new SQLPlayers(player)).hasPermission(Cargos.TRIAL, "trial.admin")) {
			new EntenAPI(Main.getMain()).sendActionbar("§cVocê está no modo admin", player);
			AdminCommand.addAdmin(player);
		}
	}
		
	@EventHandler
	public void AdminmodeJoins(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			List<String> adminlist = PlayerHGManager.getList(PlayerState.ADMINMODE);
			
			if (p.getName() != player.getName()) {
				if (adminlist.contains(player.getName())) {
					p.hidePlayer(player);
				}	
			}
		}
	}
	
	@EventHandler
	public void playerItemJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (!(new TimersManager().getState() == State.Iniciando)) {
			return;
		}
		
		player.getInventory().clear();
		//hotbar itens
		//teleporttospawn	
	}
	
	@EventHandler
	public void playerStateJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (new TimersManager().getState() == State.Iniciando) {
			new PlayerHGManager(player).addHoldState();
			return;
		}
	}
	
	@EventHandler
	public void playerCommonJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (new TimersManager().getState() == State.Iniciando) {
			for (Player players : Bukkit.getOnlinePlayers()) {
				if (PlayerHGManager.getList(PlayerState.ADMINMODE).contains(players.getName())) {
					player.hidePlayer(players);
					return;
				}
				player.showPlayer(player);
				players.showPlayer(player);
			}
		}
	}
	
	@EventHandler
	public void createANewPlayer(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		Player player = e.getPlayer();
		SQLPlayers sql = new SQLPlayers(player);
		StatusManagers status = new StatusManagers(sql);
		RanksManager rank = new RanksManager(sql);
		GroupsManager group = new GroupsManager(sql);
		
		status.criar();
		rank.criar();
		group.criar();
		sql.criar();
		
		List<String> cima = new CustomizationFile().getTablistHeater();
		List<String> baixo = new CustomizationFile().getTablistFooter();
		
		new EntenAPI(Main.getMain()).sendTabTitle(player, StringUtils.join(cima, ""), StringUtils.join(baixo, ""));
		
		if (!(new TimersManager().getState() == State.Iniciando)) return;
		
		player.sendMessage("§eSeu rank foi carregado como " + rank.getRank().getEditedName());
	}
	
	
}
