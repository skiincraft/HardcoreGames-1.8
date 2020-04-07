package org.henrya.pingapi;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import me.skincraft.hardcoregames.Main;

import org.bukkit.event.Listener;

/**
 * An API that gives developers more control over how they reply to ping requests
 * @author Henry Anderson
 */
public class PingAPI {
	private static List<PingListener> listeners;

	public void onEnable() {
		try {
			PingAPI.listeners = new ArrayList<PingListener>();
			String name = Bukkit.getServer().getClass().getPackage().getName();
			String version = name.substring(name.lastIndexOf('.') + 1);
        	Class<?> injector = Class.forName("org.henrya.pingapi." + version + ".PingInjector");
        	Bukkit.getPluginManager().registerEvents((Listener) injector.newInstance(), Main.getMain());
        	Main.getMain().getLogger().log(Level.INFO, "Successfully hooked into " + Bukkit.getServer().getName() + " " + version);
		} catch(ClassNotFoundException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException e) {
		    Main.getMain().getLogger().log(Level.SEVERE, "Non compatible server version!", e);
		}
	}
	
	/**
	 * Registers a new PingListener instance
	 * @param listener The PingListener implementation
	 */
	public static void registerListener(PingListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Returns a List of all PingListener's that have been registered
	 * @return Return's a List of PingListener
	 */
	public static List<PingListener> getListeners() {
		return listeners;
	}
}