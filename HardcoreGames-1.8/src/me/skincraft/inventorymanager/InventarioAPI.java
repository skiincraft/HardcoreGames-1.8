package me.skincraft.inventorymanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.skincraft.hardcoregames.Main;

public abstract class InventarioAPI implements Listener {
	
	private Player player;
	private ItemStack hotbaritem;
	private String inventoryname = "inventoryexample";
	private String hotbaritemname = "@sknz";
	private List<ItemStack> contentHotbar = new ArrayList<ItemStack>();
	private List<String> lore = new ArrayList<String>();
	private boolean listener;
	
	
	public InventarioAPI(ItemStack hotbaritem, String hotbaritemname, String inventoryname) {
		this.hotbaritem = hotbaritem;
		this.inventoryname = inventoryname;
		this.hotbaritemname = hotbaritemname;
		this.lore.add("");
		contentHotbar.clear();
		listener = false;
	}
	
	public InventarioAPI(ItemStack hotbaritem, String hotbaritemname, String inventoryname, List<String> hotbarlore, ItemStack[] contents) {
		this.hotbaritem = hotbaritem;
		this.inventoryname = inventoryname;
		this.hotbaritemname = hotbaritemname;
		this.lore = hotbarlore;
		contentHotbar.addAll(Arrays.asList(contents));
		listener = false;
	}
	
	public InventarioAPI(String inventoryname) {
		this.inventoryname = inventoryname;
		this.hotbaritem = new ItemStack(Material.APPLE);
		this.hotbaritemname = "@sknz";
		this.lore.add("");
		contentHotbar.clear();
		listener = false;
	}
	
	
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	public List<ItemStack> getContents() {
		return contentHotbar;
	}
	
	public static ItemStack contentItem(Material mat, String displayname, List<String> lore) {
		ItemStack s = new ItemStack(mat);
		ItemMeta m = s.getItemMeta();

		m.setDisplayName(displayname);
		
		if (lore != null) {
			m.setLore(lore);	
		}
		
		s.setItemMeta(m);
		return s;
	}
	
	public void addContents(List<ItemStack> content) {
		contentHotbar.addAll(content);
	}
	
	public ItemStack getHotbaritem() {
		ItemMeta meta = hotbaritem.getItemMeta();
		meta.setDisplayName(hotbaritemname);
		meta.setLore(lore);
		hotbaritem.setItemMeta(meta);
		
		return hotbaritem;
	}
	
	public String getInventoryName() {
		return inventoryname;
	}
	
	public void setInventoryName(String name) {
		this.inventoryname = name;
	}
	
	public String getHotbarItemName() {
		return hotbaritemname;
	}
	public abstract Inventory InvMake(Player player);
	
	public boolean itemHotbarChekers(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack handItem = player.getItemInHand();
		ItemStack interact = getHotbaritem();
		
		if (handItem.getType() != interact.getType()) return false;
		if (!handItem.getItemMeta().hasDisplayName()) return false;
		
		if (e.getAction() == Action.RIGHT_CLICK_AIR ||
				e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			return true;
		}
		
		return false;
	}
	
	
	@SuppressWarnings("deprecation")
	public boolean clickInventoryCheckers(InventoryClickEvent e) {
		if (e.getInventory().getTitle().equalsIgnoreCase(getInventoryName())) {
		if (e.getCurrentItem() == null) {
			return false;
		}
		if (e.getCurrentItem().getTypeId() == 0) {
			return false;
		}
		
		e.setCancelled(true);
		return true;
		}
		return false;
	}
	
	
		@SuppressWarnings("deprecation")
		public boolean clickInventoryCheckers(InventoryClickEvent e, String[] str) {
			String titleName = e.getInventory().getTitle();
			if (titleName.equalsIgnoreCase(str[0]) || titleName.equalsIgnoreCase(str[1])) {
			if (e.getCurrentItem() == null) {
				return false;
			}
			if (e.getCurrentItem().getTypeId() == 0) {
				return false;
			}
			
			e.setCancelled(true);
			return true;
			}
			return false;
		}
	/*
	@EventHandler
	public void itemHotbarInteract(PlayerInteractEvent e) {
		if (itemHotbarChekers(e) == false) return;
		
		Player player = e.getPlayer();
		
		String hand = player.getItemInHand().getItemMeta().getDisplayName();
		String interact = getHotbarItemName();
		
		if (hand.equals(interact)) {
			// ACTION
		}
	}
	*/
	
	/* 
	@EventHandler
	public void clickInventario(InventoryClickEvent e) {
		Player player = (Player)e.getWhoClicked();
		if (clickInventoryCheckers(e) == false) return;
		
		String[] nameitem = new String[1];
		String display = e.getCurrentItem().getItemMeta().getDisplayName();
			
		if (display.equalsIgnoreCase(nameitem[0])) {
			player.closeInventory();
			
			executar ação
		}
	}
	*/
	
	@SuppressWarnings("deprecation")
	public ItemStack createSkull(String owner, String[] lore) {
		ItemStack skull = new ItemStack(Material.getMaterial(397));
		SkullMeta skullmeta = (SkullMeta)skull.getItemMeta();
		skull.setDurability((short)3);
		skullmeta.setOwner(owner);
		skullmeta.setDisplayName("§a" + owner);
		skullmeta.setLore(Arrays.asList(lore));
		
		skull.setItemMeta(skullmeta);
		return skull;
	}
	
	public Inventory createInventory(Player player, InventorySize size) {
		Inventory inv = Bukkit.getServer().createInventory((InventoryHolder)player, size.getSize(), getInventoryName());
		this.player = player;
		return inv;
	}
	
	public boolean isListener() {
		return listener ;
	}
	
	
	public void setListener() {
		listener = true;
	}
	
	public void endListener() {
		if (isListener()) {
			Bukkit.getPluginManager().registerEvents(this, Main.getMain());
			Bukkit.getConsoleSender().sendMessage("§3§lINVENTARIO\n§a - §6" + this.getClass().getName() + "§a registrado com sucesso.");
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void createContent() {
	}
}
