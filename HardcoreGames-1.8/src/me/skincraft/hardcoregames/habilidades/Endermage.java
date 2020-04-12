package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Endermage extends KitClass {

	public Endermage() {
		super("endermage",
				"Endermage", new String[] 
						{"Com seu portal traga inimigos a sua", "Posição ao pisar no mesmo bloco do portal."},
						new ItemStack(Material.ENDER_PORTAL_FRAME), new ItemStack(Material.ENDER_PORTAL_FRAME), 15);
	}
	
}
