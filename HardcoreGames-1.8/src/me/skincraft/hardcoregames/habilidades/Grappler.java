package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Grappler extends KitClass {

	public Grappler() {
		super("grappler",
				"Grappler", new String[] 
						{"Fisgue com sua corda em lugares","e puxe para ir rapidamente para l�!"},
						new ItemStack(Material.LEASH), new ItemStack(Material.LEASH), 25);
	}
	
}
