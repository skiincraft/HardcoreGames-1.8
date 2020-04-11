package me.skincraft.hardcoregames.customevents;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.skincraft.hardcoregames.Main;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;

public class RegisterEvent implements Listener {

	
	public static Map<String, String> lastDamage = new HashMap<>();
	@EventHandler
	public void lastDamageCapture(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		
		Player player = (Player) e.getEntity();
		Player damager = (Player) e.getDamager();
		
		if (lastDamage.containsKey(player.getName())) {
			if (lastDamage.get(player.getName())
					.equalsIgnoreCase(damager.getName())) {
				return;
			}
			lastDamage.remove(player.getName());
		}
		
		lastDamage.put(player.getName(), damager.getName());
		
		Bukkit.getScheduler().runTaskLater(Main.getMain(), new Runnable() {
			
			@Override
			public void run() {
				if (lastDamage.get(player.getName()).equals(damager.getName())) {
				lastDamage.remove(player.getName());
				}
				
			}
		}, 10*20L);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
    public void playerKiller(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) return;
		if (!(e.getDamager() instanceof Player)) return;
		
		Player killer = (Player) e.getDamager();
		Player player = (Player) e.getEntity();

		if (!((player.getHealth()-e.getDamage()) <= 0)) {
			return;
		}
		e.setCancelled(true);
		player.setHealth(20);
		
		PlayerDeathByPlayerEvent evento = new PlayerDeathByPlayerEvent(player, killer);
		Bukkit.getPluginManager().callEvent(evento);	
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
    public void playerMob(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) return;
		if ((e.getDamager() instanceof Player)) return;
		
		Entity entity = e.getDamager();
		Player player = (Player) e.getEntity();
		
		if (lastDamage.containsKey(player.getName())) {
			return;
		}

		if (!((player.getHealth()-e.getDamage()) <= 0)) {
			return;
		}
		e.setCancelled(true);
		player.setHealth(20);
		
		PlayerDeathByMobEvent evento = new PlayerDeathByMobEvent(player, entity);
		Bukkit.getPluginManager().callEvent(evento);
	
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerDeathLast(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) return;
		if (e.getCause() == DamageCause.ENTITY_ATTACK) return;
		
		Player player = (Player)e.getEntity();
		
		if (!lastDamage.containsKey(player.getName())) {
			return;
		}
		
		if (!((player.getHealth()-e.getDamage()) <= 0)) {
			return;
		}
		
		Player killer = Bukkit.getPlayer(lastDamage.get(player.getName()));
		player.setHealth(20);
		
		e.setCancelled(true);
		PlayerDeathByPlayerEvent evento = new PlayerDeathByPlayerEvent(player, killer);
		Bukkit.getPluginManager().callEvent(evento);	
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerDeath(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) return;
		
		Player player = (Player)e.getEntity();
		
		if (lastDamage.containsKey(player.getName())) {
			return;
		}
		
		if (!((player.getHealth()-e.getDamage()) <= 0)) {
			return;
		}
		
		player.setHealth(20);
		e.setCancelled(true);
		PlayerDeathEvent evento = new PlayerDeathEvent(player, e.getCause());
		Bukkit.getPluginManager().callEvent(evento);	
	}
	
	@EventHandler
	public void respawn(org.bukkit.event.entity.PlayerDeathEvent e) { //CASO ACONTEÇA DE BUGAR ACIMA, ele renasce auto.
		final Player p = e.getEntity();
		if (p.isDead()) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				public void run() {
					((CraftPlayer) p).getHandle().playerConnection
							.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
				}
			}, 15L);
		}
	}
	
}
