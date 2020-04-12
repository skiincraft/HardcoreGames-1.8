package me.skincraft.hardcoregames.commands;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.skincraft.hardcoregames.api.LystMCCommands;
import me.skincraft.hardcoregames.customevents.BooleanCommands;
import me.skincraft.hardcoregames.customevents.BooleanCommands.CBoolean;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;

public class DanoCommand extends LystMCCommands {

	public DanoCommand() {
		super("dano", "Comando manipulação de dano do servidor.", Arrays.asList("damage"));
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!isPlayer(sender)) {
			return true;
		}
		
		Player player = (Player) sender;
		if (!hasPermission(player, Cargos.TRIAL, "mod.dano")) {
			sendPermissionMessage(player);
			return true;
		}
		
		setPrefix(ChatColor.AQUA + "§lDAMAGE");
		if (args.length == 0) {
			if (!new BooleanCommands().getBoolean(CBoolean.Damage)) {
				new BooleanCommands().setBoolean(CBoolean.Damage, true);
				broadcast(getPrefix() + "Damage foi habilitado neste servidor.");
				
			} else {
				new BooleanCommands().setBoolean(CBoolean.Damage, false);
				broadcast(getPrefix() + "Damage foi desabilitado neste servidor.");
			}
			return true;
		}
		
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("on")) {
				new BooleanCommands().setBoolean(CBoolean.Damage, true);
				broadcast(getPrefix() + "Damage foi habilitado neste servidor.");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("off")) {
				new BooleanCommands().setBoolean(CBoolean.Damage, false);
				broadcast(getPrefix() + "Damage foi desabilitado neste servidor.");
				return true;
			} else {
				sendUsage(player, "dano <on/off>");
			}
		}
		
		return false;
	}

}
