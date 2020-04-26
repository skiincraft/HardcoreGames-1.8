package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Thor extends KitClass {

	public Thor() {
		super("thor",
				"Thor", new String[] 
						{"Lute como um guerreiro viking", "com sua força e com seu machado!"},
						new ItemStack(Material.WOOD_AXE), 15);
	}
	
	
}
