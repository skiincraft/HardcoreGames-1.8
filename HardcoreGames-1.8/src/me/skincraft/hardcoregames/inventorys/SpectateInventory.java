package me.skincraft.hardcoregames.inventorys;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.skincraft.hardcoregames.api.InventorySize;
import me.skincraft.hardcoregames.managers.KitManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;

public class SpectateInventory {
	
	private Player player;
	public final String inventoryname = "§aMenu de Espectador";
	
	public SpectateInventory(Player player) {
		this.player = player;
	}
	
	@SuppressWarnings("deprecation")
	public Inventory Inventario () {
		Inventory inv = createInventory(player, inventoryname, InventorySize.NORMAL);
		List<String> lista = PlayerHGManager.getList(PlayerState.SPECTATOR);
		
		for (int i = 0;i < lista.size();i++) {
			Player playerSpec = Bukkit.getPlayer(lista.get(i));
			ItemStack skull = new ItemStack(Material.getMaterial(397));
			SkullMeta sm = (SkullMeta) skull.getItemMeta();
			skull.setDurability((short)3);
			sm.setOwner(playerSpec.getName());
			sm.setDisplayName("§a" + playerSpec.getName());
			KitManager kitm = new KitManager(playerSpec);
			
			String kits = "§7" + kitm.getPlayerKit1().getDisplayName() + "/" + kitm.getPlayerKit1().getDisplayName();
			
			String[] str = new String[] 
					       {"§b» " + kits,
							"§eKills: §2" + new PlayerHGManager(playerSpec).getPlayerStreak()};
			
			sm.setLore(Arrays.asList(str));
			skull.setItemMeta(sm);
			inv.addItem(new ItemStack[] { skull });
		}
		
		return inv;
	}
	
	@EventHandler
	public void itemHotbarInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack ItemInHand = player.getItemInHand();
		ItemStack ItemInteract = new ItemStack(Material.SLIME_BALL); //CHANGE THIS
		if (ItemInHand.getType() != ItemInteract.getType())return;
		
		if (e.getAction() == Action.RIGHT_CLICK_AIR ||
				e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			ItemMeta meta = ItemInteract.getItemMeta();
			meta.setDisplayName("§6Jogadores Restantes");
			ItemInteract.setItemMeta(meta);
			
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
