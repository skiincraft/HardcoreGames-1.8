package me.skincraft.other.kit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.managers.GroupsManager;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;
import me.skincraft.hardcoregames.mysql.SQLPlayers;

public abstract class KitClass implements Listener {

	private String kitname;
	private String displayname;
	private String[] description;
	
	private ItemStack kititem;
	private ItemStack kiticon;
	
	private boolean listener;
	
	private int cust;
	
	public KitClass(boolean nenhum) {
		kitname = "Nenhum";
		displayname = "Nenhum";
		description = new String[]{"Nenhum"};
		
		kititem = new ItemStack(Material.AIR);
		kiticon = new ItemStack(Material.AIR);
		
		cust = 0;
		
		listener = false;
	}
	
	public KitClass(String kit, String displayname, String[] description) {
		this.kitname = kit;
		this.displayname = displayname;
		this.description = description;
		
		this.kititem = new ItemStack(Material.AIR);
		this.kiticon = new ItemStack(Material.ITEM_FRAME);
		
		this.cust = 10000;
		
		this.listener = false;
	}
	
	public KitClass(String kit, String displayname, String[] description, ItemStack kiticon) {
		this.kitname = kit;
		this.displayname = displayname;
		this.description = description;
		
		this.kititem = new ItemStack(Material.AIR);
		this.kiticon = kiticon;
		
		this.cust = 10000;
		
		this.listener = false;
	}
	
	public KitClass(String kit, String displayname, String[] description, ItemStack kiticon, int custo) {
		this.kitname = kit;
		this.displayname = displayname;
		this.description = description;
		
		this.kititem = new ItemStack(Material.AIR);
		this.kiticon = kiticon;
		
		this.cust = custo + 000;
		
		this.listener = false;
	}
	
	public KitClass(String kit, String displayname, String[] description, ItemStack kiticon, ItemStack kititem, int custo) {
		this.kitname = kit;
		this.displayname = displayname;
		this.description = description;
		
		this.kititem = kititem;
		this.kiticon = kiticon;
		
		this.cust = custo + 000;
		
		this.listener = false;
		
	}
	
	public boolean isListener() {
		return listener;
	}
	
	
	public void setListener() {
		listener = true;
	}
	
	public void endListener() {
		if (isListener()) {
			Bukkit.getPluginManager().registerEvents(this, Main.getMain());
			Bukkit.getConsoleSender().sendMessage("\n§3§lKIT\n§a - §6" + this.getName() + "§a registrado com sucesso.");
		}
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
	
	public boolean hasPermission(Player player) {
		if (Main.freeKits.contains(this.getName())) {
			return true;
		}
		
		return new GroupsManager(new SQLPlayers(player)).hasPermission(Cargos.VIP, getPermissions());
		
	}
	
	public List<String> getKitUsers(){
		Map<String, KitClass> primary = KitManager.KitUtils.primaryKit;
		
		List<String> list = new ArrayList<String>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (primary.containsKey(player.getName())) {
				if (primary.get(player.getName()).getName() == this.getName()) {
					list.add(player.getName());
				}
			}
		}
		return list;
	}
	
	public int getCust() {
		return cust;
	}
	
}
