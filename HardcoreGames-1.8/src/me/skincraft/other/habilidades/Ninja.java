package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Ninja extends KitClass {

	public Ninja() {
		super("ninja",
				"Ninja", new String[] 
						{"Não deixe seu inimigo descançar e", "teleporte-se atras dele, lembre-se esse é seu jeito ninja!"},
						new ItemStack(Material.EMERALD), 30);
	}
	
	
}
