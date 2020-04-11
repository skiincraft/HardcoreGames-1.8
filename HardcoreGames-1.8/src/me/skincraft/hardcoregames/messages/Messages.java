package me.skincraft.hardcoregames.messages;

public enum Messages {
	
	NO_COMMAND_PERMISSIONS("§cVocê não possui permissão para executar este comando");
	
	String message;
	Messages(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
