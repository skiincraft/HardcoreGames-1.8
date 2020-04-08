package me.skincraft.hardcoregames.managers;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.mysql.SQLPlayers;
import me.skincraft.hardcoregames.mysql.SQLPlayers.SQLOptions;

public class StatusManagers {

	private SQLPlayers playerl;
	private static FileConfiguration player_Status;
	private static File playerStatus;
	private boolean file;
	
	private String databaseName = "`statushg`";
	
	public static FileConfiguration statushg() {
		return player_Status;
	}
	
	public StatusManagers(SQLPlayers player) {
		this.playerl = player;
		if (Main.getMain().DBSQL == false) {
			file = true;	
		} else {
			file = false;
		}
	}
	
	public enum SOptions {
		Nickname, UUID, Kills, Deaths, Wins;
	}
	
	private String nickname = SOptions.Nickname.name();
	private String uuid = SOptions.UUID.name();
	private String kills = SOptions.Kills.name();
	private String deaths = SOptions.Deaths.name();
	private String wins = SOptions.Wins.name();
	
	public boolean veriifcarExiste() {
		if (file) {
			if (statushg().get("StatusHG." + playerl.get(SQLOptions.UUID) + "." + nickname) == null) {
				return false;
			}
			return true;
		}
		
		try {
			ResultSet resultSet = Main.getMain().getSQL().getConnection().createStatement()
					.executeQuery("SELECT * FROM "+ databaseName + " WHERE `uuid` = '" + this.playerl.get(SQLOptions.UUID) + "';");
			
			if (resultSet.next()) {
				return resultSet.getString(uuid) != null;
			}
			return false;
		} catch (Exception e) {
			Main.getMain().loggerError(e, "");
			return true;
		}
	}
	
	public void criar() {
		if (file) {
			if (veriifcarExiste()) {
				return;
			}
			statushg().set("StatusHG." + playerl.get(SQLOptions.UUID) + "." + nickname, playerl.get(SQLOptions.Nickname));
			statushg().set("StatusHG." + playerl.get(SQLOptions.UUID) + "." + uuid, playerl.get(SQLOptions.UUID));
			statushg().set("StatusHG." + playerl.get(SQLOptions.UUID) + "." + kills, 0);
			statushg().set("StatusHG." + playerl.get(SQLOptions.UUID) + "." + deaths, 0);
			statushg().set("StatusHG." + playerl.get(SQLOptions.UUID) + "." + wins, 0);
			save();
			return;
		}
		
		if (veriifcarExiste()) {
			return;
		}
		
		try {
			Main.getMain().getSQL().getConnection()
			.createStatement()
			.execute("INSERT INTO " + databaseName + "(`uuid`, `nickname`, `kills`, `deaths`, `wins`) VALUES"
					+ "('"+ playerl.get(SQLOptions.UUID) + "', "
					+ "'" + playerl.get(SQLOptions.Nickname) + "', "
					+ "'" + 0 + "', "
					+ "'" + 0 + "', "
					+ "'" + 0 + "');");
			return;
		} catch (SQLException e) {
			Main.getMain().loggerError(e, "");
			return;
		}
	}
	
	public void deletar() {
		if (file) {
		//	
		}
		try {
			Main.getMain().getSQL().getConnection()
			.createStatement()
			.execute("DELETE FROM " + databaseName + "WHERE `uuid` = '" +
			playerl.get(SQLOptions.UUID) + "';");
		} catch (SQLException e) {
			Main.getMain().loggerError(e, "");
		}
		
	}
	
	public String get(SOptions coluna) {
		if (file) {
			if (!veriifcarExiste()) {
				criar();
			}
			
			return statushg().getString("StatusHG." + playerl.get(SQLOptions.UUID) + "." + coluna.name());
		}
		
		if (!veriifcarExiste()) {
			criar();
		}
		
		try {
			ResultSet resultSet = Main.getMain().getSQL().getConnection().createStatement()
			.executeQuery("SELECT * FROM " + databaseName + " WHERE `uuid` = '" +
			playerl.get(SQLOptions.UUID) + "';");
			
			if (resultSet.next()) {
				return resultSet.getString(coluna.name());
			}
			return null;
		} catch (Exception e) {
			Main.getMain().loggerError(e, "");
			return null;
		}
	}
	
	public int getInt(SOptions coluna) {
		if (file) {
			if (!veriifcarExiste()) {
				criar();
			}
			
			return statushg().getInt("StatusHG." + playerl.get(SQLOptions.UUID) + "." + coluna.name());
		}
		
		if (!veriifcarExiste()) {
			criar();
		}
		
		try {
			ResultSet resultSet = Main.getMain().getSQL().getConnection().createStatement()
			.executeQuery("SELECT * FROM " + databaseName + " WHERE `uuid` = '" +
			playerl.get(SQLOptions.UUID) + "';");
			
			if (resultSet.next()) {
				return resultSet.getInt(coluna.name());
			}
			return 0;
		} catch (Exception e) {
			Main.getMain().loggerError(e, "");
			return 0;
		}
	}
	
	public void set(SOptions coluna, String valor) {
		if (file) {
			if (veriifcarExiste()) {
				criar();
			}
			statushg().set("StatusHG." + playerl.get(SQLOptions.UUID) + "." + coluna.name(), valor);
			save();
			return;
		}
		
		if (!veriifcarExiste()) {
			criar();
		}
		
		try {
			Main.getMain().getSQL().getConnection()
			.createStatement().execute("UPDATE " + databaseName + " SET `" +
			
			coluna.name().toLowerCase() + "` = '" + valor + "' WHERE `uuid` = '" + 	get(SOptions.UUID) + "';");
			return;
		} catch (SQLException e) {
			Main.getMain().loggerError(e, "");
			return;
		}
	}
	
	public void set(SOptions coluna, boolean valor) {
		if (file) {
			if (veriifcarExiste()) {
				criar();
			}
			statushg().set("StatusHG." + playerl.get(SQLOptions.UUID) + "." + coluna.name(), valor);
			save();
			return;
		}
		
		if (!veriifcarExiste()) {
			criar();
		}
		
		try {
			Main.getMain().getSQL().getConnection()
			.createStatement().execute("UPDATE " + databaseName + " SET `" +
			
			coluna.name().toLowerCase() + "` = '" + valor + "' WHERE `uuid` = '" + 	get(SOptions.UUID) + "';");
			return;
		} catch (SQLException e) {
			Main.getMain().loggerError(e, "");
			return;
		}
	}
	
	public void set(SOptions coluna, int valor) {
		if (file) {
			if (veriifcarExiste()) {
				criar();
			}
			statushg().set("StatusHG." + playerl.get(SQLOptions.UUID) + "." + coluna.name(), valor);
			save();
			return;
		}
		
		if (!veriifcarExiste()) {
			criar();
		}
		
		try {
			Main.getMain().getSQL().getConnection()
			.createStatement().execute("UPDATE " + databaseName + " SET `" +
			
			coluna.name().toLowerCase() + "` = '" + valor + "' WHERE `uuid` = '" + 	get(SOptions.UUID) + "';");
			return;
		} catch (SQLException e) {
			Main.getMain().loggerError(e, "");
			return;
		}
	}
	
	public void setStatus (int kills, int deaths, int wins) {
		if (!veriifcarExiste()) {
			criar();
		}
		
		set(SOptions.Nickname, get(SOptions.Nickname));
		set(SOptions.UUID, get(SOptions.UUID));
		set(SOptions.Kills, kills);
		set(SOptions.Deaths, deaths);
		set(SOptions.Wins, wins);
	}
		
	public void save() {
		try {
			player_Status.save(playerStatus);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setup(Plugin plugin) {

		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		playerStatus = new File(plugin.getDataFolder() + "/files", "playerstatus.yml");
		if (playerStatus.exists()) {
			try {
				playerStatus.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		player_Status = YamlConfiguration.loadConfiguration(playerStatus);
	}

}
