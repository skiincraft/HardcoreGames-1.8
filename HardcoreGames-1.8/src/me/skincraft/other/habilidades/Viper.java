package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Viper extends KitClass {

	public Viper() {
		super("viper",
				"Viper", new String[] 
						{"Envenene seus inimigos com sua", "adaga venenosa e mate-os!"},
						new ItemStack(Material.SPIDER_EYE), 10);
	}
	
	
}
