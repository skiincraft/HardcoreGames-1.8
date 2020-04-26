package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Viking extends KitClass {

	public Viking() {
		super("viking",
				"Viking", new String[] 
						{"Lute como um guerreiro viking", "com sua força e com seu machado!"},
						new ItemStack(Material.STONE_AXE), 15);
	}
	
	
}
