package me.skincraft.hardcoregames.playerdeathevent;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.enums.ItemList;
import me.skincraft.hardcoregames.managers.GroupsManager;
import me.skincraft.hardcoregames.managers.KitManager;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;
import me.skincraft.hardcoregames.managers.KitManager.Kits;
import me.skincraft.hardcoregames.mysql.SQLPlayers;

public class Utils {

	public static String itemName(ItemStack i) {
		
		ItemList.sendList();
		
		int size = ItemList.itemMaterial.size();
		List<Material> itemM = ItemList.itemMaterial;
		List<String> itemN = ItemList.itemName;
		
				
		String nome = null;
		for (int o = 0; o < size; o++) {
			if (i.getType() == itemM.get(o)) {
				nome = itemN.get(o);
				return nome;
			}
		}
		return i.getItemMeta().getDisplayName();
	}
	
	public static String getHabilidades(Player p) {
		KitManager kit = new KitManager(p);
		if (kit.getPlayerKit2().equals(Kits.Nenhum)) {
			return kit.getPlayerKit1().getDisplayName();
		}
		return "§b" + kit.getPlayerKit1().getDisplayName() + "§7/§b" + kit.getPlayerKit2().getDisplayName();
	}
	
	public static boolean isVip(Player player) {
		GroupsManager group = new GroupsManager(new SQLPlayers(player));
		if (group.hasPermission(Cargos.VIP, "vip.renascer")) {
				return true;
		}
		return false;
	}
	
	public static void spawnarRaio(Player player) {
		player.getLocation().getWorld().strikeLightningEffect(player.getLocation());
	}

	
}
