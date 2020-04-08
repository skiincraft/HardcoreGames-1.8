package me.skincraft.hardcoregames.scoreboard;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.api.CustomizationFile;
import me.skincraft.hardcoregames.timers.State;
import me.skincraft.hardcoregames.timers.TimersManager;

public class SPlayer {
	private String player;
	private ScoreB sb;

	public String scoretitle = Main.getMain().servername;
	public String site = new CustomizationFile().getWebsite();
	
	public SPlayer(final String player) {
		this.player = player;
		(this.sb = new ScoreB()).setSlot(DisplaySlot.SIDEBAR);
		this.sb.setName("  " + scoretitle + "  ");
		
		if (new TimersManager().getState() == State.Iniciando) {
			List<String> start = new CustomizationFile().getScoreboard(State.Iniciando, Bukkit.getPlayer(player));
			
			for (int i = 0; i < start.size();i++) {
				this.sb.addLine(start.size()-(i+1), start.get(i));
			}
		}
		
		if (new TimersManager().getState() == State.Invencibilidade) {
			List<String> start = new CustomizationFile().getScoreboard(State.Invencibilidade, Bukkit.getPlayer(player));
			
			for (int i = 0; i < start.size();i++) {
				this.sb.addLine(start.size()-(i+1), start.get(i));
			}
		}

		if (new TimersManager().getState() == State.Andamento) {
			List<String> start = new CustomizationFile().getScoreboard(State.Andamento, Bukkit.getPlayer(player));
			
			for (int i = 0; i < start.size();i++) {
				this.sb.addLine(start.size()-(i+1), start.get(i));
			}
		}
		
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(this.player);
	}

	public synchronized void updateScoreboard() {
		if (!this.sb.hasBoard(this.getPlayer())) {
			this.sb.setForPlayer(this.getPlayer());

		}
		
		if (new TimersManager().getState() == State.Iniciando) {
			List<String> start = new CustomizationFile().getScoreboard(State.Iniciando, Bukkit.getPlayer(player));
			
			for (int i = 0; i < start.size();i++) {
				this.sb.updateLine(start.size()-(i+1), start.get(i));
			}
		}
		
		if (new TimersManager().getState() == State.Invencibilidade) {
			List<String> start = new CustomizationFile().getScoreboard(State.Invencibilidade, Bukkit.getPlayer(player));
			
			for (int i = 0; i < start.size();i++) {
				this.sb.updateLine(start.size()-(i+1), start.get(i));
			}
		}

		if (new TimersManager().getState() == State.Andamento) {
			List<String> start = new CustomizationFile().getScoreboard(State.Andamento, Bukkit.getPlayer(player));
			
			for (int i = 0; i < start.size();i++) {
				this.sb.updateLine(start.size()-(i+1), start.get(i));
			}
		}
		
	}
}
