package me.skincraft.hardcoregames.messages;

public enum Messages {
	
	NO_COMMAND_PERMISSIONS("�cVoc� n�o possui permiss�o para executar este comando");
	
	String message;
	Messages(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
