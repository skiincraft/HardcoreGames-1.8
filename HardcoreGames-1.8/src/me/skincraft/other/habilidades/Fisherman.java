package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Fisherman extends KitClass {

	public Fisherman() {
		super("fisherman",
				"Fisherman", new String[] 
						{"Com usa vara de pescar, use a sua vantagem", "fisgando seus inimigo e trazendo ate voc�"},
						new ItemStack(Material.FISHING_ROD), new ItemStack(Material.FISHING_ROD),15);
	}
	
}
