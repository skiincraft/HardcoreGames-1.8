package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Lumberjack extends KitClass {

	public Lumberjack() {
		super("lumberjack",
				"Lumberjack", new String[] 
						{"Quebre as arvores instantaneamente com", "seu machado de madeira!"},
						new ItemStack(Material.WOOD_AXE), new ItemStack(Material.WOOD_AXE), 5);
	}
	
	
}
