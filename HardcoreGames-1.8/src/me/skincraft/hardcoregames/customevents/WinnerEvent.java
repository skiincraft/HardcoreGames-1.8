package me.skincraft.hardcoregames.customevents;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.api.EntenAPI;
import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;
import me.skincraft.hardcoregames.timers.State;
import me.skincraft.hardcoregames.timers.TimersManager;

public class WinnerEvent {

	public WinnerEvent() {
		
	}
	
	private static void sendmessages(Player p, String... messages) {
		p.sendMessage(messages);
	}
	
	public void verificarWin() {
		if (!(new TimersManager().getState() == State.Andamento)) {
			return;
		}
		int playersVivos = PlayerHGManager.getSize(PlayerState.ALIVE);
		if (playersVivos == 0) {
			invalidUsable(Type.noPlayers);
		}
		
		if (playersVivos != 1) {
			return;
		}
		
		new TimersManager().setState(State.Finalizando);
		for (String playername : PlayerHGManager.getList(PlayerState.ALIVE)) {
			Player player = Bukkit.getPlayer(playername);
			player.getInventory().clear();
			new EntenAPI(Main.getMain()).spawnFlyingItem(new ItemStack(Material.CAKE), player.getLocation().add(1, 1.5, 0));
			
			sendmessages(player, "", "§e§lParabens!", "§eVocê ganhou a partida no " + Main.getMain().servername + "!",
					"§acom §e§l" + new PlayerHGManager(player).getPlayerStreak() + " KILLS§7.", "");
			
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
			player.setGameMode(GameMode.ADVENTURE);
			
			tasksRunnable(player);
		}
	}
	
	private static void tasksRunnable(Player player) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				spawnRandomFirework(player.getLocation().add(0, 1, 0));
			}
		}, 2 * 20, 3 * 20);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage("§2§lVENCEDOR §2" + player.getName() + "§7 venceu a partida.");
			}
		}, 0L, 2 * 20L);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					new EntenAPI(Main.getMain()).connect(p, "lobby");
				}
			}
		}, 20 * 22);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {

				Bukkit.shutdown();
			}
		}, 20 * 25);
	}
	
	public static void spawnRandomFirework(Location loc) {
		Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();
		Random r = new Random();
		int rt = r.nextInt(4) + 1;
		FireworkEffect.Type type = FireworkEffect.Type.BALL;
		if (rt == 1) {
			type = FireworkEffect.Type.BALL;
		}

		if (rt == 2) {
			type = FireworkEffect.Type.BALL_LARGE;
		}
		if (rt == 3) {
			type = FireworkEffect.Type.BURST;
		}
		if (rt == 4) {
			type = FireworkEffect.Type.CREEPER;
		}
		if (rt == 5) {
			type = FireworkEffect.Type.STAR;
		}
		Color c1 = Color.RED;
		Color c2 = Color.YELLOW;
		Color c3 = Color.ORANGE;

		FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withColor(c2)
				.withFade(c3).with(type).trail(r.nextBoolean()).build();
		fwm.addEffect(effect);
		int rp = r.nextInt(2) + 1;
		fwm.setPower(rp);

		EntityType entity = EntityType.CHICKEN;
		World getWorld = Bukkit.getWorld(fw.getWorld().getName());
		Entity galinha = getWorld.spawnEntity(loc, entity);

		fw.setPassenger(galinha);
		fw.setFireworkMeta(fwm);

	}

	
	private enum Type {
		noPlayers;
	}
	
	private void invalidUsable(Type type) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.kickPlayer("§4§lServidor Reiniciando!" + "\n" + 
		                      "§7O jogo foi encerrado pois o §7§lSISTEMA não" + "\n" +
					          "§7encontrou nenhum player em estado §e§lALIVE" + "\n" +
		                      "\n§fEntre em contato com o Developer");
		}
	}
	
}
