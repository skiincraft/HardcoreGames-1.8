package me.skincraft.hardcoregames.managers;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.mysql.SQLPlayers;
import me.skincraft.hardcoregames.mysql.SQLPlayers.SQLOptions;

public class RanksManager {

	private SQLPlayers playerl;
	private static FileConfiguration player_rank;
	private static File playerrank;
	private boolean file;
	
	private String databaseName = "`ranks`";
	
	public static FileConfiguration ranks() {
		return player_rank;
	}
	
	public RanksManager(SQLPlayers player) {
		this.playerl = player;
		if (Main.getMain().DBSQL == false) {
			file = true;	
		} else {
			file = false;
		}
	}
	
	public enum Ranks {
		
		LILY(ChatColor.WHITE, "Lily", "✽", 0, "m"),
		ORCHID(ChatColor.LIGHT_PURPLE, "Orchid", "✾", 1000, "l"),
		DAISY(ChatColor.YELLOW, "Daisy", "❀", 2500, "k"),
		TULIP(ChatColor.BLUE, "Tupip", "⚘", 4000, "j"),
		CARNATION(ChatColor.RED, "Carnation", "✮", 5500, "i"),
		VIOLET(ChatColor.DARK_PURPLE, "Violet", "♣", 6500, "h"),
		ROSE(ChatColor.DARK_RED, "Rose", "✿", 7500, "g"),
		CAMELLIA(ChatColor.DARK_GREEN, "Camellia", "✳", 9000, "f"),
		IRIS(ChatColor.DARK_BLUE, "Iris", "❆", 10500, "e"), 
		SUNFLOWER(ChatColor.GOLD, "Sunflower", "⚘", 12000, "d"),
		LISANTHUS(ChatColor.DARK_PURPLE, "Lisanthus", "❈", 13500, "c"),
		LOTUS(ChatColor.DARK_BLUE, "Lotus", "❃", 15000, "b"),
		CALLALILY(ChatColor.GRAY, "Callalily", "✦", 17500, "a");

		ChatColor color;
		String rank_name;
		String rank_symbol;
		int exp;
		String teamName;

		Ranks(ChatColor color, String name, String symbol, int exp, String teamName) {
			this.color = color;
			this.rank_name = name;
			this.rank_symbol = symbol;
			this.exp = exp;
			this.teamName = teamName;
		}

		public ChatColor getColor() {
			return this.color;
		}

		public String getName() {
			return this.rank_name;
		}

		public String getUpperName() {
			return this.rank_name.toUpperCase();
		}

		public String getUpperNameWithColor() {
			return this.color + this.rank_name.toUpperCase();
		}

		public String getEditedName() {
			return this.color + this.rank_symbol + " §l" + this.rank_name.toUpperCase();
		}

		public String getPrefixInName() {
			return this.color + this.rank_symbol + " " + this.rank_name.toUpperCase();
		}

		public String getSymbol() {
			return this.rank_symbol;
		}

		public String getEditedSymbol() {
			return this.color + this.rank_symbol;
		}
		
		public String getTeamName() {
			return this.teamName;
		}

		public int getExp() {
			return this.exp;
		}
	}

	
	public enum ROptions {
		Nickname, UUID, Rank;
	}
	
	private String nickname = ROptions.Nickname.name();
	private String uuid = ROptions.UUID.name();
	private String rank = ROptions.Rank.name();
	
	
	public boolean veriifcarExiste() {
		if (file) {
			if (ranks().get("Ranks." + playerl.get(SQLOptions.UUID) + "." + nickname) == null) {
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
			ranks().set("Ranks." + playerl.get(SQLOptions.UUID) + "." + nickname, playerl.get(SQLOptions.Nickname));
			ranks().set("Ranks." + playerl.get(SQLOptions.UUID) + "." + uuid, playerl.get(SQLOptions.UUID));
			ranks().set("Ranks." + playerl.get(SQLOptions.UUID) + "." + rank, Ranks.LILY.getName());
			save();
			return;
		}
		
		if (veriifcarExiste()) {
			return;
		}
		
		try {
			Main.getMain().getSQL().getConnection()
			.createStatement()
			.execute("INSERT INTO " + databaseName + "(`uuid`, `nickname`, `rank`) VALUES"
					+ "('"+ playerl.get(SQLOptions.UUID) + "', "
					+ "'" + playerl.get(SQLOptions.Nickname) + "', "
					+ "'" + Ranks.LILY.getName() + "');");
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
	
	public String get(ROptions coluna) {
		if (file) {
			if (!veriifcarExiste()) {
				criar();
			}
			
			return ranks().getString("Ranks." + playerl.get(SQLOptions.UUID) + "." + coluna.name());
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
	
	public void set(ROptions coluna, String valor) {
		if (file) {
			if (veriifcarExiste()) {
				criar();
			}
			ranks().set("Ranks." + playerl.get(SQLOptions.UUID) + "." + coluna.name(), valor);
			save();
			return;
		}
		
		if (!veriifcarExiste()) {
			criar();
		}
		
		try {
			Main.getMain().getSQL().getConnection()
			.createStatement().execute("UPDATE " + databaseName + " SET `" +
			
			coluna.name().toLowerCase() + "` = '" + valor + "' WHERE `uuid` = '" + 	get(ROptions.UUID) + "';");
			return;
		} catch (SQLException e) {
			Main.getMain().loggerError(e, "");
			return;
		}
	}
	
	public void setRank (Ranks rank) {
		if (!veriifcarExiste()) {
			criar();
		}
		
		set(ROptions.Nickname, get(ROptions.Nickname));
		set(ROptions.UUID, get(ROptions.UUID));
		set(ROptions.Rank, rank.getName());
	}
	
	public Ranks getRank() {
		    for(Ranks current : Ranks.values()) {
		        if(current.getName().equalsIgnoreCase(get(ROptions.Rank))) {
		            return current;
		        }
		    }
			return null;
	}
	
	/*public Ranks getRank() {
		List<Ranks> ra = Arrays.asList(Ranks.values());
		for (int i = 0; i < ra.size(); i++) {
			if (ra.get(i).name().equalsIgnoreCase(get(ROptions.Rank))) {
				return ra.get(i);
			}
		}
		return Ranks.LILY;
	}
	*/
	public Ranks getNextRank() {
		ArrayList<Ranks> list = new ArrayList<>();
		for (Ranks ranks : Ranks.values()) {
			if (!list.contains(ranks)) {
				list.add(ranks);
			}
		}
		return list.get(getRank().ordinal() + 1);
	}

	public Ranks getLeastRank() {
		ArrayList<Ranks> list = new ArrayList<>();
		for (Ranks ranks : Ranks.values()) {
			if (!list.contains(ranks)) {
				list.add(ranks);
			}
		}
		return list.get(getRank().ordinal() - 1);
	}

	public boolean hasRank(Ranks rank) {
		return getRank().equals(rank);
	}

	public boolean hasRankPermission(Ranks rank) {
		return getRank().ordinal() >= rank.ordinal();
	}
	
	public int percentage() {
		if (hasRank(Ranks.CALLALILY)) {
			return 100;
		}

		return playerl.getInt(SQLOptions.Xp) * 100 / getNextRank().getExp();
	}	
	
	public List<Ranks> addRankList() {
		List<Ranks> rankList = new ArrayList<Ranks>();
		rankList.add(Ranks.LILY);
		rankList.add(Ranks.ORCHID); 
		rankList.add(Ranks.DAISY);
		rankList.add(Ranks.TULIP);
		rankList.add(Ranks.CARNATION);
		rankList.add(Ranks.VIOLET);
		rankList.add(Ranks.ROSE);
		rankList.add(Ranks.CAMELLIA);
		rankList.add(Ranks.IRIS);
		rankList.add(Ranks.SUNFLOWER);
		rankList.add(Ranks.LISANTHUS);
		rankList.add(Ranks.LOTUS);
		rankList.add(Ranks.CALLALILY);
		return rankList;
		
	}
	
	public void checkUpdate() {
		new BukkitRunnable() {
			public void run() {
				int xp = playerl.getInt(SQLOptions.Xp);
				List<Ranks> rankings = new ArrayList<Ranks>();
				rankings.addAll(addRankList());
				int rankSize = rankings.size();
				
				if (getRank().getExp() > xp) {
					setRank(getLeastRank());
				}
				
				for(int i = 0; i < rankSize;i++) {
					if (hasRank(rankings.get(i))) {
						if (xp >= getNextRank().getExp()) {
							String rankupdated = getNextRank().getColor() + getNextRank().getName();
							String player = playerl.get(SQLOptions.Nickname);
							playerl.getPlayer().sendMessage("§3§lRANK §fVocê upou para o rank "+ rankupdated);
							Bukkit.broadcastMessage("§3§lRANK §e" + player + " upou para o rank " + rankupdated);

							setRank(getNextRank());
							//new TagManager(sqlPlayer.getPlayer()).setTag(new TagManager(sqlPlayer.getPlayer()).getTag());							
						}
					}
				}
			}
		}.runTaskTimer(Main.getMain(), 0, 20);
	}

	
	public void save() {
		try {
			player_rank.save(playerrank);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setup(Plugin plugin) {

		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		playerrank = new File(plugin.getDataFolder() + "/files", "playerranks.yml");
		if (playerrank.exists()) {
			try {
				playerrank.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		player_rank = YamlConfiguration.loadConfiguration(playerrank);
	}

}
