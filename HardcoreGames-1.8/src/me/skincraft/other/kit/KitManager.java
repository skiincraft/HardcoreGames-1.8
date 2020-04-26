package me.skincraft.other.kit;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.managers.GroupsManager;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;
import me.skincraft.hardcoregames.mysql.SQLPlayers;
import me.skincraft.other.habilidades.Nenhum;

public class KitManager {
	
	@SuppressWarnings("unused")
	private Player playerM;
	private String player;
	
	public KitManager(Player playerM) {
		this.playerM = playerM;
		this.player = playerM.getName();
	}
	
	public static class KitUtils {
		public static Map<String, KitClass> primaryKit = new HashMap<>();
		public static Map<String, KitClass> secoundaryKit = new HashMap<>();
	}
	
	public ItemStack getItemKit() {
		return getPlayerKit1().getKitItem();
	}
	
	public ItemStack getItemKit2() {
		return getPlayerKit2().getKitItem();		
	}
	
	public KitClass getPlayerKit1() {
		if (!KitUtils.primaryKit.containsKey(player)) {
			setKit1(new Nenhum());
		}
		return KitUtils.primaryKit.get(player);
	}
	
	public KitClass getPlayerKit2() {
		if (!KitUtils.secoundaryKit.containsKey(player)) {
			setKit2(new Nenhum());
		}
		return KitUtils.secoundaryKit.get(player);
	}
	
	public void setKit1(KitClass kit) {
		KitUtils.primaryKit.put(player, kit);
		return;
	}
	
	public void setKit2(KitClass kit) {
		KitUtils.secoundaryKit.put(player, kit);
		return;
	}
	
	public void removeKit1() {
		if (getPlayerKit1() == new Nenhum()) {
			return;
		}
		KitUtils.primaryKit.replace(player, new Nenhum());
	}
	
	public void removeKit2() {
		if (getPlayerKit2() == new Nenhum()) {
			return;
		}
		
		KitUtils.secoundaryKit.replace(player, new Nenhum());
	}
	
	public void clear() {
		KitUtils.primaryKit.replace(player, new Nenhum());
		KitUtils.secoundaryKit.replace(player, new Nenhum());
	}
	
	public boolean containsKit(KitClass kit) {
		if (hasSelectedKit1(kit)) {
			return true;
		}
		
		if (hasSelectedKit2(kit)) {
			return true;
		}
		return false;
	}
	
	public boolean hasKitPermission(KitClass kit) {
		if (Main.freeKits.contains(kit.getName())) {
			return true;
		}
		if (new GroupsManager(new SQLPlayers(playerM)).hasPermission(Cargos.VIP, kit.getPermissions())) {
			return true;
		}
		
		return false;
	}
	
	public boolean hasSelectedKit1(KitClass kit) {
		if (!KitUtils.primaryKit.containsKey(player)) {
			setKit1(new Nenhum());
		}
		if (KitUtils.primaryKit.get(player).equals(kit)) {
			return true;
		}
		return false;
	}
	
	public boolean hasSelectedKit2(KitClass kit) {
		if (!KitUtils.secoundaryKit.containsKey(player)) {
			setKit1(new Nenhum());
		}
		return false;
	}
	
}
