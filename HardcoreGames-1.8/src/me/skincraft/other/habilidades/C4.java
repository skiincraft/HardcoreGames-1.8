package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class C4 extends KitClass {

	public C4() {
		super("c4",
				"C4", new String[] 
						{"Exploda seus inimigos com suas bombas adesivas", "que explodem quando armadas!"},
						new ItemStack(Material.SLIME_BALL), new ItemStack(Material.SLIME_BALL), 20);
	}
	
}
