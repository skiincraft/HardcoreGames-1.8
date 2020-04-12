package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Fisherman extends KitClass {

	public Fisherman() {
		super("fisherman",
				"Fisherman", new String[] 
						{"Com usa vara de pescar, use a sua vantagem", "fisgando seus inimigo e trazendo ate você"},
						new ItemStack(Material.FISHING_ROD), new ItemStack(Material.FISHING_ROD),15);
	}
	
}
