package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Tank extends KitClass {

	public Tank() {
		super("tank",
				"Tank", new String[] 
						{"Derrote times facilmente", "ao matar o inimigo o mesmo explode!"},
						new ItemStack(Material.DIAMOND_CHESTPLATE), 15);
	}
	
	
}
