package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Snail extends KitClass {

	public Snail() {
		super("snail",
				"Snail", new String[] 
						{"Enfraqueça seus inimigos e faça", "com que eles não fujam!"},
						new ItemStack(Material.STRING), 10);
	}
	
	
}
