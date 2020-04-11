package me.skincraft.hardcoregames.playerdeathevent;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.api.CustomizationFile;
import me.skincraft.hardcoregames.api.EntenAPI;
import me.skincraft.hardcoregames.api.ItemConstruct;
import me.skincraft.hardcoregames.managers.GroupsManager;
import me.skincraft.hardcoregames.managers.KitManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;
import me.skincraft.hardcoregames.mysql.SQLPlayers;
import me.skincraft.hardcoregames.timers.State;
import me.skincraft.hardcoregames.timers.TimersManager;

public class PlayerRespawnManager {
	
	private Player player;
	
	public PlayerRespawnManager(Player player) {
		this.player = player;
	}
	
	public void respawnPlayer() {
		if (new TimersManager().getState() != State.Andamento) {
			return;
		}
		
		if (!new GroupsManager(new SQLPlayers(player)).hasPermission(Cargos.VIP, "vip.renascer")) {
			return;
		}
		
		if (PlayerHGManager.getList(PlayerState.SPECTATOR).contains(player.getName())) {
			return;
		}
		new PlayerHGManager(player).addAliveState();
		
		String[] a = new String[] {"","§cVocê acabou de renascer",
		"§eAgora você tem que se arrumar novamente!"};
		
		player.sendMessage(a);
		player.setGameMode(GameMode.SURVIVAL);
		player.setHealth(20);
		new EntenAPI(Main.getMain()).refreshPlayer(player);
		player.getInventory().clear();
		
		//Item do kit
		new ItemConstruct(Material.COMPASS, "§cBussola", 0)
		.setInventory(player.getInventory()).setSlot(8).build();
		player.teleport(randomSpawnLocation());
	}
	
	public void addSpectatorMode() {
		if (new TimersManager().getState() != State.Andamento) {
			return;
		}
		
		if (!new GroupsManager(new SQLPlayers(player)).hasPermission(Cargos.VIP, "vip.espectar")) {
			return;
		}
		
		player.getInventory().clear();
		player.eject();
		new PlayerHGManager(player).addSpectatorState();
		
		new ItemConstruct(Material.WOOD_PLATE, "§cJogador Anterior", 3, player.getInventory(), "", "§7Utilize este item para teleportar", "§7para players que estão vivos.").build();
		new ItemConstruct(Material.SLIME_BALL, "§6Jogadores Restantes", 4, player.getInventory(), "", "§7Utilize este item para teleportar", "§7para players que estão vivos.").build();
		new ItemConstruct(Material.STONE_PLATE, "§aProximo Jogador", 5, player.getInventory(), "", "§7Utilize este item para teleportar", "§7para players que estão vivos.").build();
		
		player.setGameMode(GameMode.SURVIVAL);
		player.setAllowFlight(true);
		player.setFlying(true);
		player.updateInventory();
		
		Vector vetor = player.getLocation().getDirection().multiply(1.35D).setY(3.0D);
		player.setVelocity(vetor);
		
		player.sendMessage("§eVocê entrou no modo espectador!");

		KitManager kit = new KitManager(player);
		kit.removeKit1();
		kit.removeKit2();
		
	}
	
	public void addDeathPlayer() {
		String[] a = new String[] 
				{"§c§lFAILED TO CONNECT", "",
						"Você morreu e foi desqualificado",
						"§ePara renascer nos primeiros 5 minutos", "§eAdquira vip na loja em: " + 
								new CustomizationFile().getWebsite()};
		
		new PlayerHGManager(player).addDeadState();
		if (!player.isOnline()) {
			return;
		}
		
		if (Main.getMain().bungeecord) {
			player.sendMessage(a);
			new EntenAPI(Main.getMain()).connect(player, "lobby");
			return;
		}

		player.kickPlayer(StringUtils.join(a, "\n"));

	}
	
	private Location randomSpawnLocation() {
		Random spawnRandom = new Random();
		Location local = null;
		
		int x = spawnRandom.nextInt(180) + 1;
		int y = 149;
		int z = spawnRandom.nextInt(180) + 1;
		boolean blocoBaixo = false;
		
		while (!blocoBaixo) {
			local = new Location(Bukkit.getWorld("world"), x, y, z);
			if (local.getBlock().getType() != Material.AIR) {
				blocoBaixo = true;
			} else {
				y--;
			}
		}
		return local.add(0, 2, 0);
	}

}
