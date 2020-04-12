package me.skincraft.hardcoregames.habilidades;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.kit.KitClass;

public class Antitower extends KitClass {

	public Antitower() {
		super("anchor",
				"Antitower", new String[] 
						{"Não deixe ninguem o pisotear", "caindo dos ares e pulando em você!"},
						new ItemStack(Material.ANVIL), 10);
	}
	
}
