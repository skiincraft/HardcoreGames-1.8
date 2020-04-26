package me.skincraft.hardcoregames;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.henrya.pingapi.PingAPI;

import me.skincraft.hardcoregames.events.CraftingItens;
import me.skincraft.hardcoregames.listenerloader.CommandsLoader;
import me.skincraft.hardcoregames.listenerloader.EventsLoader;
import me.skincraft.hardcoregames.logger.Logging;
import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.mysql.MySQL;
import me.skincraft.hardcoregames.scoreboard.SManager;
import me.skincraft.hardcoregames.socket.MotdListeners;
import me.skincraft.hardcoregames.timers.Iniciando;
import me.skincraft.hardcoregames.timers.State;
import me.skincraft.hardcoregames.timers.TimersManager;
import me.skincraft.hardcoregames.utils.ConfigurationFiles;
import me.skincraft.inventorymanager.InventarioAPI;
import me.skincraft.inventorymanager.Loader;
import me.skincraft.other.kit.KitClass;
import me.skincraft.other.kit.KitsLoader;

public class Main extends JavaPlugin {

	public static FileConfiguration config;
	public static String logspath = "logs/" + "logs-Lystmc";
	public boolean DBSQL;
	private MySQL connection;
	public Logging log = new Logging(this);
	public static Plugin plugin;
	public boolean bungeecord;
	public static List<KitClass> kitlist = new ArrayList<KitClass>();
	public static List<InventarioAPI> inventorylist = new ArrayList<InventarioAPI>();
	
	public static List<String> freeKits = new ArrayList<String>();
	
	
	
	public static Plugin getPlugin() {
		return plugin;
	}
	public static Main getMain() {
		return (Main) JavaPlugin.getProvidingPlugin((Class<?>) Main.class);
	}
	public MySQL getSQL() {
		return connection;
	}
	public void logger(Level level, String message) {
		log.debug(level, message, true);
	}
	
	public void loggerError(Throwable exception ,String message) {
		log.debug(message, exception, true);
	}
	private void consoleMsg(String message) {
		Bukkit.getConsoleSender().sendMessage(message);
	}
	
	private void loadMOTD() {
		PingAPI.registerListener(new MotdListeners());
	}

	public void loadPing() {
		new PingAPI().onEnable();
	}
	
	public void onDisable() {
		if (this.connection != null) {
			this.connection.close();
		}
		
		PlayerHGManager.clearAllStates();
		Bukkit.getServer().getScheduler().cancelAllTasks();
		String thisserver = Main.getMain().getConfig().getString("ServerIP");
		
		log.debug(Level.OFF, "SERVIDOR FOI DESLIGADO: " + thisserver, true);
	}
	
	public void onLoad() {
		plugin = this;
		bungeecord = false;
		this.getLogger().info("Carregando plugin....");
		
		log.debug(Level.INFO, "Sistema de logs funcionando", true);
		File diretorio = new File(logspath);
		if (!diretorio.exists()) {
			diretorio.mkdir();
		}

		/*if (deleteWorld == true) {
			Bukkit.getServer().unloadWorld("world", false);
			deleteDir(new File("world"));
		}
		*/
	}

	
	public String servername;
	
	
	@Override
	public void onEnable() {
		new TimersManager().setState(State.Iniciando);
		servername = getConfig().getString("Servername");
		
		new EventsLoader(this).load();
		new CommandsLoader(this).load();
		new KitsLoader(this).load();
		new Loader(this).load();
		
		this.loadPing();
		this.loadMOTD();
		
		this.connection = new MySQL();
		this.connection.abrir();
		this.connection.setup();
		
		
		new ConfigurationFiles();
		new CraftingItens();
		
		PlayerHGManager.clearAllStates();
		Bukkit.getWorld("world").setGameRuleValue("NATURAL_REGENERATION", "false");
		if (DBSQL == true) {
			log.debug(Level.INFO, "MYSQL: Esta ativo", true);
		} else {
			log.debug(Level.INFO, "MYSQL: Esta desativado", true);
		}
		
		SManager.onEnable();
		consoleMsg("Plugin de Hardcore Games carregado.");
		new Iniciando();
	}	
}
