package me.skincraft.inventorymanager.kitselector;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.api.ItemConstruct;
import me.skincraft.inventorymanager.InventarioAPI;
import me.skincraft.inventorymanager.InventorySize;
import me.skincraft.other.kit.KitClass;
import me.skincraft.other.kit.KitManager;

public class KitSelector extends InventarioAPI {

	public KitSelector(ItemStack hotbaritem, String hotbaritemname, String inventoryname, List<String> hotbarlore,
			ItemStack[] contents) {
		super(new ItemStack(Material.CHEST), "§6Kit 1 §7[Seletor de Kits]", "§aKits Primários [1]",
				Arrays.asList(" ","§7Utilize isso para escolher", "§7o seu kit primario."), 
				null);
	}
	
	@Override
	public Inventory InvMake(Player player) {
		return Inventario(player, Page.PAGE_ONE);
	}
	
	public Inventory Inventario(Player player, Page page) {
		Inventory inv = createInventory(player, InventorySize.BIG);
		setInventoryName(page.getPageName());
		setPlayer(player);
		
		options(inv, page);
		int inicial = 19;
		int fim = 19+7;
		
		List<KitClass> kit = Main.kitlist;
		int i = page.getInt();
		
		SelectorUtils utils = new SelectorUtils();
		
		for (inicial = 19; inicial < fim; inicial++) {
			KitClass getter = kit.get(i);
			
			inv.setItem(inicial, utils.kitIcon(getter));
			
			if (getter.hasPermission(player)) {
				inv.setItem(inicial, utils.noKitIcon(getter));
			}
			
			//TOGGLEKIT
			
			i++;
		}
		
		inicial = 28;
		fim = 28+7;
		
		for (inicial = 28; inicial < fim; inicial++) {
			KitClass getter = kit.get(i);
			
			
			inv.setItem(inicial, utils.kitIcon(getter));
			
			if (getter.hasPermission(player)) {
				inv.setItem(inicial,
						utils.noKitIcon(getter));
			}
			
			//TOGGLEKIT
			i++;
		}
		
		inicial = 37;
		fim = 37+7;
		
		for (inicial = 37; inicial < fim; inicial++) {
			KitClass getter = kit.get(i);
			if (page.equals(Page.PAGE_TWO)) {
				if (i >= Main.kitlist.size()) {
					return inv;
				}
			}
			
			inv.setItem(inicial, utils.kitIcon(getter));
			
			if (getter.hasPermission(player)) {
				inv.setItem(inicial, utils.noKitIcon(getter));
			}
			
			//TOGGLEKIT
			i++;
		}
		return inv;
	}
	
	public void options (Inventory inv, Page page) {
		KitManager kitm = new KitManager(getPlayer());
		String kitSelected = kitm.getPlayerKit1().getDisplayName();
		Material matKitSelected = Material.BED;//kitm.getItemKit1().getType();
		
		String[] option = page.getCarpets();
		new ItemConstruct(Material.CHEST, "§aKits Primários", 3, inv, "","§7Abra o menu de kits primários").build();
		new ItemConstruct(Material.ANVIL, "§a§5Status", 4, inv, "","§7Abra o menu de status").build();
		new ItemConstruct(Material.TRAPPED_CHEST, "§aKits Secundários", 5, inv, "","§7Abra o menu de kits secundários").build();
		new ItemConstruct(Material.CARPET, option[0], 48, inv, "","§7Retorne no menu anterior").setType(7).build();
		new ItemConstruct(Material.CARPET, option[1], 50, inv, "","§7Avance para o proximo menu").setType(5).build();
		
		new ItemConstruct(matKitSelected, "§6Kit Selecionado: §a" + kitSelected, 49, inv, "","§7Click para mais informações").build();
	}
	
	public void clickInventoryEvent(InventoryClickEvent e) {
		Player player = (Player)e.getWhoClicked();
		String page1 = Page.PAGE_ONE.getPageName();
		String page2 = Page.PAGE_ONE.getPageName();
		
		if (!clickInventoryCheckers(e, new String[] {page1, page2})) return;
		
		String[] kitselector = new String[2];
		String[] contains = new String[2];
		String[] message = new String[2];
		String[] pagelistone = Page.PAGE_ONE.getCarpets();
		String[] pagelisttwo = Page.PAGE_TWO.getCarpets();
		
		contains[0] = "§4" + "א"; message[0] = "§cVocê não obtem este kit, você pode adquirir comprando.";
		contains[1] = "§7Ð "; message[1] = "§cEsse kit foi desativado.";
		kitselector[0] = "§aKits Primários";kitselector[1] = "§aKits Secundários";
		
		String display = e.getCurrentItem().getItemMeta().getDisplayName();
		
		if (display.contains(contains[0])) {
			e.setCancelled(true);
			player.sendMessage(message[0]);
			return;
		}
		
		if (display.contains(contains[1])) {
			e.setCancelled(true);
			player.sendMessage(message[1]);
			return;
		}
		
		if (display.equals(kitselector[0])) {
			e.setCancelled(true);
			player.closeInventory();
			player.openInventory(this.Inventario(player, Page.PAGE_ONE));
		}
		
		if (display.equals(kitselector[1])) {
			e.setCancelled(true);
			player.closeInventory();
			//player.openInventory(new Kit2Seletor(player).Inventario());
		}
		
		if (display.equals(pagelisttwo[0])) {
			e.setCancelled(true);
			player.closeInventory();
			player.openInventory(this.Inventario(player, Page.PAGE_ONE));
		}
		
		if (display.equals(pagelistone[1])) {
			e.setCancelled(true);
			player.closeInventory();
			player.openInventory(this.Inventario(player, Page.PAGE_TWO));
		}
		
		
		List<KitClass> value = Main.kitlist;
		
		for (int i = 1; i < value.size();i++) {
			KitClass kit = value.get(i);
			if (display.equalsIgnoreCase("§3" + kit.getDisplayName())) {
				e.setCancelled(true);
				//new SelectorUtils().SelectPrimary(player, kit.getFullDisplayName().toLowerCase());
			}
		}
	}
	
	
	
}
