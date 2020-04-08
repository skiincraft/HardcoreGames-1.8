package me.skincraft.hardcoregames.timers;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.api.EntenAPI;
import me.skincraft.hardcoregames.customevents.WinnerEvent;

public class Andamento {

	private static Integer runnable = null;
	private int startingTime;
	private String[] messages = new String[]
			{"§eUm minifeast nasceu entre: "};
	
	public Andamento(int startingTime) {
		new TimersManager().setState(State.Andamento);
		this.startingTime = startingTime;
		startRunnable();
	}
	
	public void startRunnable() {
		new TimersManager().setTimer(State.Andamento, startingTime);
		runnable = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				int tempo = new TimersManager().getTimer(State.Andamento);
				new TimersManager().setTimer(State.Andamento, tempo+1);
				new WinnerEvent().verificarWin();
				
				minifeastSpawns(new TimersManager().getTimer(State.Andamento));
				
				if(tempo == 2100) {
					//criar arena final
					for (Player player : Bukkit.getOnlinePlayers()) {
						player.sendTitle("§6§lFINAL BATTLE", null);
						new EntenAPI(Main.getMain()).sendActionbar("§5Você esta na arena final!", player);
					}
				}
				
				if (tempo == 2100 + (5*60)) {
					//Acabar partida;
				}
				
				
			}
		}, 0, 20L);		
	}
	
	private void minifeastSpawns(int tempo) {
		Integer[] timeevents = new Integer[] {210, 450, 750, 1050, 1350, 1800, 2000};
		for (int i = 0; i < timeevents.length;i++) {
			if (Arrays.asList(timeevents).get(i) == tempo) {
				Bukkit.broadcastMessage(messages[0]);
				//spawnarMinifeast
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
