package me.skincraft.hardcoregames.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerHGManager {

	private Player player;
	
	public PlayerHGManager(Player player) {
		this.player = player;
	}
	
	public static class HGManagerUtils {
		public static List<String> alive = new ArrayList<String>();
		public static List<String> hold = new ArrayList<String>();
		public static List<String> dead = new ArrayList<String>();
		public static List<String> spectator = new ArrayList<String>();
		public static List<String> notverified = new ArrayList<String>();
		public static ArrayList<String> admin = new ArrayList<>();
		public static Map<String, Integer> playerStreak = new HashMap<>();
	}

	public enum PlayerState {
		ADMINMODE("admin"), ALIVE("vivo"), HOLD("espera"), DEAD("morto"), SPECTATOR("espectador"),
		NotVerified("notVerified");
		
		String statusname;
		PlayerState(String status) {
			statusname = status;
		}
	}
	
	public int getPlayerStreak() {
		if (!HGManagerUtils.playerStreak.containsKey(player.getName())) {
			setPlayerStreak(0);
		}
		return HGManagerUtils.playerStreak.get(player.getName());
	}
	
	public void setPlayerStreak(int streak) {
		HGManagerUtils.playerStreak.put(player.getName(), streak);
		return;
	}
	
	public PlayerState getPlayerState() {
		if (HGManagerUtils.alive.contains(player.getName())) {
			return PlayerState.ALIVE;
		}
		if (HGManagerUtils.hold.contains(player.getName())) {
			return PlayerState.HOLD;
		}
		if (HGManagerUtils.dead.contains(player.getName())) {
			return PlayerState.DEAD;
		}
		if (HGManagerUtils.spectator.contains(player.getName())) {
			return PlayerState.SPECTATOR;
		}
		if (HGManagerUtils.admin.contains(player.getName())) {
			return PlayerState.ADMINMODE;
		}
		if (HGManagerUtils.notverified.contains(player.getName())) {
			return PlayerState.NotVerified;
		}
		return PlayerState.NotVerified;
	}
	
	public void setPlayerState(PlayerState state) {
		removeState();
		if (state.equals(PlayerState.ALIVE)) {
			HGManagerUtils.alive.add(player.getName());
			return;
		}
		if (state.equals(PlayerState.DEAD)) {
			HGManagerUtils.dead.add(player.getName());
			return;
		}
		if (state.equals(PlayerState.HOLD)) {
			HGManagerUtils.hold.add(player.getName());
			return;
		}
		if (state.equals(PlayerState.SPECTATOR)) {
			HGManagerUtils.spectator.add(player.getName());
			return;
		}
		if (state.equals(PlayerState.ADMINMODE)) {
			HGManagerUtils.admin.add(player.getName());
			return;
		}
		if (state.equals(PlayerState.NotVerified)) {
			HGManagerUtils.spectator.add(player.getName());
			return;
		}
	}
	
	public void removeState() {
		if (getPlayerState().equals(PlayerState.ALIVE)) {
			HGManagerUtils.alive.remove(player.getName());
		}
		if (getPlayerState().equals(PlayerState.DEAD)) {
			HGManagerUtils.dead.remove(player.getName());
		}
		if (getPlayerState().equals(PlayerState.HOLD)) {
			HGManagerUtils.hold.remove(player.getName());
		}
		if (getPlayerState().equals(PlayerState.SPECTATOR)) {
			HGManagerUtils.spectator.remove(player.getName());
		}
		if (getPlayerState().equals(PlayerState.ADMINMODE)) {
			HGManagerUtils.admin.remove(player.getName());
		}
		if (getPlayerState().equals(PlayerState.NotVerified)) {
			HGManagerUtils.spectator.remove(player.getName());
		}
	}
	
	public void addSpectatorState() {
		removeState();
		setPlayerState(PlayerState.SPECTATOR);
	}
	
	public void addAdminState() {
		removeState();
		setPlayerState(PlayerState.ADMINMODE);
	}
	
	public void addAliveState() {
		removeState();
		setPlayerState(PlayerState.ALIVE);
	}
	
	public void addDeadState() {
		removeState();
		setPlayerState(PlayerState.DEAD);
	}
	
	public void addHoldState() {
		removeState();
		setPlayerState(PlayerState.HOLD);
	}
	
	public void addNotVerifiedState() {
		removeState();
		setPlayerState(PlayerState.NotVerified);
	}
	
	public static int getSize(PlayerState state) {
		if (state.equals(PlayerState.ALIVE)) {
			return HGManagerUtils.alive.size();
		}
		if (state.equals(PlayerState.DEAD)) {
			return HGManagerUtils.dead.size();
		}
		if (state.equals(PlayerState.HOLD)) {
			return HGManagerUtils.hold.size();
		}
		if (state.equals(PlayerState.SPECTATOR)) {
			return HGManagerUtils.spectator.size();
		}
		if (state.equals(PlayerState.ADMINMODE)) {
			return HGManagerUtils.admin.size();
		}
		if (state.equals(PlayerState.NotVerified)) {
			return HGManagerUtils.notverified.size();
		}
		return 0;
	}
	
	public static int getPlayerSize() {
		int size = Bukkit.getOnlinePlayers().size() - (getSize(PlayerState.ADMINMODE) - getSize(PlayerState.SPECTATOR));
		return size;
	}
	
	public static List<String> getList(PlayerState state) {
		if (state.equals(PlayerState.ALIVE)) {
			return HGManagerUtils.alive;
		}
		if (state.equals(PlayerState.DEAD)) {
			return HGManagerUtils.dead;
		}
		if (state.equals(PlayerState.HOLD)) {
			return HGManagerUtils.hold;
		}
		if (state.equals(PlayerState.SPECTATOR)) {
			return HGManagerUtils.spectator;
		}
		if (state.equals(PlayerState.ADMINMODE)) {
			return HGManagerUtils.admin;
		}
		if (state.equals(PlayerState.NotVerified)) {
			return HGManagerUtils.notverified;
		}
		return null;
	}
	
	public static void setList(PlayerState state, List<String> list) {
		if (state.equals(PlayerState.ALIVE)) {
			HGManagerUtils.alive.clear();
			HGManagerUtils.alive.addAll(list);
			return;
		}
		if (state.equals(PlayerState.DEAD)) {
			HGManagerUtils.dead.clear();
			HGManagerUtils.dead.addAll(list);
			return;
		}
		if (state.equals(PlayerState.HOLD)) {
			HGManagerUtils.hold.clear();
			HGManagerUtils.hold.addAll(list);
			return;
		}
		if (state.equals(PlayerState.SPECTATOR)) {
			HGManagerUtils.spectator.clear();
			HGManagerUtils.spectator.addAll(list);
			return;
		}
		if (state.equals(PlayerState.ADMINMODE)) {
			HGManagerUtils.spectator.clear();
			HGManagerUtils.spectator.addAll(list);
			return;
		}
		if (state.equals(PlayerState.NotVerified)) {
			HGManagerUtils.notverified.clear();
			HGManagerUtils.notverified.addAll(list);
			return;
		}
	}
	
	public static void clearAllStates() {
		HGManagerUtils.alive.clear();
		HGManagerUtils.dead.clear();
		HGManagerUtils.hold.clear();
		HGManagerUtils.notverified.clear();
		HGManagerUtils.spectator.clear();
	}
}
