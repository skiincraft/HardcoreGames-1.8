package me.skincraft.hardcoregames.customevents;

import java.util.HashMap;

public class BooleanCommands {
	
	public static HashMap<CBoolean, Boolean> commands = new HashMap<>();
	
	public enum CBoolean {
		Build, Damage, Chat
	}
	
	public void setBoolean(CBoolean option, boolean is) {
		commands.put(option, is);
	}
	
	public boolean getBoolean(CBoolean option) {
		if (!commands.containsKey(option)) {
			commands.put(option, true);
		}
		return commands.get(option);
	}
	
}
