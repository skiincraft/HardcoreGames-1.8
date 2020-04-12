package me.skincraft.hardcoregames.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.skincraft.hardcoregames.managers.GroupsManager;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;
import me.skincraft.hardcoregames.mysql.SQLPlayers;
import net.md_5.bungee.api.ChatColor;

public abstract class LystMCCommands extends Command {

	private String commandPrefix = "";
	
	public LystMCCommands(String name) {
		super(name);
	}

	public LystMCCommands(String name, String description) {
		super(name, description, "", new ArrayList<>());
	}
	
	public LystMCCommands(String name, String description, List<String> aliases) {
		super(name, description, "", aliases);
	}
	
	public void broadcast(String message) {
		Bukkit.broadcastMessage(message);
	}

	public boolean hasPermission(Player sender, Cargos group, String permissions) {
		return new GroupsManager(new SQLPlayers(sender)).hasPermission(group, permissions);
	}

	public void sendPermissionMessage(Player sender) {
		sender.sendMessage(ChatColor.RED + "Você não possui permissão para executar este comando");
	}
	
	public void sendUsage(Player sender, String usage) {
		sender.sendMessage(ChatColor.RED + "Utilize: /" + usage);
	}

	public void sendUsage(CommandSender sender, String usage) {
		sender.sendMessage(ChatColor.RED + "Utilize: /" + usage);
	}
	
	public void sendConsoleAdvert(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "Somente player podem executar este comando.");
	}

	public boolean isPlayer(CommandSender sender) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Somente player podem executar este comando.");
			return false;
		}
		return sender instanceof Player;
	}

	public abstract boolean execute(CommandSender sender, String label, String args[]);

	public void staffBroadcast(String warning) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (hasPermission(player, Cargos.ADMIN, Cargos.ADMIN.name() + ".warning")) {
				player.sendMessage("§7§o[" + warning + "]");
			}
		}
	}

	public void staffBroadcast(String warning, Cargos group) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (hasPermission(player, group, group.name() + ".warning")) {
				player.sendMessage("§7§o[" + warning + "]");
			}
		}
	}

	public String getPrefix() {
		return commandPrefix;
	}

	public void setPrefix(String commandPrefix) {
		this.commandPrefix = commandPrefix + " §f";
	}

}
