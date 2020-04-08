package me.skincraft.hardcoregames.events;

import org.bukkit.entity.CreatureType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

@SuppressWarnings("deprecation")
public class NoSpawnMob implements Listener {

	@EventHandler
	public void onSummonGhast(CreatureSpawnEvent e) {
		if (e.getCreatureType() != CreatureType.GHAST) {
			return;
		}
		if (e.getCreatureType() == CreatureType.GHAST) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onSummonGuardian(CreatureSpawnEvent e) {
		if (e.getCreatureType() != CreatureType.GUARDIAN) {
			return;
		}
		if (e.getCreatureType() == CreatureType.GUARDIAN) {
			e.setCancelled(true);
		}
	}
	
}
