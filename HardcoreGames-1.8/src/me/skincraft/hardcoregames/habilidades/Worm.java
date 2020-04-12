package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Worm extends KitClass {

	public Worm() {
		super("worm",
				"Worm", new String[] 
						{"Quebre terras instantaneamente", "como uma minhoca-mineman!"},
						new ItemStack(Material.DIRT), 5);
	}
	
	
}
