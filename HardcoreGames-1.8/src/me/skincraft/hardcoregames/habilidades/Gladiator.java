package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Gladiator extends KitClass {

	public Gladiator() {
		super("gladiator",
				"Gladiator", new String[] 
						{"Use sua jaula para prender ", "e desafiar seus inimigos"},
						new ItemStack(Material.IRON_FENCE), new ItemStack(Material.IRON_FENCE), 25);
	}
	
}
