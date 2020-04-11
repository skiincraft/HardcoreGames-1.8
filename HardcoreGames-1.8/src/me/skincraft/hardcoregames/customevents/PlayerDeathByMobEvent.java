package me.skincraft.hardcoregames.customevents;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.managers.GroupsManager;
import me.skincraft.hardcoregames.managers.KitManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;
import me.skincraft.hardcoregames.mysql.SQLPlayers;

public class PlayerDeathByMobEvent extends Event {
	
	private Player player;
	private Entity entidade;
	private DamageCause cause;
	private boolean raio;
	
	private static final HandlerList handlers = new HandlerList();
	 
	public PlayerDeathByMobEvent(Player player, Entity entidade) {
		this.player = player;
		this.cause = DamageCause.ENTITY_ATTACK;
		this.entidade = entidade;
		this.raio = false;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public DamageCause getCause() {
		return cause;
	}
	
	public Entity getEntity() {
		return entidade;
	}
	
	public ItemStack getItemPlayerHand() {
		return player.getItemInHand();
	}
	
	public KitManager getPlayerKitM() {
		return new KitManager(player);
	}
	
	public String getDeathAnnounce() {
		String[] deathAnnounce;
		if (entidade instanceof Animals) {
			deathAnnounce = new String[] 
					{"§7" + player.getName() + "morreu para um mob."};
			
			return StringUtils.join(deathAnnounce, " ");	
		}
		deathAnnounce = new String[] 
				{"§7" + player.getName() + "morreu para um monstro."};
		
		return StringUtils.join(deathAnnounce, " ");	
	}
	
	public boolean isRaio() {
		return raio;
	}
	
	public boolean hasVIP() {
		if (new GroupsManager(new SQLPlayers(player))
				.hasPermission(Cargos.VIP, "vip.reviver")) {
			return true;
		}
		return false;
	}

	public void setRaio(boolean raio) {
		if (!this.raio) {
			if (raio) {
				Location loc = player.getLocation();
				loc.getWorld().strikeLightningEffect(loc);
			}
		}
		this.raio = raio;
	}
	
	public int getRemains() {
		if (PlayerHGManager.getList(PlayerState.SPECTATOR).contains(player.getName())) {
			return PlayerHGManager.getSize(PlayerState.ALIVE);
		}
		
		if (PlayerHGManager.getList(PlayerState.DEAD).contains(player.getName())) {
			return PlayerHGManager.getSize(PlayerState.ALIVE);
		}
		return PlayerHGManager.getSize(PlayerState.ALIVE) - 1;
	}
	
	@Override
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
