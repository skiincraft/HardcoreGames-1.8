package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Reaper extends KitClass {

	public Reaper() {
		super("reaper",
				"Reaper", new String[] 
						{"Corra atras do seus inimigos levando-os", "para a morte com sua foice!"},
						new ItemStack(Material.STONE_HOE), 10);
	}
	
	
}
