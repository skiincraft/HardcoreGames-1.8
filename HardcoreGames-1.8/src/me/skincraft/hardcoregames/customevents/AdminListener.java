package me.skincraft.hardcoregames.customevents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.managers.GroupsManager;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;
import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;
import me.skincraft.hardcoregames.mysql.SQLPlayers;

public class AdminListener implements Listener {

	public static ArrayList<Player> vanished = new ArrayList<>();

	public static boolean isVanished(Player p) {
		return vanished.contains(p);
	}

	public static void makeVanished(Player p) {
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			GroupsManager group = new GroupsManager(new SQLPlayers(player));
			if (!group.hasPermission(Cargos.TRIAL, "trial.admin")) {
				player.hidePlayer(p);
			} else {
				player.showPlayer(p);
			}
		}
		
		if (!isVanished(p)) {
			vanished.add(p);
		}
	}

	public static void makeVisible(Player p) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.showPlayer(p);
		}
		
		if (isVanished(p) == true) {
			vanished.remove(p);
		}
	}

	public static void updateVanished() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (isVanished(player)) {
				makeVanished(player);
			} else {
				makeVisible(player);
			}
		}
	}

	@EventHandler
	public void admimPickup(PlayerPickupItemEvent e) {
		List<String> a = PlayerHGManager.getList(PlayerState.ADMINMODE);
		if (a.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void adminItemInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack ItemInHand = player.getItemInHand();
		ItemStack ItemInteract = new ItemStack(Material.APPLE); //CHANGE THIS
		
		Material[] material = new Material[] 
				{Material.WOOD_DOOR, Material.MAGMA_CREAM};

		for (int i = 0; i < material.length; i++) {
			if (ItemInHand.getType() == Arrays.asList(material).get(i)) {
				ItemInteract = new ItemStack(Arrays.asList(material).get(i));	
			}
		}
		
		if (ItemInHand.getType() != ItemInteract.getType())return;
		
		if ((e.getAction() == Action.RIGHT_CLICK_AIR)) {
			if (ItemInteract.getType() == Material.WOOD_DOOR) {
				player.performCommand("admin");
				return;
			}
			
			if (ItemInteract.getType() == Material.MAGMA_CREAM) {
				player.performCommand("admin");
				Bukkit.getScheduler().runTaskLater(Main.getMain(), new Runnable() {
					
					@Override
					public void run() {
						player.performCommand("admin");
					}
				}, 1*20L);
				return;
			}
			
			
		}
	}

	@EventHandler
	public void playerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		for (Player player : Bukkit.getOnlinePlayers()) {
			List<String> adminlist = PlayerHGManager.getList(PlayerState.ADMINMODE);
			if (adminlist.contains(player.getName())) {
				p.hidePlayer(player);
			}
		}
	}

	@EventHandler
	public void openAdminInventory(PlayerInteractEntityEvent e) {
		if (!(e.getRightClicked() instanceof Player)) {
			return;
		}

		Player player = e.getPlayer();
		Player clicked = (Player) e.getRightClicked();
		List<String> lista = PlayerHGManager.getList(PlayerState.ADMINMODE);
		
		if (lista.contains(player.getName())) {
			ItemStack item = player.getItemInHand();

			if (!(e.getRightClicked() instanceof Player)) {
				return;
			}

			if (item.getType() == Material.AIR) {
				player.openInventory(clicked.getInventory());
				e.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler
	public void Onquit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		Player p = e.getPlayer();
		
		List<String> lista = PlayerHGManager.getList(PlayerState.ADMINMODE);
		if (lista.contains(p.getName())) {
			new PlayerHGManager(p).removeAdmin();
			makeVisible(p);
		}
	}
}
