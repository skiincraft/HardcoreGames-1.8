package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Buildtower extends KitClass {

	public Buildtower() {
		super("buildtower",
				"Buildtower", new String[] 
						{"Construa torres gigantescas em segundos!"},
						new ItemStack(Material.DIRT), 10);
	}
	
}
