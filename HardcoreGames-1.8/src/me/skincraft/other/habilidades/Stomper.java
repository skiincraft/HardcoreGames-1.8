package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Stomper extends KitClass {

	public Stomper() {
		super("stomper",
				"Stomper", new String[] 
						{"Pisoteie seus inimigos caindo de lugares altos"},
						new ItemStack(Material.IRON_BOOTS), 20);
	}
	
	
}
