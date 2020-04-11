package me.skincraft.hardcoregames.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.api.ItemConstruct;
import me.skincraft.hardcoregames.api.UtilsAPI;
import me.skincraft.hardcoregames.bossbar.BossBarAPI;
import me.skincraft.hardcoregames.customevents.AdminListener;
import me.skincraft.hardcoregames.managers.GroupsManager;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;
import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;
import me.skincraft.hardcoregames.messages.Messages;
import me.skincraft.hardcoregames.mysql.SQLPlayers;
import me.skincraft.hardcoregames.playerdeathevent.PlayerRespawnManager;

public class AdminCommand extends Command {

	public AdminCommand(String name, String description, String usageMessage, List<String> aliases) {
		super(name, description, usageMessage, aliases);
	}
	
	public static Map<String, PlayerState> lastState = new HashMap<>();
	

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Estão no modo admin " + PlayerHGManager.getSize(PlayerState.ADMINMODE) + " players");
			return true;
		}
		Player player = (Player)sender; 
		
		if (!new GroupsManager(new SQLPlayers(player))
				.hasPermission(Cargos.TRIAL, "trial.admin")) {
			player.sendMessage(Messages.NO_COMMAND_PERMISSIONS.getMessage());
			return true;
		}
		
		List<String> a;
		a = PlayerHGManager.getList(PlayerState.ADMINMODE);
		if (!a.contains(player.getName())) {
			addAdmin(player);
			return true;
		}
		
		if (a.contains(player.getName())) {
			removeAdmin(player);
			return true;
		}
		return false;
	}
	
	public static void addAdmin(Player player) {
		
		UtilsAPI util = new UtilsAPI(Main.getMain());
		String[] message1 = new String[2];
		message1[0] = "§cVocê entrou no modo admin";
		message1[1] = "§eVocê esta invisivel para todos os jogadores";
		List<String> a;
		a = PlayerHGManager.getList(PlayerState.ADMINMODE);
		if (!a.contains(player.getName())) {
			if (Bukkit.getOnlinePlayers().size() != 0) {
				player.sendMessage(message1);
				BossBarAPI.setMessage(player, "§cVocê esta no modo admin!", 55, 3);
				util.saveArmor(player);
				util.saveInventory(player);
				player.getInventory().clear();
				
				lastState.put(player.getName(), new PlayerHGManager(player).getPlayerState());
				new PlayerHGManager(player).addAdminState();
				AdminListener.makeVanished(player);
				
				player.setGameMode(GameMode.CREATIVE);
				
				
				new ItemConstruct(Material.MAGMA_CREAM, null, 0)
				.setInventory(player.getInventory())
				.setItemName("§3Checar Rapido")
				.setLore("§7Com este item você pode sair", "§7e entrar no /admin rapidamente.").setSlot(1).build();
				
				new ItemConstruct(Material.WOOD_DOOR, null, 0)
				.setInventory(player.getInventory())
				.setItemName("§3Sair do Admin").setLore("§7Saia desse modo atual para o modo jogador", "")
				.setSlot(4).build();
				
				new ItemConstruct(Material.PACKED_ICE, null, 0)
				.setInventory(player.getInventory())
				.setItemName("§3Congelar jogador").setLore("§7Congele algum jogador", "")
				.setSlot(7).build();
				
			}
		}
	}
	
	public static void removeAdmin(Player player) {
		UtilsAPI util = new UtilsAPI(Main.getMain());
		List<String> a = PlayerHGManager.getList(PlayerState.ADMINMODE);
		if (a.contains(player.getName())) {
			String[] message2 = new String[2];
			message2[0] = "§cVoce saiu do modo admin.";
			message2[1] = "§eAgora voce está visivel para todos os players.";
			player.sendMessage(message2);
			BossBarAPI.removeBar(player);
			player.getInventory().clear();
			player.setGameMode(GameMode.SURVIVAL);
			
			player.getInventory().setArmorContents(util.getArmor(player));
			player.getInventory().setContents(util.getInventory(player));
			player.updateInventory();
			
			for (Player online : Bukkit.getOnlinePlayers()) {

				online.showPlayer(player);
				player.showPlayer(online);

				if (a.contains(online.getName())) {
					player.hidePlayer(online);
				} else if (PlayerHGManager.getList(PlayerState.SPECTATOR).contains(online.getName())) {
					player.hidePlayer(online);
				}
			}
			AdminListener.makeVisible(player);
			returnLastState(player, lastState.get(player.getName()));
		}
	}
	
	private static void returnLastState(Player player, PlayerState state) {
		PlayerHGManager pHG = new PlayerHGManager(player); 
		if (state == PlayerState.SPECTATOR) {
			new PlayerRespawnManager(player).addSpectatorMode();
			pHG.addSpectatorState();
			return;
		}
		if (state == PlayerState.ALIVE) {
			pHG.addAliveState();
			return;
		}
		
		if (state == PlayerState.DEAD) {
			pHG.addDeadState();
			return;
		}
		
		if (state == PlayerState.HOLD) {
			pHG.addHoldState();
			return;
		}		
		if (state == PlayerState.NotVerified) {
			pHG.addNotVerifiedState();
			return;
		}
	}
	
	public void hideAdmins(Player player, List<String> a) {
		for (Player coll : Bukkit.getOnlinePlayers()) {
			if (a.contains(coll.getName())) {
				coll.showPlayer(player);
				return;
			}
			coll.hidePlayer(player);
		}
	}
	

}
