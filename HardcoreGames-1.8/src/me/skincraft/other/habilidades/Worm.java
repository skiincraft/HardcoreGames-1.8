package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Worm extends KitClass {

	public Worm() {
		super("worm",
				"Worm", new String[] 
						{"Quebre terras instantaneamente", "como uma minhoca-mineman!"},
						new ItemStack(Material.DIRT), 5);
	}
	
	@EventHandler
	public void onlogin (PlayerJoinEvent e) {
		e.setJoinMessage("MENSAGEM NOVAAAAA +" + getFullDisplayName());
	}
	
}
