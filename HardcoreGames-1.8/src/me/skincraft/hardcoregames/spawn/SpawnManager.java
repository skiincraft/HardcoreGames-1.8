package me.skincraft.hardcoregames.spawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.skincraft.hardcoregames.Main;

public class SpawnManager {
	
	private Main main = Main.getMain();
	
	public SpawnManager() {
		super();
	}
	
	private FileConfiguration getConfig() {
		return main.getConfig();
	}
	
	public enum Spawns {
		Spawn, Practice;
	}
	
	public Location getSpawn(Spawns spawns) {
		double x = (double) getConfig().get(spawns.name() + ".X");
		double y = (double) getConfig().get(spawns.name() + ".Y");;
		double z = (double) getConfig().get(spawns.name() + ".Z");;
		float yaw = (float) getConfig().get(spawns.name() + ".Yaw");
		float pitch = (float) getConfig().get(spawns.name() + ".Pitch");
		World world = Bukkit.getWorld("world");
		
		return new Location(world, x, y, z, yaw, pitch);
	}
	
	public void setSpawn(Spawns spawn, Location location) {
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		double[] xyz = new double[] {x,y,z};
		
		float yaw = location.getYaw();
		float pitch = location.getPitch();
		
		String[] loc = new String[] {"X","Y","Z"};
		
		for (int i = 0; i < loc.length;i++) {
			getConfig().set(spawn.name() + "." + loc[i], xyz[i]);
		}
		
		getConfig().set(spawn.name() + ".Pitch", pitch);
		getConfig().set(spawn.name() + ".Yaw", yaw);
	}
	
	public void teleport(Player player, Spawns to) {
		player.teleport(getSpawn(to));
	}
	
	public void teleportSpawn(Player player) {
		teleport(player, Spawns.Spawn);
		//item
	}
	
}
