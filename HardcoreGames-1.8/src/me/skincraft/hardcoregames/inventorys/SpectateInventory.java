package me.skincraft.hardcoregames.inventorys;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import me.skincraft.hardcoregames.api.InventorySize;
import me.skincraft.hardcoregames.kit.KitManager;
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
		List<String> lista = PlayerHGManager.getList(PlayerState.ALIVE);
		
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
	

	public static Inventory createInventory(Player player, String inventoryname, InventorySize size) {
		Inventory inv = Bukkit.getServer().createInventory((InventoryHolder)player, size.getSize(), inventoryname);
		return inv;
	}
}
