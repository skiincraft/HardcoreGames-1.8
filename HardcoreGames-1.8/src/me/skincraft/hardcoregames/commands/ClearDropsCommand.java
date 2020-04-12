package me.skincraft.hardcoregames.commands;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import me.skincraft.hardcoregames.api.LystMCCommands;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;

public class ClearDropsCommand extends LystMCCommands {
	
	public ClearDropsCommand() {
		super("Cleardrops", "Comando para limpar os items do chão.", Arrays.asList("cd","dropclear"));
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!isPlayer(sender)) {
			return true;
		}
		
		Player player = (Player) sender;
		if (!hasPermission(player, Cargos.MOD, "mod.cleardrops")) {
			sendPermissionMessage(player);
			return true;
		}
		
		setPrefix(ChatColor.AQUA + "§lCLEARDROPS");
		if (args.length >= 0) {
			for (Item itemsDropados : Bukkit.getWorlds().get(0).getEntitiesByClass(Item.class)) {
				itemsDropados.remove();
			}
			broadcast(getPrefix() + "Items no chão foram limpos.");
		}
		return false;
	}

}
