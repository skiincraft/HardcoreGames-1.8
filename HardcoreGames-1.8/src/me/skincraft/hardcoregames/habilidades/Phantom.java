package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Phantom extends KitClass {

	public Phantom() {
		super("phantom",
				"Phantom", new String[] 
						{"Crie assas e voe temporariamente", "pelos ares do HardcoreGames!"},
						new ItemStack(Material.FEATHER), new ItemStack(Material.FEATHER), 10);
	}
	
	
}
