package me.skincraft.hardcoregames.kit;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.utils.ClassGetter;

public class KitsLoader {

	public Main main;

	public KitsLoader(Main main) {
		this.main = main;
	}

	public void load() {
		ConsoleCommandSender s = Bukkit.getConsoleSender();
		Main.kitlist.clear();
		for (Class<?> classes : ClassGetter.getClassesForPackage(Main.getPlugin(Main.class), "me.skincraft.hardcoregames.habilidades")) {			
			try {
				if (KitClass.class.isAssignableFrom(classes) && classes != KitClass.class) {
					KitClass commandBase = (KitClass) classes.newInstance();
					
					Main.kitlist.add(commandBase);
					s.sendMessage("\n§3§lKIT\n§a - §6" + commandBase.getName() + "§a registrado com sucesso.");
				}
			} catch (Exception e) {
				s.sendMessage("\n§4§lERRO\n§c - §e" + classes.getSimpleName() + "§c não foi registado como kit.");
			}
		}
		s.sendMessage("Foram registrados " + Main.kitlist.size() + " kits no servidor.");
	}
}
