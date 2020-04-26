package me.skincraft.inventorymanager.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.api.ItemConstruct;
import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;
import me.skincraft.inventorymanager.InventarioAPI;
import me.skincraft.inventorymanager.InventorySize;
import me.skincraft.other.kit.KitManager;

public class SpectateInventory extends InventarioAPI {
	
	public SpectateInventory() {
		super(new ItemStack(Material.SLIME_BALL), "§6Jogadores Restantes" ,"§aMenu de Espectador", 
				Arrays.asList("§7Utilize este item para teleportar", "§7para players que estão vivos."), 
				new ItemStack[] {contentItem(Material.WOOD_PLATE, "§cJogador Anterior", null),
						contentItem(Material.WOOD_PLATE, "§aProximo Jogador", null)});
	}
	
	

	@Override
	public Inventory InvMake(Player player) {
		Inventory inv = createInventory(player, InventorySize.NORMAL);
		List<String> lista = PlayerHGManager.getList(PlayerState.ALIVE);
		for (String names: lista) {
			Player playerspec = Bukkit.getPlayer(names);
			
			KitManager kitm = new KitManager(playerspec);
			String kits = "§7" + kitm.getPlayerKit1().getDisplayName() + "/" + kitm.getPlayerKit1().getDisplayName();
			String[] str = new String[] 
					       {"§b» " + kits,
							"§eKills: §2" + new PlayerHGManager(playerspec).getPlayerStreak()};
			
			inv.addItem(new ItemStack[] {createSkull(names, str)});
		}
		
		return inv;
	}
	
	/*
	 * EVENTOS
	 */
	
	@EventHandler
	public void itemHotbarInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (itemHotbarChekers(e) == false) return;
		
		
		String hand = player.getItemInHand().getItemMeta().getDisplayName();
		String interact = getHotbarItemName();
		
		if (hand.equals(interact)) {
			player.openInventory(InvMake(e.getPlayer()));
		}
	}
	
	public static Map<String, Integer> anteTP = new HashMap<>();
	public static Map<String, Integer> proxTP = new HashMap<>();
	
	@EventHandler
	public void itemHotbarInteract2(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack ItemInHand = player.getItemInHand();
		ItemStack ItemInteract = new ItemStack(Material.EGG); //CHANGE THIS
		
		List<ItemStack> stacklist = new ArrayList<ItemStack>();
		stacklist.add(new ItemConstruct(Material.WOOD_PLATE, "§cJogador Anterior", 0).buildItemStack());
		stacklist.add(new ItemConstruct(Material.STONE_PLATE, "§aProximo Jogador", 0).buildItemStack());
		
		for (int i = 0;i < stacklist.size();i++) {
			if (ItemInHand == stacklist.get(i)) {
				ItemInteract = stacklist.get(i);
			}
		}
		
		if (ItemInHand.getType() != ItemInteract.getType())return;
		
		if (e.getAction() == Action.RIGHT_CLICK_AIR ||
				e.getAction() == Action.RIGHT_CLICK_BLOCK) {

			String hand_DisplayName = ItemInHand.getItemMeta().getDisplayName();
			if (!ItemInHand.getItemMeta().hasDisplayName())return;
			
			List<String> speclist = PlayerHGManager.getList(PlayerState.ALIVE);
			
			if (hand_DisplayName.equals(stacklist.get(0).getItemMeta().getDisplayName())) {
				if (!anteTP.containsKey(player.getName())) {
					if (PlayerHGManager.getSize(PlayerState.ALIVE) == 0) {
						return;
					}
					anteTP.put(player.getName(), 0);
				}
				int num = anteTP.get(player.getName());
				player.teleport(Bukkit.getPlayer(speclist.get(num)));
			}
			
			if (hand_DisplayName.equals(stacklist.get(1).getItemMeta().getDisplayName())) {
				if (!anteTP.containsKey(player.getName())) {
					if (PlayerHGManager.getSize(PlayerState.ALIVE) == 0) {
						return;
					}
					anteTP.put(player.getName(), 0);
				}
				int num = anteTP.get(player.getName()) + 1;
				if (PlayerHGManager.getSize(PlayerState.ALIVE) < num) {
					num = 0;
				}
				
				player.teleport(Bukkit.getPlayer(speclist.get(num)));
			}
		}
	}

	
	
	 
	@EventHandler
	public void clickInventario(InventoryClickEvent e) {
		Player player = (Player)e.getWhoClicked();
		if (clickInventoryCheckers(e) == false) return;
		
		List<String> spectators = PlayerHGManager.getList(PlayerState.SPECTATOR);
		String[] nameitem = new String[spectators.size()];
		for (int i = 0; i < spectators.size(); i++) {
			nameitem[i] = spectators.get(i); 
		}
		
		String display = e.getCurrentItem().getItemMeta().getDisplayName();
		for (int i = 0; i < nameitem.length; i++) {
			if (display.equalsIgnoreCase(nameitem[i])) {
				player.closeInventory();
			}
		}
	}

	
}
