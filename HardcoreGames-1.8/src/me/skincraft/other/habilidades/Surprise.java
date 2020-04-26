package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Surprise extends KitClass {

	public Surprise() {
		super("surprise",
				"Surprise", new String[] 
						{"Tenha sorte e ganhe kits para jogar", "nesta partida, Boa Sorte!"},
						new ItemStack(Material.SPONGE), 0);
	}
	
	
}
