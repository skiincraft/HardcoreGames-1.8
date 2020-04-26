package me.skincraft.hardcoregames.events;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;

public class PlayerSpectatorEvents implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityTarget(EntityTargetEvent event) {
		if ((event.getTarget() != null) && (event.getTarget().getType() == EntityType.PLAYER)) {
			Player player = (Player) event.getTarget();
			if ((new PlayerHGManager(player).getPlayerState() == PlayerState.SPECTATOR) && (!event.isCancelled())) {
				EntityType type = event.getEntity().getType();
				if ((type == EntityType.ENDERMAN) || (type == EntityType.WOLF) || (type == EntityType.PIG_ZOMBIE)
						|| (type == EntityType.BLAZE) || (type == EntityType.CAVE_SPIDER)
						|| (type == EntityType.CREEPER) || (type == EntityType.GHAST) || (type == EntityType.MAGMA_CUBE)
						|| (type == EntityType.SILVERFISH) || (type == EntityType.SKELETON)
						|| (type == EntityType.SLIME) || (type == EntityType.SPIDER) || (type == EntityType.WITCH)
						|| (type == EntityType.WITHER_SKULL) || (type == EntityType.ZOMBIE)
						|| (type == EntityType.IRON_GOLEM) || (type == EntityType.ENDER_DRAGON)
						|| (type == EntityType.WITHER) || (type == EntityType.GUARDIAN)) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (PlayerHGManager.getList(PlayerState.SPECTATOR).contains(player.getName())) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {

		Player p = event.getPlayer();
		for (int i = 0; i < 3; i++) {
			List<Entity> entities = p.getNearbyEntities(i, i, i);
			for (Entity e : entities) {
				if ((e.getType().equals(EntityType.PLAYER)) && PlayerHGManager.getList(PlayerState.SPECTATOR).contains(p.getName())) {
					if (p.hasPermission("trial.Admin")) {
						return;
					}
					if (!((Player) e).isDead()) {
						p.teleport(p.getLocation().add(0.0D, 2.5D, 0.0D));
						p.sendMessage("Você não pode chegar perto de §c" + ((Player) e).getName());
						p.setFlying(true);
						return;
					}
				}
			}
		}
	}

	@EventHandler
	public void playerSpecDamage(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		if (!(e.getDamager() instanceof Player)) {
			return;
		}

		Player p1 = (Player) e.getEntity();
		Player p2 = (Player) e.getDamager();
		if (PlayerHGManager.getList(PlayerState.SPECTATOR).contains(p1.getName())) {
			e.setCancelled(true);
			return;
		}
		if (PlayerHGManager.getList(PlayerState.SPECTATOR).contains(p2.getName())) {
			e.setCancelled(true);
			return;
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void specAntiDup(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (PlayerHGManager.getList(PlayerState.SPECTATOR).contains(p.getName())) {
			if (e.getCurrentItem().getType() != Material.getMaterial(397)) {
				return;
			}
			e.setCancelled(true);
			//p.sendMessage("Você não pode pegar itens no modo Espectador!");
			return;
		}
	}

	@EventHandler
	public void specBlockEvent(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (PlayerHGManager.getList(PlayerState.SPECTATOR).contains(p.getName())) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void specCancelKit(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (PlayerHGManager.getList(PlayerState.SPECTATOR).contains(player.getName())) {
			return;
		}

		if (event.getMessage().toLowerCase().startsWith("/kit ")) {
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void specDamageMobs(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) {
			return;
		}

		Player p = (Player) e.getDamager();
		if (PlayerHGManager.getList(PlayerState.SPECTATOR).contains(p.getName())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void specDisableDamage(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		Player player = (Player) e.getEntity();
		Player damager = (Player) e.getDamager();
		if (PlayerHGManager.getList(PlayerState.SPECTATOR).contains(player.getName())) {
			e.setCancelled(true);
			return;
		}

		if (PlayerHGManager.getList(PlayerState.SPECTATOR).contains(damager.getName())) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void specDisableDamage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}

		Player player = (Player) e.getEntity();
		if (PlayerHGManager.getList(PlayerState.SPECTATOR).contains(player.getName())) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void specDropItem(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		if (PlayerHGManager.getList(PlayerState.SPECTATOR).contains(player.getName())) {
			e.setCancelled(true);
			return;
		}

		if (PlayerHGManager.getList(PlayerState.ADMINMODE).contains(player.getName())) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void specFoodLevel(FoodLevelChangeEvent e) {
		Player player = (Player) e.getEntity();
		if (PlayerHGManager.getList(PlayerState.SPECTATOR).contains(player.getName())) {
			e.setFoodLevel(20);
		}
	}

	@EventHandler
	public void specItemInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (!PlayerHGManager.getList(PlayerState.SPECTATOR).contains(player.getName())) {
			return;
		}
		if (player.getItemInHand().getType() == Material.FLINT_AND_STEEL) {
			e.setCancelled(true);
			return;
		}
		
		if (player.getItemInHand().getType() == Material.LAVA_BUCKET) {
			e.setCancelled(true);
			return;
		}
		
		if (player.getItemInHand().getType() == Material.WATER_BUCKET) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void specItemPickup(PlayerPickupItemEvent e) {
		Player player = e.getPlayer();
		if (PlayerHGManager.getList(PlayerState.SPECTATOR).contains(player.getName())) {
			e.setCancelled(true);
			return;
		} else if (PlayerHGManager.getList(PlayerState.ADMINMODE).contains(player.getName())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void spectatorBlockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		if (PlayerHGManager.getList(PlayerState.SPECTATOR).contains(player.getName())) {
			if (player.hasPermission("trial.admin")) {
				e.setCancelled(false);
				return;
			}
			e.setCancelled(true);
			return;
		}
	}
/*
	@EventHandler
	public void specThorDamage(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}

		Player player = (Player) event.getEntity();
		if ((event.getEntity() instanceof LightningStrike)) {
			if (new KitManager(player).containsKit(new Thor())) {
				event.setDamage(0.0D);
			} else {
				event.setDamage(4.0D);
				event.getEntity().setFireTicks(200);
			}
		}
	}*/
}
