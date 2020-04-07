package me.skincraft.hardcoregames.timers;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import me.skincraft.hardcoregames.Main;

public class Invencibilidade {

	private static Integer runnable = null;
	private String[] messages = new String[]
			{"§eA Invencibilidade ira acabar em "};
	
	public Invencibilidade() {
		TimersManager state = new TimersManager();
		if (!(state.getState() == (State.Invencibilidade))) {
			return;
		}
		new TimersManager().setState(State.Invencibilidade);
		startRunnable();
	}
	
	public void startRunnable() {
		new TimersManager().setTimer(State.Invencibilidade, 2*60);
		runnable = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
			@SuppressWarnings("deprecation")
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
					new TimersManager().setTimer(State.Invencibilidade, tempo--);
				} else {
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.sendTitle("Boa sorte!", "invencibilidade acabou");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 10f, 10f);
					}
					cancelRunnable();
				}
			}
		}, 0, 20L);		
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
