package me.skincraft.hardcoregames.timers.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import me.skincraft.hardcoregames.timers.TimersManager;

public class AndamentoEvents implements Listener {

	@EventHandler
	public void noBreakBlocks(BlockBreakEvent e) {
		if (new TimersManager().isFinalizando()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void noDamage(EntityDamageEvent e) {
		if (new TimersManager().isFinalizando()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void noEntityDamage(EntityDamageByEntityEvent e) {
		if (new TimersManager().isFinalizando()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void noBlockDamage(EntityDamageByBlockEvent e) {
		if (new TimersManager().isFinalizando()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void noDropItem(PlayerDropItemEvent e) {
		if (new TimersManager().isFinalizando()) {
			e.setCancelled(false);
		}
	}
	
	@EventHandler
	public void noPickupItem(PlayerPickupItemEvent e) {
		if (new TimersManager().isFinalizando()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void noHunger(FoodLevelChangeEvent e) {
		if (new TimersManager().isFinalizando()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void noInteract(PlayerInteractEvent e) {
		if (new TimersManager().isFinalizando()) {
			e.setCancelled(false);
		}
	}
	
	@EventHandler
	public void noPlaceBlock(BlockPlaceEvent e) {
		if (new TimersManager().isFinalizando()) {
			e.setCancelled(true);
		}
	}
	
	
}
