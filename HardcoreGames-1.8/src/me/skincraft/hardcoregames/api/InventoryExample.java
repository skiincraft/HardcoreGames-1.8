package me.skincraft.hardcoregames.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class InventoryExample {
	
	private Player player;
	public final String inventoryname = "";
	
	public InventoryExample(Player player) {
		this.player = player;
	}
	
	public Inventory Inventario () {
		Inventory inv = createInventory(player, inventoryname, InventorySize.NORMAL);
		
		
		
		return inv;
	}
	
	@EventHandler
	public void itemHotbarInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack ItemInHand = player.getItemInHand();
		ItemStack ItemInteract = player.getItemInHand(); //CHANGE THIS
		if (ItemInHand.getType() != ItemInteract.getType())return;
		
		if (e.getAction() == Action.RIGHT_CLICK_AIR ||
				e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			String hand_DisplayName = ItemInHand.getItemMeta().getDisplayName();
			String Interact_DisplayName = ItemInteract.getItemMeta().getDisplayName();
			if (!ItemInHand.getItemMeta().hasDisplayName())return;
			
			if (hand_DisplayName.equals(Interact_DisplayName)) {
				player.openInventory(Inventario());
			}
		}
	}
	
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void eventoClickInventario(InventoryClickEvent e) {
		Player player = (Player)e.getWhoClicked();
		String titleName = e.getInventory().getTitle();
		if (titleName.equalsIgnoreCase(inventoryname)) {
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().getTypeId() == 0) {
				return;
			}
			e.setCancelled(true);
			
			String[] nameitem = new String[1];
			String display = e.getCurrentItem().getItemMeta().getDisplayName();
			
			if (display.equalsIgnoreCase(nameitem[0])) {
				player.closeInventory();
				
				/*
				 * Executar a ação do comando 
				 * 
				 */
				
				return;
			}
		}
	}
	
	
	
	public static Inventory createInventory(Player player, String inventoryname, InventorySize size) {
		Inventory inv = Bukkit.getServer().createInventory((InventoryHolder)player, size.getSize(), inventoryname);
		return inv;
	}
}
