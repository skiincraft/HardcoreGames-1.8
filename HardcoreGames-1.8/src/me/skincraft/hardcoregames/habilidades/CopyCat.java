package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class CopyCat extends KitClass {

	public CopyCat() {
		super("copycat",
				"CopyCat", new String[] 
						{"Seja um inimigo que aprende rapido e", "e use a habilidade de seu inimigo!"},
						new ItemStack(Material.SLIME_BALL), 20);
	}
	
}
