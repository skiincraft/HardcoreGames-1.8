package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Anchor extends KitClass {

	public Anchor() {
		super("anchor",
				"Anchor", new String[] 
						{"§7Fique mais resistente a dano de","§7Espadas,mas tenha cuidado com madeiras!"},
						new ItemStack(Material.ANVIL), 20);
	}
	
}
