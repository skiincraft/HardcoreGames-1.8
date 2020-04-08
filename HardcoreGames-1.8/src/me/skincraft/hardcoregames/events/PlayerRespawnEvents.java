package me.skincraft.hardcoregames.events;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.api.CustomizationFile;
import me.skincraft.hardcoregames.api.EntenAPI;
import me.skincraft.hardcoregames.api.ItemConstruct;
import me.skincraft.hardcoregames.managers.GroupsManager;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;
import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;
import me.skincraft.hardcoregames.mysql.SQLPlayers;
import me.skincraft.hardcoregames.timers.State;
import me.skincraft.hardcoregames.timers.TimersManager;
import me.skincraft.hardcoregames.utils.SpectatorUtils;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;

public class PlayerRespawnEvents implements Listener {

	@EventHandler
	public void reviverAtDeathEvent(PlayerDeathEvent e) {

		Player player = e.getEntity();
		TimersManager timerm = new TimersManager();
		GroupsManager group = new GroupsManager(new SQLPlayers(player));
		if (!(timerm.getState() == State.Andamento)) {
			return;
		}

		List<String> l = PlayerHGManager.getList(PlayerState.ALIVE);
		if (timerm.getTimer() <= 300) {
			if (group.hasPermission(Cargos.VIP, "vip.renascer")) {
				if (l.contains(player.getName())) {
					player.setGameMode(GameMode.SURVIVAL);
					player.getInventory().clear();
					//Dar kits;
					Inventory inv = player.getInventory();
					new ItemConstruct(Material.COMPASS, null, 0)
					.setInventory(inv)
					.setSlot(8).build();
					return;
				}
			}	
		}

		if (timerm.getTimer() >= 300) {
			if (group.hasPermission(Cargos.TRIAL, "trial.admin")) {
				player.eject();
				SpectatorUtils.addPlayerSpectator(player);
				return;
			}

			if (group.hasPermission(Cargos.VIP, "vip.espectador")) {
				player.eject();
				SpectatorUtils.addPlayerSpectator(player);
				return;
			}
		}
		new PlayerHGManager(player).addDeadState();
		new EntenAPI(Main.getMain()).connect(player, "lobby");

		String[] a = new String[] 
				{"§c§lFAILED TO CONNECT", "",
						"Você morreu e foi desqualificado",
						"§ePara renascer nos primeiros 5 minutos", "§eAdquira vip na loja em: " + 
								new CustomizationFile().getWebsite()};
		player.kickPlayer(StringUtils.join(a, "/n"));
	}
	
	@EventHandler
	public void onRespawnRenove(PlayerDeathEvent e) {
		final Player p = e.getEntity();
		if (p.isDead()) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				public void run() {
					((CraftPlayer) p).getHandle().playerConnection
							.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
				}
			}, 15L);
		}
	}
	
	@EventHandler
	public void  respawnPlayer(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		GroupsManager group = new GroupsManager(new SQLPlayers(player));
		if (new TimersManager().getState() == State.Iniciando) {
			player.getInventory().clear();
			for (Player all : Bukkit.getOnlinePlayers()) {
				if (PlayerHGManager.getList(PlayerState.ADMINMODE)
						.contains(all.getName())) {
					player.showPlayer(all);
				} else {
					player.hidePlayer(all);
				}
			}
			//Send to spawn e dar itens
			player.playSound(player.getLocation(), Sound.BLAZE_HIT, 1.0F, 1.0F);
			return;
		}
		
		if (new TimersManager().getState() == State.Andamento) {
			if (new TimersManager().getTimer() <= 300) {
				if (group.hasPermission(Cargos.VIP, "vip.renascer")) {
				
					player.teleport(randomSpawnLocation());
					new EntenAPI(Main.getMain()).refreshPlayer(player);
					String[] a = new String[] {"","§cVocê acabou de renascer",
							"§eAgora você tem que se arrumar novamente!"};
					//Dar itens do kit
					player.sendMessage(a);
					return;
				}
			}
		}
	}
	
	public static Location randomSpawnLocation() {
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
