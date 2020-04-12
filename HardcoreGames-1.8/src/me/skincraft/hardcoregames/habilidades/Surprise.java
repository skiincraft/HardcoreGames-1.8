package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Surprise extends KitClass {

	public Surprise() {
		super("surprise",
				"Surprise", new String[] 
						{"Tenha sorte e ganhe kits para jogar", "nesta partida, Boa Sorte!"},
						new ItemStack(Material.SPONGE), 0);
	}
	
	
}
