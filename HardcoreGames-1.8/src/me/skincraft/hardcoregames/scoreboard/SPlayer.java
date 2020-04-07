package me.skincraft.hardcoregames.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import me.skincraft.hardcoregames.timers.State;
import me.skincraft.hardcoregames.timers.TimersManager;

public class SPlayer {
	private String player;
	private ScoreB sb;

	public String scoretitle = "§f§lLyst§4§lMC";
	public String site = "";
	
	public SPlayer(final String player) {
		this.player = player;
		(this.sb = new ScoreB()).setSlot(DisplaySlot.SIDEBAR);
		this.sb.setName("  " + scoretitle + "  ");
		if (new TimersManager().getState() == State.Iniciando) {

			this.sb.addLine(10, "§f");
			this.sb.addLine(9, "§fIniciando em: ");
			this.sb.addLine(8, "§fJogadores: ");
			this.sb.addLine(7, "§f");
			this.sb.addLine(6, "§fKit 1: ");
			this.sb.addLine(5, "§fKit 2:");
			this.sb.addLine(4, "§f");
			this.sb.addLine(3, "§fXP: ");
			this.sb.addLine(2, "§c ");

			this.sb.addLine(1, "§e" + site);
		}

		if (new TimersManager().getState() == State.Invencibilidade) {

			this.sb.addLine(10, "§f");
			this.sb.addLine(9, "§fInvencibilidade: ");
			this.sb.addLine(8, "§fJogadores: ");
			this.sb.addLine(7, "§f");
			this.sb.addLine(6, "§fKit 1: ");
			this.sb.addLine(5, "§fKit 2:");
			this.sb.addLine(4, "§f");
			this.sb.addLine(3, "§fXP: ");
			this.sb.addLine(2, "§c ");
			this.sb.addLine(1, "§e" + site);

		}

		if (new TimersManager().getState() == State.Andamento) {

			this.sb.addLine(10, "§f");
			this.sb.addLine(9, "§fTempo: ");
			this.sb.addLine(8, "§fJogadores: ");
			this.sb.addLine(7, "§f");
			this.sb.addLine(6, "§fKit 1: ");
			this.sb.addLine(5, "§fKit 2: ");
			this.sb.addLine(4, "§f");
			this.sb.addLine(3, "§fKills: ");
			this.sb.addLine(2, "§c ");
			this.sb.addLine(1, "§e" + site);

		}
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(this.player);
	}

	public synchronized void updateScoreboard() {
		if (!this.sb.hasBoard(this.getPlayer())) {
			this.sb.setForPlayer(this.getPlayer());

		}
		
		String kit1 = sb.getKitPrimary();
		String kit2 = sb.getKitSecoundary();
		int players = Bukkit.getOnlinePlayers().size();
		
		if (new TimersManager().getState() == State.Iniciando) {
			this.sb.updateLine(9, "§fIniciando em: §b" + sb.getTimer(State.Iniciando));
			this.sb.updateLine(8, "§fJogadores: §e" + players + "/" + Bukkit.getMaxPlayers());
			this.sb.updateLine(6, "§fKit 1: §3" + kit1);
			this.sb.updateLine(5, "§fKit 2: §3" + kit2);
			this.sb.updateLine(3, "§fXP: §b" + 0);
			
		}
		if (new TimersManager().getState() == State.Invencibilidade) {
			this.sb.updateLine(9, "§fInvencibilidade: §b" + sb.getTimer(State.Invencibilidade));
			this.sb.updateLine(8, "§fJogadores: §e" + players + "/" + Bukkit.getMaxPlayers());
			this.sb.updateLine(6, "§fKit 1: §3" + kit1);
			this.sb.updateLine(5, "§fKit 2: §3" + kit2);
			this.sb.updateLine(3, "§fXP: §b" + 0);
		}
		if (new TimersManager().getState() == State.Andamento) {
			this.sb.updateLine(9, "§fTempo em: §b" + sb.getTimer(State.Andamento));
			this.sb.updateLine(8, "§fJogadores: §e" + players + "/" + Bukkit.getMaxPlayers());
			this.sb.updateLine(6, "§fKit 1: §3" + kit1);
			this.sb.updateLine(5, "§fKit 2: §3" + kit2);
			this.sb.updateLine(3, "§fKills: §b" + 0);
		}
	}
}
