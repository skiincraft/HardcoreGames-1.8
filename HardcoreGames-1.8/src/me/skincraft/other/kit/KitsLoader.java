package me.skincraft.other.kit;

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
		for (Class<?> classes : ClassGetter.getClassesForPackage(Main.getPlugin(Main.class), "me.skincraft.other.habilidades")) {			
			try {
				if (KitClass.class.isAssignableFrom(classes) && classes != KitClass.class) {
					KitClass kitclass = (KitClass) classes.newInstance();
					
					Main.kitlist.add(kitclass);	
					
					kitclass.setListener();
					kitclass.endListener();	
				}
			} catch (Exception e) {
				s.sendMessage("\n§4§lERRO\n§c - §e" + classes.getSimpleName() + "§c não foi registado como kit.");
			}
		}
		s.sendMessage("Foram registrados " + Main.kitlist.size() + " kits no servidor.");
	}
}
