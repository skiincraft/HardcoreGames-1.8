package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Monk extends KitClass {

	public Monk() {
		super("monk",
				"Monk", new String[] 
						{"Confunda seu inimigo com sua habilidade de", "monkey e embaralhe o inventario do seu inimigo!"},
						new ItemStack(Material.BLAZE_ROD), new ItemStack(Material.BLAZE_ROD), 15);
	}
	
	
}
