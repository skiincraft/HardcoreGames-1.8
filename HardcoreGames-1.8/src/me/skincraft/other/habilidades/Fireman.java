package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Fireman extends KitClass {

	public Fireman() {
		super("fireman",
				"Fireman", new String[] 
						{"Com tanta experiencia fique invencivel ao fogo", "e com seu balde de agua apague!"},
						new ItemStack(Material.LAVA_BUCKET), new ItemStack(Material.BUCKET),15);
	}
	
}
