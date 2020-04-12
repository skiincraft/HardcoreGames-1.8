package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Flash extends KitClass {

	public Flash() {
		super("flash",
				"Flash", new String[] 
						{"Use sua velocidade temporaria para encontrar", "ou at� mesmo fugir de seus inimigos!"},
						new ItemStack(Material.REDSTONE_TORCH_ON), new ItemStack(Material.REDSTONE_TORCH_ON), 15);
	}
	
}
