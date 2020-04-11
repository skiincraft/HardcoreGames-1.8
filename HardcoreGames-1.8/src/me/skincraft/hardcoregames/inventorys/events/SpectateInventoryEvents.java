package me.skincraft.hardcoregames.inventorys.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.api.ItemConstruct;
import me.skincraft.hardcoregames.inventorys.SpectateInventory;
import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;

public class SpectateInventoryEvents implements Listener{
	
	Map<String, Integer> anteTP = new HashMap<>();
	Map<String, Integer> proxTP = new HashMap<>();
	
	@EventHandler
	public void itemHotbarInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack ItemInHand = player.getItemInHand();
		ItemStack ItemInteract = new ItemStack(Material.SLIME_BALL); //CHANGE THIS
		
		List<ItemStack> stacklist = new ArrayList<ItemStack>();
		stacklist.add(new ItemConstruct(Material.WOOD_PLATE, "§cJogador Anterior", 0).buildItemStack());
		stacklist.add(new ItemConstruct(Material.SLIME_BALL, "§6Jogadores Restantes", 0).buildItemStack());
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
				player.openInventory(new SpectateInventory(player).Inventario());
			}
			
			if (hand_DisplayName.equals(stacklist.get(2).getItemMeta().getDisplayName())) {
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
	
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void eventoClickInventario(InventoryClickEvent e) {
		Player player = (Player)e.getWhoClicked();
		String titleName = e.getInventory().getTitle();
		if (titleName.equalsIgnoreCase(new SpectateInventory(player).inventoryname)) {
			if (e.getCurrentItem() == null) {
				return;
			}
			if (e.getCurrentItem().getTypeId() == 0) {
				return;
			}
			e.setCancelled(true);
			
			String display = e.getCurrentItem().getItemMeta().getDisplayName();
			List<String> specList = PlayerHGManager.getList(PlayerState.SPECTATOR);
			for (int i = 0; i < specList.size();i++) {
				Player specplayer = Bukkit.getPlayer(specList.get(i));
				if (display.equalsIgnoreCase("§a" + specList.get(i))) {
					player.teleport(specplayer.getLocation());
					player.closeInventory();
					player.playSound(player.getLocation(), Sound.ANVIL_USE, 1.0F, 1.0F);
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void spectatorChatMode(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		if (!PlayerHGManager.getList(PlayerState.SPECTATOR).contains(p.getName())) {
			return;
		}
		
		event.setCancelled(true);

		for (Player allspec : Bukkit.getOnlinePlayers()) {
			if (PlayerHGManager.getList(PlayerState.SPECTATOR).contains(allspec.getName())) {
				allspec.sendMessage("§7§lEspectador§7 " + p.getName() + " §3» §f" + event.getMessage());
				return;
			}
		}
		return;
	}
}
