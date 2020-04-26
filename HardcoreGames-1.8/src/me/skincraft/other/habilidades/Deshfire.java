package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Deshfire extends KitClass {

	public Deshfire() {
		super("deshfire",
				"Deshfire", new String[] 
						{"Ative sua furia e deixe seus inimigos", "incendiados ao verem seu verdadeiro poder!"},
						new ItemStack(Material.REDSTONE_BLOCK), new ItemStack(Material.REDSTONE_BLOCK), 20);
	}
	
}
