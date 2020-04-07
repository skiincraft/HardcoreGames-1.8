package me.skincraft.hardcoregames.listenerloader;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.utils.ClassGetter;

public class ListenersLoader {

	public Main main;

	public ListenersLoader(Main main) {
		this.main = main;
	}

	public void load() {
		ConsoleCommandSender s = Bukkit.getConsoleSender();
		s.sendMessage("§e§l|COMEÇANDO A CARREGAR OS RECURSOS|");
		for (Class<?> classes : ClassGetter.getClassesForPackage(Main.getPlugin(Main.class), "me.skincraft.hardcoregames")) {
			try {
				if (Listener.class.isAssignableFrom(classes)) {
					Listener listener = (Listener) classes.newInstance();
					Bukkit.getPluginManager().registerEvents(listener, Main.getPlugin(Main.class));
					s.sendMessage("\n§3§lLISTENER\n§a - §6" + classes.getSimpleName() + "§a Registrado com sucesso.");
				}
			} catch (Exception exception) {
				//Main.getMain().loggerError(exception, "Erro ao registrar [" + classes.getSimpleName() + "] como listener\n" + exception.getMessage());
				s.sendMessage("\n§4§lERRO\n§c - §e" + classes.getSimpleName() + "§c não foi registado como listener.");
			}
		}
	}
}
