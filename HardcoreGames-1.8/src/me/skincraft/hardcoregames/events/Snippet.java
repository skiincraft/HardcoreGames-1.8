package me.skincraft.hardcoregames.events;

import org.bukkit.entity.Player;

import me.skincraft.hardcoregames.managers.StatusManagers;
import me.skincraft.hardcoregames.managers.StatusManagers.SOptions;
import me.skincraft.hardcoregames.mysql.SQLPlayers;
import me.skincraft.hardcoregames.mysql.SQLPlayers.SQLOptions;

public class Snippet {
	public void statusAdd(Player killer) {
		SQLPlayers sql = new SQLPlayers(killer);
		StatusManagers data = new StatusManagers(sql);
		
		sql.set(SQLOptions.Xp, sql.getInt(SQLOptions.Xp) +13);
		data.set(SOptions.Kills, data.getInt(SOptions.Kills) + 1);
		
	}
	
	public void statusRemove(Player death) {
		SQLPlayers sql = new SQLPlayers(death);
		StatusManagers data = new StatusManagers(sql);
		
		sql.set(SQLOptions.Xp, sql.getInt(SQLOptions.Xp) -3);
		data.set(SOptions.Kills, data.getInt(SOptions.Deaths) + 1);
	}
}

