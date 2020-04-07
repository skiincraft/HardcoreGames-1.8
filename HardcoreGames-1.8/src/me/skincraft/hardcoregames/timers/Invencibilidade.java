package me.skincraft.hardcoregames.timers;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.api.EntenAPI;

public class Invencibilidade {

	private static Integer runnable = null;
	private String[] messages = new String[]
			{"§eA Invencibilidade ira acabar em "};
	
	public Invencibilidade() {
		new TimersManager().setState(State.Invencibilidade);
		startRunnable();
	}
	
	public void startRunnable() {
		new TimersManager().setTimer(State.Invencibilidade, 2*60);
		runnable = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), new Runnable() {
			@Override
			public void run() {
				int tempo = new TimersManager().getTimer(State.Invencibilidade);
				if (tempo > 0) {
					if (tempo <= 5) {
						broadcast(tempo);
						for (Player p : Bukkit.getOnlinePlayers()) {
							p.setFoodLevel(20);
							p.setHealth(20);
						}
					}
					new TimersManager().setTimer(State.Invencibilidade, tempo-1);
				} else {
					iniciarPartida();
					cancelRunnable();
				}
			}
		}, 0, 20L);		
	}
	
	@SuppressWarnings("deprecation")
	public static void iniciarPartida() {
		new Andamento(2*60);
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendTitle("Boa sorte!", "invencibilidade acabou");
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 10f, 10f);
			
			new EntenAPI(Main.getMain()).refreshPlayer(player);
		}
	}
	
	private void broadcast(int tempo) {
		Integer[] timeevents = new Integer[] {1, 2, 3, 4, 5};
		for (int i = 0; i < timeevents.length;i++) {
			if (Arrays.asList(timeevents).get(i) == tempo) {
				Bukkit.broadcastMessage(messages[0] + tempo);
			}
		}
	}
	
	public static void cancelRunnable() {
		if (runnable != null) {
			Bukkit.getScheduler().cancelTask(runnable);
			runnable = null;
		}
	}

	
}
