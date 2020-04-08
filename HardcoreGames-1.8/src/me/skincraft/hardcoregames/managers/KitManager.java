package me.skincraft.hardcoregames.managers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitManager {
	
	@SuppressWarnings("unused")
	private Player playerM;
	private String player;
	
	public KitManager(Player playerM) {
		this.playerM = playerM;
		this.player = playerM.getName();
	}
	
	public static class KitUtils{
		public static Map<String, Kits> primaryKit = new HashMap<>();
		public static Map<String, Kits> secoundaryKit = new HashMap<>();
	}
	
	public ItemStack getItemKit1() {
		Material kitItem = getPlayerKit1().getKitItem();
		String name = getPlayerKit1().getDisplayName();
		
		ItemStack item = new ItemStack(kitItem, 1, (short) 0);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(name);
		item.setItemMeta(itemmeta);
		
		return item;
		
	}
	
	public ItemStack getItemKit2() {
		Material kitItem = getPlayerKit2().getKitItem();
		String name = getPlayerKit2().getDisplayName();
		
		ItemStack item = new ItemStack(kitItem, 1, (short) 0);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(name);
		item.setItemMeta(itemmeta);
		
		return item;
		
	}
	
	public Kits getPlayerKit1() {
		if (!KitUtils.primaryKit.containsKey(player)) {
			setKit1(Kits.Nenhum);
		}
		return KitUtils.primaryKit.get(player);
	}
	
	public Kits getPlayerKit2() {
		if (!KitUtils.secoundaryKit.containsKey(player)) {
			setKit2(Kits.Nenhum);
		}
		return KitUtils.secoundaryKit.get(player);
	}
	
	public void setKit1(Kits kit) {
		KitUtils.primaryKit.put(player, kit);
		return;
	}
	
	public void setKit2(Kits kit) {
		KitUtils.secoundaryKit.put(player, kit);
		return;
	}
	
	public void removeKit1() {
		KitUtils.primaryKit.replace(player, Kits.Nenhum);
	}
	
	public void removeKit2() {
		KitUtils.secoundaryKit.replace(player, Kits.Nenhum);
	}
	
	public void clear() {
		KitUtils.primaryKit.replace(player, Kits.Nenhum);
		KitUtils.secoundaryKit.replace(player, Kits.Nenhum);
	}
	
	public boolean containsKit(Kits kit) {
		if (hasSelectedKit1(kit)) {
			return true;
		}
		
		if (hasSelectedKit2(kit)) {
			return true;
		}
		return false;
	}
	
	public boolean hasSelectedKit1(Kits kit) {
		if (!KitUtils.primaryKit.containsKey(player)) {
			setKit1(Kits.Nenhum);
		}
		if (KitUtils.primaryKit.get(player).equals(kit)) {
			return true;
		}
		return false;
	}
	
	public boolean hasSelectedKit2(Kits kit) {
		if (!KitUtils.secoundaryKit.containsKey(player)) {
			setKit1(Kits.Nenhum);
		}
		return false;
	}
	
	public void setKit1WithPermission(Kits kit) {
		//
	}
	
	public void setKit2WithPermission(Kits kit) {
		//
	}
	
	
	
	public enum Kits {
		
		Nenhum(Material.AIR, "Nenhum", 0),
		Achilles(Material.WOOD_SWORD, "Achilles", 15000),
		Anchor(Material.ANVIL, "Anchr", 20000),
		Antitower(Material.IRON_HELMET, "Antitower", 10000),
		Boxer(Material.STONE_SWORD, "Boxer", 15000),
		Buildtower(Material.DIRT, "Buildtower", 10000),
		C4(Material.SLIME_BALL, "C4", 20000),
		CopyCat(Material.SLIME_BALL, "CopyCat", 20000),
		Deshfire(Material.REDSTONE_BLOCK, "Deshfire", 20000),
		Endermage(Material.PORTAL, "Endermage", 15000),
		Fireman(Material.WATER_BUCKET, "Fireman", 15000),
		Fisherman(Material.FISHING_ROD, "Fisherman", 10000),
		Flash(Material.REDSTONE_TORCH_ON, "Flash", 15000),
		Gladiator(Material.IRON_FENCE, "Gladiator", 25000),
		Grappler(Material.LEASH, "Grappler", 25000),
		Hotpotato(Material.POTATO, "Hot-Potato", 15000),
		Hulk(Material.SADDLE, "Hulk", 15000),
		Jumper(Material.FISHING_ROD, "Jumper", 15000),
		Kangaroo(Material.FIREWORK, "Kangaroo", 30000),
		Launcher(Material.SPONGE, "Launcher", 15000),
		Lumberjack(Material.WOOD_AXE, "Lumberjack", 5000),
		Magma(Material.MAGMA_CREAM, "Magma", 15000),
		Miner(Material.STONE_PICKAXE, "Miner", 5000),
		Monk(Material.BLAZE_ROD, "Monk", 15000),
		Ninja(Material.EMERALD, "Ninja", 30000),
		Phantom(Material.FEATHER, "Phantom", 10000),
		Reaper(Material.STONE_HOE, "Reaper", 10000),
		Snail(Material.WEB, "Snail", 10000),
		Specialist(Material.ENCHANTED_BOOK, "Specialist", 15000),
		Stomper(Material.IRON_BOOTS, "Stomper", 20000),
		Surprise(Material.ITEM_FRAME, "Surprise", 0),
		Tank(Material.DIAMOND_CHESTPLATE, "Tank", 15000),
		Thor(Material.WOOD_AXE, "Thor", 15000),
		Tower(Material.GOLD_BOOTS, "Tower", 23000),
		Viking(Material.STONE_AXE, "Viking", 15000),
		Viper(Material.SPIDER_EYE, "Viper", 10000),
		Worm(Material.DIRT, "Worm", 5000);
		
		Material kitItem;
		String displayName;
		int cust;
		
		Kits(Material kitItem, String displayName, int cust){
			this.kitItem = kitItem;
			this.displayName = displayName;
			this.cust = cust;
		}
		
		public Material getKitItem() {
			return this.kitItem;
		}
		
		public String getName() {
			return this.name();
		}
		
		public String getDisplayName() {
			return this.displayName;	
		}
		
		public static List<Kits> getList(){
			return Arrays.asList(values());
		}
		
		public String getPermission() {
			return "kit." + this.getName().toLowerCase();
		}
		
		public int getCust() {
			return this.cust;
		}
		
	}

}
