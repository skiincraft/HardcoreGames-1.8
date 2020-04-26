package me.skincraft.inventorymanager;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryContents {

	private String displayname;
	private ItemStack stack;
	private ItemMeta meta;
	private List<String> lore;

	public InventoryContents() {
		this.stack = null;
		this.meta = stack.getItemMeta();
	}
	
	public InventoryContents(Material mat, String displayname, List<String> lore) {
		this.stack = new ItemStack(mat);
		this.displayname = displayname;
		this.meta = stack.getItemMeta();
		this.lore = lore;
	}
	
	
	public ItemStack build() {
		if (stack == null) {
			return null;
		}
		meta.setDisplayName(this.displayname);
		meta.setLore(this.lore);
		
		stack.setItemMeta(meta);
		return stack;
	}
	

}
