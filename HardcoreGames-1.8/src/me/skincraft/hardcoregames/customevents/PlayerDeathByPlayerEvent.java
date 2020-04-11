package me.skincraft.hardcoregames.customevents;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.managers.GroupsManager;
import me.skincraft.hardcoregames.managers.KitManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;
import me.skincraft.hardcoregames.managers.KitManager.Kits;
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;
import me.skincraft.hardcoregames.mysql.SQLPlayers;
import me.skincraft.hardcoregames.playerdeathevent.Utils;

public class PlayerDeathByPlayerEvent extends Event{
	
	private Player player;
	private Player killer;
	private boolean raio;

	private DamageCause cause;
	
	private static final HandlerList handlers = new HandlerList();
	 
	public PlayerDeathByPlayerEvent(Player player, Player killer) {
		this.player = player;
		this.killer = killer;
		this.cause = DamageCause.ENTITY_ATTACK;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public DamageCause getCause() {
		return cause;
	}
	
	public Player getKiller() {
		return killer;
	}
	
	public ItemStack getItemKillerHand() {
		return killer.getItemInHand();
	}
	
	public ItemStack getItemPlayerHand() {
		return player.getItemInHand();
	}
	
	public KitManager getPlayerKitM() {
		return new KitManager(player);
	}
	
	public KitManager getKillerKitM() {
		return new KitManager(killer);
	}
	
	private String getKit(Player player) {
		KitManager kit = new KitManager(player);
		if (kit.getPlayerKit2().equals(Kits.Nenhum)) {
			return kit.getPlayerKit1().getDisplayName();
		}
		return kit.getPlayerKit1().getDisplayName() + "§7/" + kit.getPlayerKit2().getDisplayName();
	}
	
	public String getDeathAnnounce() {
		String sknz = killer.getName();
		String aln = player.getName();
		
		String[] kit = new String[1];
		kit[0] = "§7("+ getKit(killer) + "§7)";
		kit[1] = "§7("+ getKit(player) + "§7)";
		
		String[] deathAnnounce = new String[] 
				{"§7" + sknz + kit[0], "§bmatou","§7" + aln + kit[1], "usando sua", Utils.itemName(getItemKillerHand())};
		
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
