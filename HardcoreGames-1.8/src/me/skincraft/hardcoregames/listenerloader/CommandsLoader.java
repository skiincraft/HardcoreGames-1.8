package me.skincraft.hardcoregames.listenerloader;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.api.LystMCCommands;
import me.skincraft.hardcoregames.utils.ClassGetter;

public class CommandsLoader {

	public Main main;

	public CommandsLoader(Main main) {
		this.main = main;
	}

	public void load() {
		ConsoleCommandSender s = Bukkit.getConsoleSender();
		for (Class<?> classes : ClassGetter.getClassesForPackage(Main.getPlugin(Main.class), "me.skincraft.hardcoregames")) {			
			try {
				if (LystMCCommands.class.isAssignableFrom(classes) && classes != LystMCCommands.class) {
					LystMCCommands commandBase = (LystMCCommands) classes.newInstance();
					((CraftServer) Bukkit.getServer()).getCommandMap().register(commandBase.getName(), commandBase);
					s.sendMessage("\n§3§lCOMMAND\n§a - §6" + classes.getSimpleName() + "§a registrado com sucesso.");
				}
			} catch (Exception e) {
				s.sendMessage("\n§4§lERRO\n§c - §e" + classes.getSimpleName() + "§c não foi registado como listener.");
			}

		}
	}
}
