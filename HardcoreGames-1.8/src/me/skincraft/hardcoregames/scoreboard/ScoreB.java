package me.skincraft.hardcoregames.scoreboard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.google.common.base.Splitter;

import me.skincraft.hardcoregames.timers.State;
import me.skincraft.hardcoregames.timers.TimersManager;

public class ScoreB {
	public static final String objective = "NoFlicker";
	private Scoreboard scoreboard;
	private ConcurrentHashMap<Integer, String> storedLines;
	private Team[] teams;
	private List<ChatColor> chatMap;

	public ScoreB() {
		this.storedLines = new ConcurrentHashMap<Integer, String>();
		(this.scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard())
				.registerNewObjective("NoFlicker", "dummy");
		this.teams = new Team[16];
		this.chatMap = new ArrayList<ChatColor>();
		ChatColor[] values;
		for (int length = (values = ChatColor.values()).length, i = 0; i < length; ++i) {
			final ChatColor chat = values[i];
			if (this.chatMap.size() + 1 > 15) {
				break;
			}
			this.chatMap.add(chat);
		}
	}

	public void addLine(final int scoreValue, String name) {
		name = this.fixDuplicates(name);
		this.teams[scoreValue] = this.scoreboard.registerNewTeam(String.valueOf(scoreValue));
		final int rand = new Random().nextInt(this.chatMap.size());
		final String idd = this.chatMap.get(rand).toString();
		this.teams[scoreValue].addEntry(idd);
		this.storedLines.put(scoreValue, idd);
		this.scoreboard.getObjective("NoFlicker").getScore(idd).setScore(scoreValue);
		final Iterator<String> iterator = Splitter.fixedLength(16).split((CharSequence) name).iterator();
		String prefix = iterator.next();
		final boolean shouldInsert = name.length() >= 16 && prefix.charAt(15) == '§';
		if (shouldInsert) {
			prefix = prefix.substring(0, 15);
		}
		this.teams[scoreValue].setPrefix(prefix);
		final String chatcolor = ChatColor.getLastColors(prefix);
		if (name.length() > 16) {
			String suffix = iterator.next();
			if (shouldInsert) {
				suffix = "§" + suffix;
			} else {
				suffix = String.valueOf(chatcolor) + suffix;
			}
			if (suffix.length() > 16) {
				suffix = suffix.substring(0, 16);
			}
			this.teams[scoreValue].setSuffix(suffix);
		}
		this.chatMap.remove(rand);
	}

	public void blankLine(final int id) {
		if (!this.hasLine(id)) {
			this.addLine(id, " ");
		}
	}

	private String fixDuplicates(String text) {
		while (this.storedLines.contains(text)) {
			text = String.valueOf(text) + "§r";
		}
		return text;
	}

	public String getLine(final int id) {
		return String.valueOf(this.teams[id].getPrefix()) + this.teams[id].getSuffix();
	}

	public String getName() {
		return this.scoreboard.getObjective("NoFlicker").getName();
	}

	public boolean hasBoard(final Player player) {
		return player.getScoreboard().getObjective("NoFlicker") != null;
	}

	public boolean hasLine(final int lineID) {
		return this.storedLines.get(lineID) != null;
	}

	public void removeLine(final int id) {
		if (!this.hasLine(id)) {
			return;
		}
		this.teams[id].unregister();
		this.teams[id] = null;
		final String idd = this.storedLines.get(id);
		this.scoreboard.resetScores(idd);
		this.storedLines.remove(id);
	}

	public void setForPlayer(final Player player) {
		player.setScoreboard(this.scoreboard);
	}

	public void setName(final String name) {
		this.scoreboard.getObjective("NoFlicker").setDisplayName(name);
	}

	public void setSlot(final DisplaySlot slot) {
		this.scoreboard.getObjective("NoFlicker").setDisplaySlot(slot);
	}
	
	public String getKitPrimary() {
		return "nenhum";
	}
	
	public String getKitSecoundary() {
		return "nenhum";
	}
	
	public int getPlayerKills() {
		return 0;
	}
	
	public String getTimer(State state) {
		return timeconversion(new TimersManager().getTimer(state));
	}
	
	private String timeconversion(Integer i) {
		int minutes = i.intValue() / 60;
		int seconds = i.intValue() % 60;
		String disMinu = (minutes < 10 ? "" : "") + minutes;
		String disSec = (seconds < 10 ? "0" : "") + seconds;
		String formattedTime = disMinu + "m " + disSec + "s";
		return formattedTime;
	}

	public void updateLine(final int id, String newName) {
		if (!this.hasLine(id)) {
			return;
		}
		newName = this.fixDuplicates(newName);
		final Iterator<String> iterator = Splitter.fixedLength(16).split((CharSequence) newName).iterator();
		String prefix = iterator.next();
		final boolean shouldInsert = newName.length() > 16 && prefix.charAt(15) == '§';
		if (shouldInsert) {
			prefix = prefix.substring(0, 15);
		}
		this.teams[id].setPrefix(prefix);
		final String chatcolor = ChatColor.getLastColors(prefix);
		if (newName.length() > 16) {
			String suffix = iterator.next();
			if (shouldInsert) {
				suffix = "§" + suffix;
			} else {
				suffix = String.valueOf(chatcolor) + suffix;
			}
			if (suffix.length() > 16) {
				suffix = suffix.substring(0, 16);
			}
			this.teams[id].setSuffix(suffix);
		} else {
			this.teams[id].setSuffix("");
		}
	}
}
