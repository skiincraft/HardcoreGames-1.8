package me.skincraft.inventorymanager.kitselector;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.api.ItemConstruct;
import me.skincraft.other.kit.KitClass;

public class SelectorUtils {

	public SelectorUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public ItemStack kitIcon(KitClass kit) {
		return kit.getKitIcon();
	}
	
	public ItemStack noKitIcon(KitClass kit) {
		ItemConstruct noKit = new ItemConstruct(Material.STAINED_GLASS_PANE, null, 14)
				.setLore("§cVocê não possui o kit §a" + kit.getDisplayName())
				.setItemName("§4" + "א" + kit.getDisplayName());
		ItemStack stack = noKit.buildItemStack();
		return stack;
	}
	
	public ItemStack disabledKitIcon(KitClass kit) {
		ItemConstruct disabledKit = new ItemConstruct(Material.STAINED_GLASS_PANE, null, 7)
				.setLore("§cEste foi desativado!")
				.setItemName("§7Ð " + kit.getDisplayName());
		ItemStack stack = disabledKit.buildItemStack();
		return stack;
	}

}
