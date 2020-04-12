package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Specialist extends KitClass {

	public Specialist() {
		super("specialist",
				"Specialist", new String[] 
						{"§3Com seu conhecimento em encantamento","carregue um livro e encante seus itens!"},
						new ItemStack(Material.ENCHANTED_BOOK), new ItemStack(Material.BOOK), 15);
	}
	
	
}
