package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Boxer extends KitClass {

	public Boxer() {
		super("boxer",
				"Boxer", new String[] 
						{"Com sua resistencia de boxeador tome menos danos","Com sua força elimine inimigos mais rapido!"},
						new ItemStack(Material.STONE_SWORD), 15);
	}
	
}
