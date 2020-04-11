package me.skincraft.hardcoregames.timers;

import java.util.HashMap;
import java.util.Map;

public class TimersManager {
	
	public TimersManager() {
	}
	
	public static class Timers {
		public static Map<State, Integer> matchTimer = new HashMap<>();
		public static State matchState;
	}
	
	public boolean isIniciando() {
		if (Timers.matchState == State.Iniciando) {
			return true;
		}
		return false;
	}
	public void changeNextState() {
		if (Timers.matchState == State.Iniciando) {
			Iniciando.cancelRunnable();
			new Invencibilidade();
			return;
		}
		if (Timers.matchState == State.Invencibilidade) {
			Invencibilidade.cancelRunnable();
			new Andamento(2*60);
			return;
		}
		if (Timers.matchState == State.Andamento) {
			Andamento.cancelRunnable();
			return;
		}
		return;
	}
	
	public boolean isInvencibilidade() {
		if (Timers.matchState == State.Invencibilidade) {
			return true;
		}
		return false;
	}
	
	public boolean isAndamento() {
		if (Timers.matchState == State.Andamento) {
			return true;
		}
		return false;
	}
	
	public boolean isFinalizando() {
		if (Timers.matchState == State.Finalizando) {
			return true;
		}
		return false;
	}
	
	public State getState() {
		return Timers.matchState;
	}
	
	public int getTimer() {
		return Timers.matchTimer.get(Timers.matchState);
	}
	
	public int getTimer(State state) {
		return Timers.matchTimer.get(state);
	}
	
	public void setState(State state) {
		Timers.matchState = state;
	}
	
	public void setTimer(State state, int tempo) {
		Timers.matchTimer.put(state, tempo);
	}

}
