package me.skincraft.hardcoregames;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.henrya.pingapi.PingAPI;
import me.skincraft.hardcoregames.listenerloader.ListenersLoader;
import me.skincraft.hardcoregames.scoreboard.SManager;
import me.skincraft.hardcoregames.socket.MotdListeners;
import me.skincraft.hardcoregames.timers.Iniciando;
import me.skincraft.hardcoregames.timers.State;
import me.skincraft.hardcoregames.timers.TimersManager;

public class Main extends JavaPlugin {

	public static Plugin plugin;
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	public static Main getMain() {
		return (Main) JavaPlugin.getProvidingPlugin((Class<?>) Main.class);
	}
	
	private void consoleMsg(String message) {
		Bukkit.getConsoleSender().sendMessage(message);
	}
	
	private void loadMOTD() {
		//PingAPI.registerListener(new MotdListeners());
		PingAPI.registerListener(new MotdListeners());
	}

	public void loadPing() {
		new PingAPI().onEnable();
	}

	
	public String servername = "§3§lLyst§f§lHG";
	
	
	@Override
	public void onEnable() {
		new TimersManager().setState(State.Iniciando);
		new ListenersLoader(this).load();
		this.loadPing();
		this.loadMOTD();
		
		consoleMsg(PingAPI.getListeners().get(0).toString() + "");
		
		SManager.onEnable();
		consoleMsg("Plugin de Hardcore Games carregado.");
		new Iniciando();
	}
	
}
