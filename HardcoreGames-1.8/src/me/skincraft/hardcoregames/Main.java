package me.skincraft.hardcoregames;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.skincraft.hardcoregames.listenerloader.ListenersLoader;

public class Main extends JavaPlugin {

	public static Plugin plugin;
	
	public static Plugin getPlugin() {
		return plugin;
	}
	public static Main getMain() {
		return (Main) JavaPlugin.getProvidingPlugin((Class<?>) Main.class);
	}
	@Override
	public void onEnable() {
		new ListenersLoader(this);
	}
	
}
