package me.skincraft.hardcoregames.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.skincraft.hardcoregames.api.LystMCCommands;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;
import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;
import me.skincraft.hardcoregames.playerdeathevent.PlayerRespawnManager;

public class ForfeitCommand extends LystMCCommands {

	public ForfeitCommand() {
		super("desistir", "Comando para desistir de uma partida.", Arrays.asList("forfeit"));
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!isPlayer(sender)) {
			return true;
		}
		
		Player player = (Player) sender;
		if (!hasPermission(player, Cargos.VIP, "vip.desistir")) {
			sendPermissionMessage(player);
			return true;
		}
		
		// Verificar se está em combate
		
		List<String> spec = PlayerHGManager.getList(PlayerState.SPECTATOR);
		List<String> alive = PlayerHGManager.getList(PlayerState.ALIVE);
		if (args.length == 0) {
			if (spec.contains(player.getName())) {
				player.sendMessage("Você não pode desistir da partida!");
				return true;
			} else if (alive.contains(player.getName())) {
				broadcast("§7" + player.getName() + " desistiu da partida e foi desclassificado.");
				new PlayerRespawnManager(player).addSpectatorMode();
			}
			return true;
		}
				
		return false;
	}

}
