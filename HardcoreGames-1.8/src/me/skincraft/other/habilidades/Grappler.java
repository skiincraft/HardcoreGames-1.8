package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Grappler extends KitClass {

	public Grappler() {
		super("grappler",
				"Grappler", new String[] 
						{"Fisgue com sua corda em lugares","e puxe para ir rapidamente para lá!"},
						new ItemStack(Material.LEASH), new ItemStack(Material.LEASH), 25);
	}
	
}
