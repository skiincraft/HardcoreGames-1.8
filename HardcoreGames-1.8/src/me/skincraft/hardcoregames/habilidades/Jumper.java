package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Jumper extends KitClass {

	public Jumper() {
		super("jumper",
				"Jumper", new String[] 
						{"Com usa vara de pescar, use a sua vantagem", "Para jogar seus inimigos ao alto!"},
						new ItemStack(Material.FISHING_ROD), new ItemStack(Material.FISHING_ROD), 15);
	}
	
}
