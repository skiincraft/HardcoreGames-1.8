package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Antitower extends KitClass {

	public Antitower() {
		super("antitower",
				"Antitower", new String[] 
						{"N�o deixe ninguem o pisotear", "caindo dos ares e pulando em voc�!"},
						new ItemStack(Material.ANVIL), 10);
	}
	
}
