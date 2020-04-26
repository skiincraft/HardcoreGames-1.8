package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Miner extends KitClass {

	public Miner() {
		super("miner",
				"Miner", new String[] 
						{"Seja um ferreiro e pegue muitos", "minerios para sua proteção e ataque!"},
						new ItemStack(Material.STONE_PICKAXE), new ItemStack(Material.STONE_PICKAXE), 5);
	}
	
	
}
