package me.skincraft.hardcoregames.timers;

public enum State {
	
	Iniciando, Invencibilidade, Andamento, Finalizando;
	
	public int getTimer() {
		return new TimersManager().getTimer(this);
	}

}
