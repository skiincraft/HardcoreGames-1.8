package me.skincraft.hardcoregames.utils;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.api.ItemConstruct;
import me.skincraft.hardcoregames.managers.KitManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager;

public class SpectatorUtils {

	public static void addPlayerSpectator(Player player) {
		
		player.getInventory().setHelmet(new ItemStack(Material.AIR));
		player.getInventory().setChestplate(new ItemStack(Material.AIR));
		player.getInventory().setLeggings(new ItemStack(Material.AIR));
		player.getInventory().setBoots(new ItemStack(Material.AIR));
		player.getInventory().clear();
		
		new PlayerHGManager(player).addSpectatorState();
		
		new ItemConstruct(Material.WOOD_PLATE, "§cJogador Anterior", 3, player.getInventory(), "", "§7Utilize este item para teleportar", "§7para players que estão vivos.")
		.build();
		new ItemConstruct(Material.SLIME_BALL, "§6Jogadores Restantes", 4, player.getInventory(), "", "§7Utilize este item para teleportar", "§7para players que estão vivos.")
		.build();
		new ItemConstruct(Material.STONE_PLATE, "§aProximo Jogador", 5, player.getInventory(), "", "§7Utilize este item para teleportar", "§7para players que estão vivos.")
		.build();
		
		player.setGameMode(GameMode.CREATIVE);
		player.setAllowFlight(true);
		player.updateInventory();
		player.sendMessage("§eVocê entrou no modo espectador!");

		KitManager kit = new KitManager(player);
		kit.removeKit1();
		kit.removeKit2();

	}

}
