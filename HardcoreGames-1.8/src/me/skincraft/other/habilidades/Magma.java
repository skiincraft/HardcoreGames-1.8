package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Magma extends KitClass {

	public Magma() {
		super("magma",
				"Magma", new String[] 
						{"Misture-se com o fogo e a lava", "e derrote seus inimigos queimados!"},
						new ItemStack(Material.MAGMA_CREAM), 15);
	}
	
	
}
