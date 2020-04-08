package me.skincraft.hardcoregames.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.api.CustomizationFile;
import me.skincraft.hardcoregames.managers.GroupsManager;
import me.skincraft.hardcoregames.managers.RanksManager;
import me.skincraft.hardcoregames.managers.StatusManagers;
import me.skincraft.hardcoregames.mysql.SQLPlayers;

public class ConfigurationFiles {

	public ConfigurationFiles() {
		createFile();
		playerConfigs();
		configurationFiles();
	}
	
	public void createFile() {
		SQLPlayers.setup(Main.getMain());
		GroupsManager.setup(Main.getMain());
		RanksManager.setup(Main.getMain());
		StatusManagers.setup(Main.getMain());
		CustomizationFile.setup(Main.getMain());
	}
	
	public void playerConfigs() {
		File file = new File(Main.getMain().getDataFolder() + "/files", "playergroups.yml");
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		file = new File(Main.getMain().getDataFolder() + "/files", "playerranks.yml");
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		file = new File(Main.getMain().getDataFolder() + "/files", "playerstatus.yml");
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		file = new File(Main.getMain().getDataFolder() + "/files", "players.yml");
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void configurationFiles() {
		File file = new File(Main.getMain().getDataFolder(), "config.yml");

		if (!file.exists()) {
			file.getParentFile().mkdirs();
			copiarConfig(Main.getMain().getResource("config.yml"), file);
		}

		file = new File(Main.getMain().getDataFolder(), "customization.yml");
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			copiarConfig(Main.getMain().getResource("customization.yml"), file);
		}

		file = new File(Main.getMain().getDataFolder(), "cake.png");
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			copiarConfig(Main.getMain().getResource("cake.png"), file);
		}
	}

	
	protected void copiarConfig(InputStream i, File config) {
		try {
			OutputStream out = new FileOutputStream(config);
			byte[] buf = new byte[710];
			int len;
			while ((len = i.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			i.close();
		} catch (Exception e) {
			Main.getMain().log.debug("Erro ao criar arquivos de configurações\n" + e.getMessage(), e, true);
			e.printStackTrace();
		}
	}

	
}
