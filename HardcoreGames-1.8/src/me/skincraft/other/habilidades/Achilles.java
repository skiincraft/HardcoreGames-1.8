package me.skincraft.other.habilidades;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.skincraft.other.kit.KitClass;

public class Achilles extends KitClass {

	public Achilles() {
		super("achilles",
				"Achilles", new String[] 
						{"Fique mais resistente a dano de","Espadas,mas tenha cuidado com madeiras!"},
						new ItemStack(Material.WOOD_SWORD), 15);
	}

	@EventHandler
	public void achillesKitEvent(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		
		Player player = (Player) e.getEntity();
		Player damager = (Player) e.getDamager();
		
		if (!getKitUsers().contains(player.getName())) {
			return;
		}
		
		ItemStack item = damager.getItemInHand();
		if (item == null) return;
		
		if (item.getType().name().contains("WOOD")) {
			e.setDamage(7D);
		}
		
		if (item.getType().name().contains("STONE")) {
			e.setDamage(e.getDamage() - 1.0D);
		}
		
		if (item.getType().name().contains("IRON")) {
			e.setDamage(e.getDamage() - 1.5D);
		}
		
		if (item.getType().name().contains("GOLD")) {
			e.setDamage(e.getDamage() - 0.5D);
		}
		
		if (item.getType().name().contains("DIAMOND")) {
			e.setDamage(e.getDamage() - 2.0D);
		}
	}
	
	
	
}
