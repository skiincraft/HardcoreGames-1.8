package me.skincraft.hardcoregames.mysql;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.skincraft.hardcoregames.Main;

public class SQLPlayers {

	private Player player;
	private static FileConfiguration players_file;
	private static File playerfile;
	private boolean file;
	
	private String databaseName = "`players`";
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public static FileConfiguration players() {
		return players_file;
	}
	
	public SQLPlayers(Player player) {
		this.player = player;
		if (Main.getMain().DBSQL == false) {
			file = true;	
		} else {
			file = false;
		}
	}
	
	public enum SQLOptions {
		Nickname, UUID, Money, Xp, FirstLogin, LastLogin;
	}
	
	private String nickname = SQLOptions.Nickname.name();
	private String uuid = SQLOptions.UUID.name();
	private String money = SQLOptions.Money.name();
	private String xp = SQLOptions.Xp.name();
	private String firstlogin = SQLOptions.FirstLogin.name();
	private String lastlogin = SQLOptions.LastLogin.name();
	
	public boolean veriifcarExiste() {
		if (file) {
			if (players().get("Players." + player.getUniqueId().toString() + "." + nickname) == null) {
				return false;
			}
			return true;
		}
		
		try {
			ResultSet resultSet = Main.getMain().getSQL().getConnection().createStatement()
					.executeQuery("SELECT * FROM "+ databaseName + " WHERE `uuid` = '" + this.player.getUniqueId().toString() + "';");
			
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
		String date = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(new Date());
		if (file) {
			if (veriifcarExiste()) {
				return;
			}
			players().set("Players." + player.getUniqueId().toString() + "." + nickname, this.player.getName());
			players().set("Players." + player.getUniqueId().toString() + "." + uuid, this.player.getUniqueId().toString());
			players().set("Players." + player.getUniqueId().toString() + "." + money, 0);
			players().set("Players." + player.getUniqueId().toString() + "." + xp, 0);
			players().set("Players." + player.getUniqueId().toString() + "." + firstlogin, date);
			players().set("Players." + player.getUniqueId().toString() + "." + lastlogin, date);
			save();
			return;
		}
		
		if (veriifcarExiste()) {
			return;
		}
		
		try {
			Main.getMain().getSQL().getConnection()
			.createStatement()
			.execute("INSERT INTO " + databaseName + "(`uuid`, `nickname`, `money`, `xp`, `firstlogin`, `lastlogin`) VALUES"
					+ "('"+ player.getUniqueId().toString() + "', "
					+ "'" + player.getName() + "', "
					+ "'" + 150 + "', "
					+ "'" + 0 + "', "
					+ "'" + date + "', "
					+ "'" + date + "');");
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
			player.getUniqueId().toString() + "';");
		} catch (SQLException e) {
			Main.getMain().loggerError(e, "");
		}
		
	}
	
	public String get(SQLOptions coluna) {
		if (file) {
			if (!veriifcarExiste()) {
				criar();
			}
			
			return players().getString("Players." + player.getUniqueId().toString() + "." + coluna.name());
		}
		
		if (!veriifcarExiste()) {
			criar();
		}
		
		try {
			ResultSet resultSet = Main.getMain().getSQL().getConnection().createStatement()
			.executeQuery("SELECT * FROM " + databaseName + " WHERE `uuid` = '" +
			player.getUniqueId().toString() + "';");
			
			if (resultSet.next()) {
				return resultSet.getString(coluna.name());
			}
			return null;
		} catch (Exception e) {
			Main.getMain().loggerError(e, "");
			return null;
		}
	}
	
	public int getInt(SQLOptions coluna) {
		if (file) {
			if (!veriifcarExiste()) {
				criar();
			}
			
			return players().getInt("Players." + player.getUniqueId().toString() + "." + coluna.name());
		}
		
		if (!veriifcarExiste()) {
			criar();
		}
		
		try {
			
			ResultSet resultSet = Main.getMain().getSQL().getConnection().createStatement()
			.executeQuery("SELECT * FROM " + databaseName + " WHERE `uuid` = '" +
			player.getUniqueId().toString() + "';");
			
			if (resultSet.next()) {
				return resultSet.getInt(coluna.name());
			}
			return 0;
		} catch (Exception e) {
			Main.getMain().loggerError(e, "");
			return 0;
		}
	}
	
	public void set(SQLOptions coluna, String valor) {
		if (file) {
			if (veriifcarExiste()) {
				criar();
			}
			players().set("Players." + player.getUniqueId().toString() + "." + coluna.name(), valor);
			save();
			return;
		}
		
		if (!veriifcarExiste()) {
			criar();
		}
		
		try {
			Main.getMain().getSQL().getConnection().createStatement().execute("UPDATE " + databaseName + " SET `" +
			coluna.name().toLowerCase() + "` = '" + valor + "' WHERE `uuid` = '" + 	get(SQLOptions.UUID) + "';");
			return;
		} catch (SQLException e) {
			Main.getMain().loggerError(e, "");
			return;
		}
	}
	
	public void set(SQLOptions coluna, int valor) {
		if (file) {
			if (veriifcarExiste()) {
				criar();
			}
			players().set("Players." + player.getUniqueId().toString() + "." + coluna.name(), valor);
			save();
			return;
		}
		
		if (!veriifcarExiste()) {
			criar();
		}
		
		try {
			Main.getMain().getSQL().getConnection()
			.createStatement().execute("UPDATE " + databaseName + " SET `" +
			
			coluna.name().toLowerCase() + "` = '" + valor + "' WHERE `uuid` = '" + 	get(SQLOptions.UUID) + "';");
			return;
		} catch (SQLException e) {
			Main.getMain().loggerError(e, "");
			return;
		}
	}
	
	public void setStatus (int money, int xp, String LastLogin) {
		if (!veriifcarExiste()) {
			criar();
		}
		
		set(SQLOptions.Nickname, get(SQLOptions.Nickname));
		set(SQLOptions.UUID, get(SQLOptions.UUID));
		set(SQLOptions.Money, money);
		set(SQLOptions.Xp, xp);
		set(SQLOptions.LastLogin, LastLogin);
	}
		
	public void save() {
		try {
			players_file.save(playerfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setup(Plugin plugin) {

		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		playerfile = new File(plugin.getDataFolder() + "/files", "players.yml");
		if (playerfile.exists()) {
			try {
				playerfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		players_file = YamlConfiguration.loadConfiguration(playerfile);
	}

}
