package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Tower extends KitClass {

	public Tower() {
		super("tower",
				"Tower", new String[] 
						{"Lute como um guerreiro viking", "com sua for�a e com seu machado!"},
						new ItemStack(Material.GOLD_BOOTS), 23);
	}
	
	
}
