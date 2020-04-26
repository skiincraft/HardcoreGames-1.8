package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Kangaroo extends KitClass {

	public Kangaroo() {
		super("kangaroo",
				"Kangaroo", new String[] 
						{"Pule igual um Canguru com seu", "Kangaroo dando doublejumps pelo mapa!"},
						new ItemStack(Material.FIREWORK), new ItemStack(Material.FIREWORK), 30);
	}
	
}
