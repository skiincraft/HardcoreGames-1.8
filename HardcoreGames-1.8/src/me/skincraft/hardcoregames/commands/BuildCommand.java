package me.skincraft.hardcoregames.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.skincraft.hardcoregames.api.LystMCCommands;
import me.skincraft.hardcoregames.customevents.BooleanCommands;
import me.skincraft.hardcoregames.customevents.BooleanCommands.CBoolean;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;

public class BuildCommand extends LystMCCommands {

	public BuildCommand() {
		super("build", "Comando de construção");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!isPlayer(sender)) {
			return true;
		}
		
		Player player = (Player) sender;
		if (!hasPermission(player, Cargos.MOD, "mod.build")) {
			sendPermissionMessage(player);
			return true;
		}
		setPrefix(ChatColor.AQUA + "§lBUILD");
		if (args.length == 0) {
			if (!new BooleanCommands().getBoolean(CBoolean.Build)) {
				new BooleanCommands().setBoolean(CBoolean.Build, true);
				broadcast(getPrefix() + "Construções foram habilitadas neste servidor.");
				
			} else {
				new BooleanCommands().setBoolean(CBoolean.Build, false);
				broadcast(getPrefix() + "Construções foram desabilitadas neste servidor.");
			}
			return true;
		}
		
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("on")) {
				new BooleanCommands().setBoolean(CBoolean.Build, true);
				broadcast(getPrefix() + "Construções foram habilitadas neste servidor.");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("off")) {
				new BooleanCommands().setBoolean(CBoolean.Build, false);
				broadcast(getPrefix() + "Construções foram desabilitadas neste servidor.");
				return true;
			} else {
				sendUsage(player, "build <on/off>");
			}
		}
		
		return false;
	}

}
