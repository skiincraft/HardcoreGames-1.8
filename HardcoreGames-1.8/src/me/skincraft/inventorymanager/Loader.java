package me.skincraft.inventorymanager;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.utils.ClassGetter;

public class Loader {

	public Main main;

	public Loader(Main main) {
		this.main = main;
	}

	public void load() {
		ConsoleCommandSender s = Bukkit.getConsoleSender();
		Main.inventorylist.clear();
		for (Class<?> classes : ClassGetter.getClassesForPackage(Main.getPlugin(Main.class), "me.skincraft.inventorymanager.inventory")) {			
			try {
				if (InventarioAPI.class.isAssignableFrom(classes) && classes != InventarioAPI.class) {
					InventarioAPI kitclass = (InventarioAPI) classes.newInstance();
					
					Main.inventorylist.add(kitclass);	
					
					kitclass.setListener();
					kitclass.endListener();
				}
			} catch (Exception e) {
				s.sendMessage("\n§4§lERRO\n§c - §e" + classes.getSimpleName() + "§c não foi registado como kit.");
			}
		}
		s.sendMessage("Foram registrados " + Main.inventorylist.size() + " kits no servidor.");
	}
}
