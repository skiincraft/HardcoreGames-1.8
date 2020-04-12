package me.skincraft.hardcoregames.api;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.habilidades.Nenhum;
import me.skincraft.hardcoregames.kit.KitManager;

public class UtilsAPI {
	
	/*
	 * Isso não é exatamente uma API 
	 * 
	 * Essa classe foi criada para evitar Variaveis
	 * desnecessarias (Que deixam os codigos feios)
	 * 
	 * Esse construtor ira servir para chamar 'estaticos publicos'
	 * de forma mais facil e que não polua visualmente os codigos.
	 */
	
	@SuppressWarnings("unused")
	private Main main;
	
	public static HashMap<String, ItemStack[]> inventorysave = new HashMap<>();
	public static HashMap<String, ItemStack[]> armadurasave = new HashMap<>();
	
	public UtilsAPI(Main main) {
		this.main = main;
	}
	
	public void saveInventory(Player player) {
		inventorysave.put(player.getName(), player.getInventory().getContents());
	}
	
	public void saveArmor(Player player) {
		inventorysave.put(player.getName(), player.getInventory().getContents());
	}
	
	public ItemStack[] getInventory(Player player) {
		return inventorysave.get(player.getName());
	}
	
	public ItemStack[] getArmor(Player player) {
		return armadurasave.get(player.getName());
	}
	
	public void SendTaskLaterMessage(Player player, int secoundsdelayed, String message) {
		Bukkit.getScheduler().runTaskLater(main, new Runnable() {
			
			@Override
			public void run() {
				player.sendMessage(message);
			}
		}, 20*secoundsdelayed);
	}
	public void SendTaskLaterMessage(Player player, int secoundsdelayed, String[] message) {
		Bukkit.getScheduler().runTaskLater(main, new Runnable() {
			
			@Override
			public void run() {
				player.sendMessage(message);
			}
		}, 20*secoundsdelayed);
	}
	
	public void sendPlayerBungee(Player paramPlayer, String paramString) {
		ByteArrayDataOutput localByteArrayDataOutput;
		(localByteArrayDataOutput = ByteStreams.newDataOutput()).writeUTF("Connect");
		localByteArrayDataOutput.writeUTF(paramString);
		paramPlayer.sendPluginMessage(Main.getPlugin(), "BungeeCord", localByteArrayDataOutput.toByteArray());
	}
	
	public static int getAmount(Player p, Material m) {
		int amount = 0;
		ItemStack[] arrayOfItemStack;
		int j = (arrayOfItemStack = p.getInventory().getContents()).length;
		for (int i = 0; i < j; i++) {
			ItemStack item = arrayOfItemStack[i];
			if ((item != null) && (item.getType() == m) && (item.getAmount() > 0)) {
				amount += item.getAmount();
			}
		}
		return amount;
	}
	
	public String getHabilidadePrimaria(Player p) {
		return new KitManager(p).getPlayerKit1().getDisplayName();
	}

	public String getHabilidades(Player p) {
		KitManager kit = new KitManager(p);
		if (kit.getPlayerKit2().equals(new Nenhum())) {
			return "§c" + kit.getPlayerKit1().getDisplayName();
		}
		return "§b" + kit.getPlayerKit1().getDisplayName() + "§7/§b" + kit.getPlayerKit2().getDisplayName();
	}

	public String getHabilidadeSecundaria(Player p) {
		return new KitManager(p).getPlayerKit2().getDisplayName();
	}

	public Player getPlayer(String args) {
		return Bukkit.getPlayer(args);
	}
	
	public void enviarSpawn(Player player) {
			
			double x = Main.getPlugin().getConfig().getDouble("CoordsSpawn.X");
			double y = Main.getPlugin().getConfig().getDouble("CoordsSpawn.Y");
			double z = Main.getPlugin().getConfig().getDouble("CoordsSpawn.Z");

			float Pitch = (float) Main.getPlugin().getConfig().getDouble("CoordsSpawn.Pitch");
			float Yaw = (float) Main.getPlugin().getConfig().getDouble("CoordsSpawn.Yaw");

			Location loc = new Location(player.getWorld(), x, y, z, Yaw, Pitch);
			player.teleport(loc);
	}
	
	public void Sound(Player p, Sound som) {
		p.playSound(p.getLocation(), som, 10, 0);
	}
}
