package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Launcher extends KitClass {

	public Launcher() {
		super("launcher",
				"Launcher", new String[] 
						{"Pule muito alto com suas esponjas magicas"},
						new ItemStack(Material.SPONGE), new ItemStack(Material.SPONGE, 12), 15);
	}
	
}
