package me.skincraft.hardcoregames.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.logging.Level;

import org.bukkit.Bukkit;

import me.skincraft.hardcoregames.Main;


public class MySQL {

	private Connection connection;
	private Statement statement;
	
	
	private String host, database, user, password;
	private int port;

	public String getHost() {
		return host;
	}

	public String getDatabase() {
		return database;
	}

	public String getUser() {
		return user;
	}
	
	public int getPort() {
		return port;
	}

	public String getPassword() {
		return password;
	}

	public Connection getConnection() {
		return connection;
	}

	public Statement getStatement() {
		return statement;
	}

	public MySQL() {
		this.host = Main.getMain().getConfig().getString("MySQL.Host");
		this.database = Main.getMain().getConfig().getString("MySQL.Database");
		this.port = Main.getMain().getConfig().getInt("MySQL.Port");
		this.user = Main.getMain().getConfig().getString("MySQL.User");
		this.password = Main.getMain().getConfig().getString("MySQL.Password");
		
		Main.getMain().logger(Level.INFO, "MYSQL-Host: " + host);
		Main.getMain().logger(Level.INFO, "MYSQL-Database: " + database);
		Main.getMain().logger(Level.INFO, "MYSQL-Porta: " + port);
		Main.getMain().logger(Level.INFO, "MYSQL-Usuario: " + user);
		Main.getMain().logger(Level.INFO, "MYSQL-Senha: " + password);
		
	}

	public boolean isNull() {
		return this.host == null || this.database == null || this.user == null || this.password == null;
	}

	public synchronized void abrir() {
		if (Main.getMain().getConfig().getBoolean("DBMYSQL") == false) {
			Main.getMain().DBSQL = false;
			return;
		}
		
		if (isNull()) {
			Main.getMain().logger(Level.SEVERE, "Dados do MYSQL não foram preenchidos.");
			Main.getMain().DBSQL = false;
			return;
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(
					"jdbc:mysql://" + this.host + ":" + 3306 + "/" + this.database + "?autoReconnect=true", this.user,
					this.password);
			
			this.statement = this.connection.createStatement();
			Main.getMain().logger(Level.INFO, "Conexao com o banco de dados estabelecida com sucesso.");
			Main.getMain().DBSQL = true;
			return;
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
			Main.getMain().logger(Level.SEVERE, "Driver não encontrado");
			Main.getMain().DBSQL = false;
			return;
		} catch (SQLClientInfoException exception) {
			exception.printStackTrace();
			Main.getMain().logger(Level.SEVERE, "Usuario e/ou senha incorreto(s).");
			Main.getMain().DBSQL = false;
			return;
		} catch (SQLTimeoutException exception) {
			exception.printStackTrace();
			Main.getMain().logger(Level.SEVERE, "Tempo de conexao excedido.");
			Main.getMain().DBSQL = false;
			return;
		} catch (SQLException exception) {
			exception.printStackTrace();
			Main.getMain().logger(Level.SEVERE, "Não foi possivel achar a database, tente criar manualmente.");
			Main.getMain().DBSQL = false;
			return;
		}
	}

	public synchronized void close() {
		if (this.connection == null) {
			return;
		}
		try {
			this.connection.close();
			if (this.statement != null) {
				this.statement.close();
			}
			return;
		} catch (SQLException exception) {
			exception.printStackTrace();
			return;
		}
	}

	public synchronized void execute(String command) {
		if (this.connection == null || this.statement == null) {
			Main.getMain().logger(Level.SEVERE, "Dados -[Connection/Statement]- não estão nulos. (execute())");
			Bukkit.getPluginManager().disablePlugin(Main.getMain());
			return;
		}
		try {
			this.statement.execute(command);
		} catch (SQLException exception) {
			exception.printStackTrace();
			return;
		}
	}

	public synchronized ResultSet resultSet(String query) {
		if (this.connection == null || this.statement == null) {
			Main.getMain().logger(Level.SEVERE, "Dados -[Connection/Statement]- não estão nulos. (resultSet())");
			Bukkit.getPluginManager().disablePlugin(Main.getMain());
			return null;
		}
		try {
			return this.statement.executeQuery(query);
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	public synchronized void executeUpdateAsync(String update) {
		if (this.connection == null || this.statement == null) {
			Main.getMain().logger(Level.SEVERE, "Dados -[Connection/Statement]- não estão nulos. (executeUpdateAsync())");
			Bukkit.getPluginManager().disablePlugin(Main.getMain());
			return;
		}
		try {
			Statement s = getConnection().createStatement();
			s.executeUpdate(update);
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void setup() {
		if (this.connection == null || this.statement == null) {
			Main.getMain().logger(Level.SEVERE, "Dados -[Connection/Statement]- não estão nulos. (setup())");
			Main.getMain().DBSQL = false;
			return;
		}
		try {
			this.statement.execute("CREATE TABLE IF NOT EXISTS `" + this.database + 
					"`.`players` (`id` INT UNSIGNED NOT NULL AUTO_INCREMENT, `uuid` VARCHAR(64) NOT NULL, `nickname` VARCHAR(64) NOT NULL, `money` INT, `xp` INT, `firstlogin` VARCHAR(24) NOT NULL, `lastlogin` VARCHAR(24) NOT NULL, PRIMARY KEY(`id`));");
			this.statement.execute("CREATE TABLE IF NOT EXISTS `" + this.database + 
					"`.`ranks` (`id` INT UNSIGNED NOT NULL AUTO_INCREMENT, `uuid` VARCHAR(64) NOT NULL, `nickname` VARCHAR(64) NOT NULL, `rank` VARCHAR(64) NOT NULL, PRIMARY KEY(`id`));");
			this.statement.execute("CREATE TABLE IF NOT EXISTS `" + this.database + 
					"`.`groups` (`id` INT UNSIGNED NOT NULL AUTO_INCREMENT, `uuid` VARCHAR(64) NOT NULL, `nickname` VARCHAR(64) NOT NULL, `cargo` VARCHAR(64) NOT NULL, `temporario` INT, `expira` LONG, PRIMARY KEY(`id`));");
			this.statement.execute("CREATE TABLE IF NOT EXISTS `" + this.database + 
					"`.`statushg` (`id` INT UNSIGNED NOT NULL AUTO_INCREMENT, `uuid` VARCHAR(64) NOT NULL, `nickname` VARCHAR(64) NOT NULL, `kills` INT NOT NULL, `deaths` INT NOT NULL, `wins` INT NOT NULL, PRIMARY KEY(`id`));");
			Main.getMain().DBSQL = true;
		} catch (SQLException exception) {
			exception.printStackTrace();
			Main.getMain().DBSQL = false;
		}
	}

}
