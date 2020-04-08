package me.skincraft.hardcoregames.logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

/**
 * This should be get initialised when the plugin loads (It contains the Logging
 * system)
 *
 * @author Max
 */
public class Logging {

	private static Logger log;
	private static final Logger debugLogger = Logger.getLogger("Logging");
	private String plugin;
	private FileHandler debugFileHandler;

	public Logging(Plugin plugin) {

		this.plugin = plugin.getName();
		Date dt = new Date();
		SimpleDateFormat dflog = new SimpleDateFormat("yyyy-MM-dd");
		String time = dflog.format(dt);

		log = plugin.getLogger();

		int in = lognumber();

		try {

			debugFileHandler = new FileHandler(
					"logs" + "/" + "logs-Lystmc" + "/" + plugin.getName() + "_" + time + "-" + in + ".log", true);
			LogFormatter formatter = new LogFormatter();

			debugLogger.addHandler(debugFileHandler);
			debugFileHandler.setFormatter(formatter);
			debugLogger.log(Level.OFF, "INICIANDO SISTEMA DE LOGGINGS");

		} catch (IOException ex) {
			debug(null, ex, false);
		} catch (SecurityException ex) {
			debug(null, ex, false);
		}
	}

	/**
	 * Logs a message
	 *
	 * @param level  The level to log
	 * @param msg    The message to log
	 * @param toFile log to own log?
	 */
	public void debug(Level level, String msg, boolean toFile) {
		if (toFile) {
			if (debugLogger != null) {
				debugLogger.log(level, msg);
			}
		}
		log.log(level, msg);
	}

	/**
	 * Logs a Exception
	 *
	 * @param msg       The message to log
	 * @param exception the exception
	 * @param toFile    log to own log?
	 */
	public void debug(String msg, Throwable exception, boolean toFile) {
		if (toFile) {
			if (debugLogger != null) {
				debugLogger.log(Level.SEVERE, msg, exception);
			}
		}
		log.log(Level.SEVERE, msg, exception);
	}

	/**
	 * Gets the logger for your plugin
	 *
	 * @param plugin The plugin to apply
	 */

	public int lognumber() {
		Date dt = new Date();
		SimpleDateFormat dflog = new SimpleDateFormat("yyyy-MM-dd");
		String time = dflog.format(dt);
		int i = 0;
		for (i = 1; i < 1000; i++) {
			File diretorio = new File("logs" + "/" + "logs-Lystmc" + "/" + plugin + "_" + time + "-" + i + ".log");
			if (!diretorio.exists()) {
				return i;
			}
		}
		return 1;

	}
}