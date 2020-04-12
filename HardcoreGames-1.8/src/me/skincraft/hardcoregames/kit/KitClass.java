package me.skincraft.hardcoregames.kit;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class KitClass {

	private String kitname;
	private String displayname;
	private String[] description;
	
	private ItemStack kititem;
	private ItemStack kiticon;
	
	private int cust;
	
	public KitClass(boolean nenhum) {
		kitname = "Nenhum";
		displayname = "Nenhum";
		description = new String[]{"Nenhum"};
		
		kititem = new ItemStack(Material.AIR);
		kiticon = new ItemStack(Material.AIR);
		
		cust = 0;
	}
	
	public KitClass(String kit, String displayname, String[] description) {
		this.kitname = kit;
		this.displayname = displayname;
		this.description = description;
		
		this.kititem = new ItemStack(Material.AIR);
		this.kiticon = new ItemStack(Material.ITEM_FRAME);
		
		this.cust = 10000;
	}
	
	public KitClass(String kit, String displayname, String[] description, ItemStack kiticon) {
		this.kitname = kit;
		this.displayname = displayname;
		this.description = description;
		
		this.kititem = new ItemStack(Material.AIR);
		this.kiticon = kiticon;
		
		this.cust = 10000;
	}
	
	public KitClass(String kit, String displayname, String[] description, ItemStack kiticon, int custo) {
		this.kitname = kit;
		this.displayname = displayname;
		this.description = description;
		
		this.kititem = new ItemStack(Material.AIR);
		this.kiticon = kiticon;
		
		this.cust = custo + 000;
	}
	
	public KitClass(String kit, String displayname, String[] description, ItemStack kiticon, ItemStack kititem, int custo) {
		this.kitname = kit;
		this.displayname = displayname;
		this.description = description;
		
		this.kititem = kititem;
		this.kiticon = kiticon;
		
		this.cust = custo + 000;
	}
	
	public String getName() {
		return kitname;
	}
	
	public String getDisplayName() {
		return displayname;
	}
	
	public String[] getDescription() {
		return description;
	}
	
	public ItemStack getKitItem() {
		return kititem;
	}
	
	public String getFullDisplayName() {
		return ChatColor.GREEN + displayname;
	}
	
	public ItemStack getKitIcon() {
		ItemStack kitcon = kiticon;
		ItemMeta meta = kiticon.getItemMeta();
		String[] desc = new String[description.length]; 
		
		for (int i = 0; i < description.length;i++) {
			desc[i] = "§7" + description[i];
		}
		
		meta.setDisplayName(getFullDisplayName());
		meta.setLore(Arrays.asList(desc));
		kitcon.setItemMeta(meta);
		
		return kitcon;
	}
	
	public String getPermissions() {
		return "kit." + kitname;
	}
	
	public int getCust() {
		return cust;
	}
	
}
