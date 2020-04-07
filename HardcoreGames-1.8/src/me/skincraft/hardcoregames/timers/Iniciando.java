package me.skincraft.hardcoregames.timers;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.skincraft.hardcoregames.Main;

public class Iniciando {
	
	private static Integer runnable = null;
	private String[] messages = new String[]
			{"§3§l" + "INICIO » ",
			"§7A partida ira iniciar em "};
	
	public Iniciando() {
		TimersManager state = new TimersManager();
		if (!(state.getState() == (State.Iniciando))) {
			return;
		}
		new TimersManager().setState(State.Iniciando);
		startRunnable();
	}
	
	public void startRunnable() {
		new TimersManager().setTimer(State.Iniciando, 5*60);
		runnable = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				int players = 0;
				int tempo = new TimersManager().getTimer(State.Iniciando);
				moveToSpawn(); 
				if (tempo > 0) {
					if (tempo >= 30 & tempo % 30 == 0) {
						broadcastTimer(tempo);
					}
					if (tempo <= 30) {
						if (players <= 5) {
							new TimersManager().setTimer(State.Iniciando, 150);
						}
					}
					
					if (tempo <= 5) {
						broadcast(tempo);
						for (Player p : Bukkit.getOnlinePlayers()) {
							p.setAllowFlight(false);
							p.setGameMode(GameMode.SURVIVAL);
							p.playSound(p.getLocation(), Sound.CLICK, 1.0F, (byte) 1);
							p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 50));
							p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 120, 50));
							p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 120, 1000));
							p.setFoodLevel(5);
							p.closeInventory();
						}
					}
					new TimersManager().setTimer(State.Iniciando, tempo--);
				} else {
					new Invencibilidade();
					cancelRunnable();
				}
			}
		}, 0, 20L);		
	}
	
	private void broadcast(int tempo) {
		Integer[] timeevents = new Integer[] {1, 2, 3, 4, 5};
		for (int i = 0; i < timeevents.length;i++) {
			if (Arrays.asList(timeevents).get(i) == tempo) {
				Bukkit.broadcastMessage(messages[0] + messages[1] + tempo);
			}
		}
	}
	
	private void broadcastTimer(int tempo) {
		Integer[] timeevents = new Integer[] {300, 270, 240, 210, 180, 120, 60, 30};
		String convertedtimer = convertTime(tempo);
		for (int i = 0; i < timeevents.length;i++) {
			if (Arrays.asList(timeevents).get(i) == tempo) {
				Bukkit.broadcastMessage(messages[0] + messages[1] + convertedtimer);
			}
		}
	}
	
	public void moveToSpawn() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			double x = p.getLocation().getX();
			double y = p.getLocation().getY();
			if (p.getLocation().getY() <= 30) {
				//teleportar para o spawn
			}
			if (x >= 60 ||x <= -60) {
				//teleportar spawn
			}
			if (y >= 60 ||y <= -60) {
				//teleportar spawn
			}
			
		}
	}
	
	private String convertTime(Integer i) {
		int minutes = i.intValue() / 60;
		int seconds = i.intValue() % 60;
		String disMinu = (minutes < 10 ? "" : "") + minutes;
		String disSec = (seconds < 10 ? "0" : "") + seconds;
		String formattedTime = disMinu + "m " + disSec + "s";
		if (seconds == 0) {
			return disMinu + "m";
		}
		return formattedTime;
	}
	
	public static void cancelRunnable() {
		if (runnable != null) {
			Bukkit.getScheduler().cancelTask(runnable);
			runnable = null;
		}
	}

}
