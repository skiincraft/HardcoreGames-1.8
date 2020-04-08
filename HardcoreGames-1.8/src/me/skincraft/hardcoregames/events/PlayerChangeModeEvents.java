package me.skincraft.hardcoregames.events;

import org.bukkit.entity.Player;

import me.skincraft.hardcoregames.managers.GroupsManager;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;
import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;
import me.skincraft.hardcoregames.mysql.SQLPlayers;
import me.skincraft.hardcoregames.timers.TimersManager;

public class PlayerChangeModeEvents {
	
	public void adminPermToPlay(Player player) {
		if (PlayerHGManager.getList(PlayerState.ADMINMODE).contains(player.getName())) {
			if (new TimersManager().isInvencibilidade()) {
				new PlayerHGManager(player).addAliveState();
				return;
			}
			GroupsManager group = new GroupsManager(new SQLPlayers(player));
			if (group.hasPermission(Cargos.MODPLUS, "modplus.play")) {
				new PlayerHGManager(player).addAliveState();
				return;
			}
			player.sendMessage("Você não pode mais participar da partida, pois entrou em modo administrador.");
			return;
		}
	}

}
