package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Flash extends KitClass {

	public Flash() {
		super("flash",
				"Flash", new String[] 
						{"Use sua velocidade temporaria para encontrar", "ou até mesmo fugir de seus inimigos!"},
						new ItemStack(Material.REDSTONE_TORCH_ON), new ItemStack(Material.REDSTONE_TORCH_ON), 15);
	}
	
}
