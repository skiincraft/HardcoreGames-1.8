package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Hulk extends KitClass {

	public Hulk() {
		super("hulk",
				"Hulk", new String[] 
						{"Com a força de um mutante segure seu inimigo", "deixando ele em panico!"},
						new ItemStack(Material.SADDLE), 15);
	}
	
}
