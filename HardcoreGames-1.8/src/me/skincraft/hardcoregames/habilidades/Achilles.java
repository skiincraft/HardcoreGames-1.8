package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Achilles extends KitClass implements Listener {

	public Achilles() {
		super("achilles",
				"Achilles", new String[] 
						{"Fique mais resistente a dano de","Espadas,mas tenha cuidado com madeiras!"},
						new ItemStack(Material.WOOD_SWORD), 15);
	}

	
	
}
