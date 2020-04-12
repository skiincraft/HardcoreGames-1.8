package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Snail extends KitClass {

	public Snail() {
		super("snail",
				"Snail", new String[] 
						{"Enfraqueça seus inimigos e faça", "com que eles não fujam!"},
						new ItemStack(Material.STRING), 10);
	}
	
	
}
