package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Snail extends KitClass {

	public Snail() {
		super("snail",
				"Snail", new String[] 
						{"Enfraque�a seus inimigos e fa�a", "com que eles n�o fujam!"},
						new ItemStack(Material.STRING), 10);
	}
	
	
}
