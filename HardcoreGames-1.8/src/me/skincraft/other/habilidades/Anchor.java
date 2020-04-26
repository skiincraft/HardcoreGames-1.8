package me.skincraft.other.habilidades;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.timers.TimersManager;
import me.skincraft.other.kit.KitClass;

public class Anchor extends KitClass {

	public Anchor() {
		super("anchor",
				"Anchor", new String[] 
						{"§7Fique mais resistente a dano de","§7Espadas, mas tenha cuidado com madeiras!"},
						new ItemStack(Material.ANVIL), 20);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void anchorKitEvent(EntityDamageByEntityEvent e) {
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

		if (!new TimersManager().isAndamento()) {
			return;
		}
		
		player.setVelocity(new Vector());
		damager.setVelocity(new Vector());
		
		Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.getMain(), new Runnable() {
			
			@Override
			public void run() {
				player.setVelocity(new Vector());
				damager.setVelocity(new Vector());
			}
		}, 3L);
		
	}

}
