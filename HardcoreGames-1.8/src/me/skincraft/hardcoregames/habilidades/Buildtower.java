package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Buildtower extends KitClass {

	public Buildtower() {
		super("buildtower",
				"Buildtower", new String[] 
						{"Construa torres gigantescas em segundos!"},
						new ItemStack(Material.DIRT), 10);
	}
	
}
