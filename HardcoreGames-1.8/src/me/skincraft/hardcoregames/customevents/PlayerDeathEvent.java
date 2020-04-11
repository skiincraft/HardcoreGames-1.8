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
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;
import me.skincraft.hardcoregames.mysql.SQLPlayers;

public class PlayerDeathEvent extends Event{
	
	private Player player;
	private DamageCause cause;
	private boolean raio;
	
	private static final HandlerList handlers = new HandlerList();
	 
	public PlayerDeathEvent(Player player, DamageCause cause) {
		this.player = player;
		this.cause = cause;
		this.raio = false;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public DamageCause getCause() {
		return cause;
	}
	
	public ItemStack getItemPlayerHand() {
		return player.getItemInHand();
	}
	
	public KitManager getPlayerKitM() {
		return new KitManager(player);
	}
	
	public String getDeathAnnounce() {
		String[] deathAnnounce;
		
			deathAnnounce = new String[] {"§7" + player.getName(),"§7morreu por uma flecha."};
		
		if (cause == DamageCause.CONTACT) { //cacto
			deathAnnounce = new String[] {"§7" + player.getName(),"§7morreu espetado por um cacto."};
		}
		
		if (cause == DamageCause.SUFFOCATION) { //sufocado
			deathAnnounce = new String[] {"§7" + player.getName(),"§7morreu sufocado"};
		}
		
		if (cause == DamageCause.FALL) { //queda
			deathAnnounce = new String[] {"§7" + player.getName(),"§7morreu caindo de um lugar alto."};
		}
		
		if (cause == DamageCause.FIRE) { //fogo
			deathAnnounce = new String[] {"§7" + player.getName(),"§7morreu pegando fogo."};
		}
		
		if (cause == DamageCause.FIRE_TICK) { //fogo
			deathAnnounce = new String[] {"§7" + player.getName(),"§7morreu pegando fogo."};
		}
		
		if (cause == DamageCause.MELTING) { //neve
			deathAnnounce = new String[] {"§7" + player.getName(),"§7morreu por uma bola de neve."};
		}
		
		if (cause == DamageCause.LAVA) { //lava
			deathAnnounce = new String[] {"§7" + player.getName(),"§7morreu nadando na lava."};
		}
		
		if (cause == DamageCause.DROWNING) { //afogado
			deathAnnounce = new String[] {"§7" + player.getName(),"§7morreu afogado."};
		}
		
		if (cause == DamageCause.BLOCK_EXPLOSION) { //explosão
			deathAnnounce = new String[] {"§7" + player.getName(),"§7morreu em uma explosão."};
		}
		
		if (cause == DamageCause.ENTITY_EXPLOSION) { //explosão
			deathAnnounce = new String[] {"§7" + player.getName(),"§7morreu em uma explosão."};
		}
		
		if (cause == DamageCause.VOID) { //void
			deathAnnounce = new String[] {"§7" + player.getName(),"§7simplesmente morreu."};
		}
		
		if (cause == DamageCause.LIGHTNING) { //raio
			deathAnnounce = new String[] {"§7" + player.getName(),"§7morreu atingido por um raio"};
		}
		
		if (cause == DamageCause.SUICIDE) { // /kill
			deathAnnounce = new String[] {"§7" + player.getName(),"§7simplesmente morreu."};
		}
		
		if (cause == DamageCause.STARVATION) { // fome
			deathAnnounce = new String[] {"§7" + player.getName(),"§7morreu com muita fome"};
		}
		
		if (cause == DamageCause.POISON) { // veneno
			deathAnnounce = new String[] {"§7" + player.getName(),"§7morreu envenenado"};
		}
		
		if (cause == DamageCause.MAGIC) { // magia
			deathAnnounce = new String[] {"§7" + player.getName(),"§7morreu atingido por magia"};
		}
		
		if (cause == DamageCause.WITHER) { // wither
			deathAnnounce = new String[] {"§7" + player.getName(),"§7morreu com efeito do wither"};
		}
		
		if (cause == DamageCause.FALLING_BLOCK) { // esmagado
			deathAnnounce = new String[] {"§7" + player.getName(),"§7morreu esmagado por um bloco."};
		}
		
		if (cause == DamageCause.THORNS) { // esmagado
			deathAnnounce = new String[] {"§7" + player.getName(),"§7morreu refletindo o proprio dano"};
		}
		
		if (cause == DamageCause.CUSTOM) { // esmagado
			deathAnnounce = new String[] {"§7" + player.getName(),"§7simplemente morreu."};
		}
		
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
