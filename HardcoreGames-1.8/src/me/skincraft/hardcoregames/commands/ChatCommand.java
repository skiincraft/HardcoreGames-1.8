package me.skincraft.hardcoregames.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.skincraft.hardcoregames.api.LystMCCommands;
import me.skincraft.hardcoregames.customevents.BooleanCommands;
import me.skincraft.hardcoregames.customevents.BooleanCommands.CBoolean;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;

public class ChatCommand extends LystMCCommands {

	public ChatCommand() {
		super("chat", "Comando de manipulação do chat");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!isPlayer(sender)) {
			return true;
		}
		
		Player player = (Player) sender;
		if (!hasPermission(player, Cargos.TRIAL, "trial.chat")) {
			sendPermissionMessage(player);
			return true;
		}
		
		setPrefix(ChatColor.AQUA + "§lCHAT");
		if (args.length == 0) {
			if (!new BooleanCommands().getBoolean(CBoolean.Chat)) {
				new BooleanCommands().setBoolean(CBoolean.Chat, true);
				broadcast(getPrefix() + "Chat foi habilitado neste servidor.");
				
			} else {
				new BooleanCommands().setBoolean(CBoolean.Chat, false);
				broadcast(getPrefix() + "Chat foi desabilitado neste servidor.");
			}
			return true;
		}
		
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("on")) {
				new BooleanCommands().setBoolean(CBoolean.Chat, true);
				broadcast(getPrefix() + "Chat foi habilitado neste servidor.");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("off")) {
				new BooleanCommands().setBoolean(CBoolean.Chat, false);
				broadcast(getPrefix() + "Chat foi desabilitado neste servidor.");
				return true;
			} else {
				sendUsage(player, "chat <on/off>");
			}
		}
		
		return false;
	}

}
