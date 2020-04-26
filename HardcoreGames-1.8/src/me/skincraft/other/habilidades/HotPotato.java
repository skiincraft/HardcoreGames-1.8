package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class HotPotato extends KitClass {

	public HotPotato() {
		super("hotpotato",
				"HotPotato", new String[] 
						{"Brinque de batata quente com seu inimigo","dando a batata quente para ele!"},
						new ItemStack(Material.BAKED_POTATO), new ItemStack(Material.BAKED_POTATO), 15);
	}
	
}
