package me.skincraft.hardcoregames.managers;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.api.LongAPI;
import me.skincraft.hardcoregames.mysql.SQLPlayers;
import me.skincraft.hardcoregames.mysql.SQLPlayers.SQLOptions;

public class GroupsManager {

	private SQLPlayers playerl;
	private static FileConfiguration player_group;
	private static File playergroup;
	private boolean file;
	
	private String databaseName = "`groups`";
	
	public static FileConfiguration group() {
		return player_group;
	}
	
	public GroupsManager(SQLPlayers player) {
		this.playerl = player;
		if (Main.getMain().DBSQL == false) {
			file = true;	
		} else {
			file = false;
		}
	}
	
	public enum Cargos {
		
		MEMBRO(ChatColor.GRAY, "Membro"),
		TOP(ChatColor.DARK_GREEN, "Top"),
		VIP(ChatColor.GREEN, "Vip"),
		PRO(ChatColor.GOLD, "Pro"),
		ULTRA(ChatColor.LIGHT_PURPLE, "Ultra"),
		BETA(ChatColor.DARK_BLUE, "Beta"),
		YOUTUBER(ChatColor.AQUA, "Youtuber"),
		BUILDER(ChatColor.YELLOW, "Builder"),
		SUPPORT(ChatColor.BLUE, "Support"),
		YOUTUBERPLUS(ChatColor.DARK_AQUA, "Youtuber+"),
		TRIAL(ChatColor.DARK_PURPLE, "Trial"),
		MOD(ChatColor.DARK_PURPLE, "Mod"),
		MODGC(ChatColor.DARK_PURPLE, "ModGC"),
		MODPLUS(ChatColor.DARK_PURPLE, "Mod+"),
		GERENTE(ChatColor.RED, "Gerente"),
		ADMIN(ChatColor.RED, "Admin"),
		DIRETOR(ChatColor.RED, "Diretor"),
		DEV(ChatColor.DARK_AQUA, "Developer"),
		DONO(ChatColor.DARK_RED, "Dono");

		ChatColor chatColor;
		String groupName;

		Cargos(ChatColor chatColor, String groupName) {
			this.chatColor = chatColor;
			this.groupName = groupName;
		}

		public ChatColor getChatColor() {
			return this.chatColor;
		}

		public String getGroupName() {
			return this.groupName;
		}

		public String getGroupUpperName() {
			return this.groupName.toUpperCase();
		}

		public String getFullName() {
			return this.chatColor + this.groupName;
		}

		public String getFullUpperName() {
			return this.chatColor + "§l" + this.groupName.toUpperCase();
		}
	}

	
	public enum GOptions {
		Nickname, UUID, Cargo, Temporario, Expira;
	}
	
	private String nickname = GOptions.Nickname.name();
	private String uuid = GOptions.UUID.name();
	private String grupo = GOptions.Cargo.name();
	private String temporario = GOptions.Temporario.name();
	private String expira = GOptions.Expira.name();
	
	String sqlGet(SQLOptions get){
		return this.playerl.get(get);
	}
	
	String groupName(Cargos cargo) {
		return cargo.name();
	}
	
	public boolean veriifcarExiste() {
		if (file) {
			if (group().get("Grupos." + playerl.get(SQLOptions.UUID) + "." + nickname) == null) {
				return false;
			}
			return true;
		}
		
		try {
			ResultSet resultSet = Main.getMain().getSQL().getConnection().createStatement()
					.executeQuery("SELECT * FROM " + databaseName + " WHERE `uuid` = '" + this.playerl.get(SQLOptions.UUID) + "';");
			
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
			group().set("Grupos." + playerl.get(SQLOptions.UUID) + "." + nickname, sqlGet(SQLOptions.Nickname));
			group().set("Grupos." + playerl.get(SQLOptions.UUID) + "." + uuid, sqlGet(SQLOptions.UUID));
			group().set("Grupos." + playerl.get(SQLOptions.UUID) + "." + grupo, groupName(Cargos.MEMBRO));
			group().set("Grupos." + playerl.get(SQLOptions.UUID) + "." + temporario, 0);
			group().set("Grupos." + playerl.get(SQLOptions.UUID) + "." + expira, 0L);
			save();
			return;
		}
		
		if (veriifcarExiste()) {
			return;
		}
		
		try {
			Main.getMain().getSQL().getConnection().createStatement()
			.execute("INSERT INTO " + databaseName + "(`uuid`, `nickname`, `cargo`, `temporario`, `expira`) VALUES"
					+ "('"+ playerl.get(SQLOptions.UUID) + "', "
					+ "'" + playerl.get(SQLOptions.Nickname) + "', "
					+ "'" + groupName(Cargos.MEMBRO) + "', "
					+ "'" + 0 + "', "
					+ "'" + 0L + "');");
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
	
	public String get(GOptions coluna) {
		if (file) {
			if (!veriifcarExiste()) {
				criar();
			}
			
			return group().getString("Grupos." + playerl.get(SQLOptions.UUID) + "." + coluna.name());
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
	
	public void set(GOptions coluna, String valor) {
		if (file) {
			if (veriifcarExiste()) {
				criar();
			}
			group().set("Gropos." + playerl.get(SQLOptions.UUID) + "." + coluna.name(), valor);
			save();
			return;
		}
		
		if (!veriifcarExiste()) {
			criar();
		}
		
		try {
			Main.getMain().getSQL().getConnection()
			.createStatement().execute("UPDATE " + databaseName + " SET `" +
			
			coluna.name().toLowerCase() + "` = '" + valor + "' WHERE `uuid` = '" + 	get(GOptions.UUID) + "';");
			return;
		} catch (SQLException e) {
			Main.getMain().loggerError(e, "");
			return;
		}
	}
	
	public void set(GOptions coluna, boolean valor) {
		if (file) {
			if (veriifcarExiste()) {
				criar();
			}
			int num = 0;
			if (valor == true) {
				num = 1;
			}
			group().set("Gropos." + playerl.get(SQLOptions.UUID) + "." + coluna.name(), num);
			save();
			return;
		}
		
		if (!veriifcarExiste()) {
			criar();
		}
		
		try {
			int num = 0;
			if (valor == true) {
				num = 1;
			}
			Main.getMain().getSQL().getConnection()
			.createStatement().execute("UPDATE " + databaseName + " SET `" +
			
			coluna.name().toLowerCase() + "` = '" + num + "' WHERE `uuid` = '" + 	get(GOptions.UUID) + "';");
			return;
		} catch (SQLException e) {
			Main.getMain().loggerError(e, "");
			return;
		}
	}
	
	public void set(GOptions coluna, long valor) {
		if (file) {
			if (veriifcarExiste()) {
				criar();
			}
			group().set("Gropos." + playerl.get(SQLOptions.UUID) + "." + coluna.name(), valor);
			save();
			return;
		}
		
		if (!veriifcarExiste()) {
			criar();
		}
		
		try {
			Main.getMain().getSQL().getConnection()
			.createStatement().execute("UPDATE " + databaseName + " SET `" +
			
			coluna.name().toLowerCase() + "` = '" + valor + "' WHERE `uuid` = '" + 	get(GOptions.UUID) + "';");
			return;
		} catch (SQLException e) {
			Main.getMain().loggerError(e, "");
			return;
		}
	}
	
	public void setCargo (Cargos group, boolean temporario, LongAPI tempo) {
		if (!veriifcarExiste()) {
			criar();
		}
		
		long timing = tempo.convert();
		
		set(GOptions.Nickname, get(GOptions.Nickname));
		set(GOptions.UUID, get(GOptions.UUID));
		set(GOptions.Cargo, group.name());
		set(GOptions.Temporario, temporario);
		set(GOptions.Expira, timing);
	}
	
	public Cargos getRank() {
		List<Cargos> ra = Arrays.asList(Cargos.values());
		for (int i = 0; i < ra.size(); i++) {
			if (ra.get(i).name().equalsIgnoreCase(get(GOptions.Cargo))) {
				return ra.get(i);
			}
		}
		return Cargos.MEMBRO;
	}
	
	public Cargos getCargo () {
		try {
			return Cargos.valueOf(get(GOptions.Cargo));
		} catch (Exception e) {
			Main.getMain().loggerError(e, "");
		}
		return null;
	}
	
	public Cargos getNextCargo() {
		ArrayList<Cargos> list = new ArrayList<>();
		for (Cargos groups : Cargos.values()) {
			if (!list.contains(groups)) {
				list.add(groups);
			}
		}
		return list.get(getCargo().ordinal() + 1);
	}
	
	public Cargos getLeastCargo() {
		ArrayList<Cargos> list = new ArrayList<>();
		for (Cargos groups : Cargos.values()) {
			if (!list.contains(groups)) {
				list.add(groups);
			}
		}
		return list.get(getCargo().ordinal() - 1);
	}
	
	public boolean hasGroup(Cargos group) {
		return getCargo().equals(group);
	}

	public boolean hasGroupPermission(Cargos group) {
		return getCargo().ordinal() >= group.ordinal();
	}
	
	public boolean hasPermission(Cargos cargo, String permission) {
		if (playerl.getPlayer().hasPermission(permission) == true) {
			return true;
		}
		
		if (hasGroupPermission(cargo) == true) {
			return true;
		}
		return false;
	}
	
	public boolean isTemporary() {
		if (file) {
			if (group().getInt("Grupos." + playerl.get(SQLOptions.UUID) + "." + "Temporario") != 0) {
			return true;
			}
			return false;
		}
		try {
			
			ResultSet resultSet = Main.getMain().getSQL().getConnection()
			.createStatement().executeQuery("SELECT * FROM " + databaseName + " WHERE `uuid` = '" +
			playerl.get(SQLOptions.UUID) + "';");
			
			if (resultSet.next()) {
				if (resultSet.getInt("temporario") != 0) {
					return true;
				}
				return false;
							//resultSet.getBoolean("temporario");	
				
			}
			
			return false;
		}catch (SQLException e) {
			Main.getMain().loggerError(e, "");
			return true;
		}
	}
	
	public void checkTemporary() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (isTemporary()) {
					long expira = Long.valueOf(get(GOptions.Expira));
					int segundos = (int) ((expira - System.currentTimeMillis()) / 1000L);
					if (segundos <= 0) {
						Cargos cargo = getCargo();
						playerl.getPlayer().sendMessage("§eSeu rank " + cargo.getChatColor() + cargo.getFullName() +
								" temporario expirou.");
						
						set(GOptions.Cargo, Cargos.MEMBRO.name());
						set(GOptions.Temporario, false);
						set(GOptions.Expira, 0L);
						
						
					}
				}
			}
		}.runTaskTimer(Main.getMain(), 0, 20);
	}
	
	public void save() {
		try {
			player_group.save(playergroup);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setup(Plugin plugin) {

		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		playergroup = new File(plugin.getDataFolder() + "/files", "playergroups.yml");
		if (playergroup.exists()) {
			try {
				playergroup.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		player_group = YamlConfiguration.loadConfiguration(playergroup);
	}

}
