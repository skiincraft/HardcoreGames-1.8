package me.skincraft.hardcoregames.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;
import me.skincraft.hardcoregames.mysql.SQLPlayers;
import me.skincraft.hardcoregames.mysql.SQLPlayers.SQLOptions;
import me.skincraft.hardcoregames.timers.State;
import me.skincraft.hardcoregames.timers.TimersManager;
import me.skincraft.other.kit.KitManager;

public class CustomizationFile {
	
	private static FileConfiguration custom_file;
	private static File customFile;
	public static FileConfiguration custom() {
		return custom_file;
	}
	
	public CustomizationFile() {
		//
	}
	
	public String getDiscord() {
		return custom().getString("Discord");
	}
	
	public String getWebsite() {
		return custom().getString("Site");
	}
	
	public String getTwitter() {
		return custom().getString("Twitter");
	}
	
	public List<String> getTablistHeater() {
		List<String> linha = custom().getStringList("Tablist.Cima");
		List<String> resultado = new ArrayList<String>();
		resultado.clear();
		for (int i = 0; i < linha.size(); i++) {
			String str = linha.get(i);
			String discord = str.replace("<discord>", getDiscord());
			String twitter = discord.replace("<twitter>", getTwitter());
			String site = twitter.replace("<site>", getWebsite());
			String servername = site.replace("<servername>", Main.getMain().servername);
			String line = servername.replace("<line>", "\n");
			
			String result = line.replace("&", "§");
			resultado.add(result);
		}
		return resultado;
	}
	
	public List<String> getTablistFooter() {
		List<String> linha = custom().getStringList("Tablist.Baixo");
		List<String> resultado = new ArrayList<String>();
		resultado.clear();
		for (int i = 0; i < linha.size(); i++) {
			String str = linha.get(i);
			String discord = str.replace("<discord>", getDiscord());
			String twitter = discord.replace("<twitter>", getTwitter());
			String site = twitter.replace("<site>", getWebsite());
			String servername = site.replace("<servername>", Main.getMain().servername);
			String line = servername.replace("<line>", "\n");
			
			String result = line.replace("&", "§");
			resultado.add(result);
		}
		return resultado;
	}
	
	private String convertTime(Integer i) {
		int minutes = i.intValue() / 60;
		int seconds = i.intValue() % 60;
		String disMinu = (minutes < 10 ? "" : "") + minutes;
		String disSec = (seconds < 10 ? "0" : "") + seconds;
		String formattedTime = disMinu + ":" + disSec + "";
		if (seconds == 0) {
			return disMinu + ":00";
		}
		return formattedTime;
	}
	
	public List<String> getScoreboard(State score, Player player) {
		List<String> linha = custom().getStringList("Scoreboard." + score.name());
		List<String> resultado = new ArrayList<String>();
		int playersize;
		Bukkit.getOnlinePlayers();
		if (new TimersManager().getState() == State.Iniciando) {
			playersize = PlayerHGManager.getSize(PlayerState.HOLD);
		} else {
			playersize = PlayerHGManager.getSize(PlayerState.ALIVE);
		}
		
		String time = convertTime(new TimersManager().getTimer(score));
		
		resultado.clear();
		for (int i = 0; i < linha.size();i++) {
			String num = linha.get(i);
			String kit1 = num.replace("<kit1>", new KitManager(player).getPlayerKit1().getDisplayName());
			String kit2 = kit1.replace("<kit2>", new KitManager(player).getPlayerKit2().getDisplayName());
			String players = kit2.replace("<players>", "" + playersize);
			String timer = players.replace("<timer>", "" + time);
			String xp = timer.replace("<xp>", "" + new SQLPlayers(player).get(SQLOptions.Xp));
			String site = xp.replace("<site>", getWebsite());
			String kills = site.replace("<kills>", "" + new PlayerHGManager(player).getPlayerStreak());
			
			String result = kills.replace("&", "§");
			
			resultado.add(result);
		}
		return resultado;
	}
	
	public void setScoreboard(State score, List<String> list) {
		custom().set("Scoreboard." + score.name(), list);
		save();
	}
	public void save() {
		try {
			custom_file.save(customFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setup(Plugin plugin) {

		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		customFile = new File(plugin.getDataFolder(), "customization.yml");
		if (customFile.exists()) {
			try {
				customFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		custom_file = YamlConfiguration.loadConfiguration(customFile);
	}


}
