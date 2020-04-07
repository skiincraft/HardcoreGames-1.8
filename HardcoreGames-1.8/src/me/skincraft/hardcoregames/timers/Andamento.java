package me.skincraft.hardcoregames.timers;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.api.EntenAPI;

public class Andamento {

	private static Integer runnable = null;
	private int startingTime;
	private String[] messages = new String[]
			{"§eUm minifeast nasceu entre: "};
	
	public Andamento(int startingTime) {
		new TimersManager().setState(State.Andamento);
		this.startingTime = startingTime;
		startRunnable();
	}
	
	public void startRunnable() {
		new TimersManager().setTimer(State.Andamento, startingTime);
		runnable = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				int tempo = new TimersManager().getTimer(State.Andamento);
				new TimersManager().setTimer(State.Andamento, tempo+1);
				
				minifeastSpawns(new TimersManager().getTimer(State.Andamento));
				
				if(tempo == 2100) {
					criarAreaBatle();
					for (Player player : Bukkit.getOnlinePlayers()) {
						player.sendTitle("§6§lFINAL BATTLE", null);
						new EntenAPI(Main.getMain()).sendActionbar("§5Você esta na arena final!", player);
					}
				}
				
				if (tempo == 2100 + (5*60)) {
					//Acabar partida;
				}
				
				
			}
		}, 0, 20L);		
	}
	
	  public static void AreaBattle(Location loc, int r, Material mat, int alturaY) {
		  int cx = loc.getBlockX();
		  int cy = loc.getBlockY();
		  int cz = loc.getBlockZ();
		  World w = loc.getWorld();
		  int rSquared = r * r;
		  for (int x = cx - r; x <= cx + r; x++)
		  for (int z = cz - r; z <= cz + r; z++)
		  for (int y = cy + 1; y <= cy + alturaY; y++)
		  if ((cx - x) * (cx - x) + (cz - z) * (cz - z) <= rSquared)
		  w.getBlockAt(x, y, z).setType(mat);
	  }
	  
	   @SuppressWarnings("deprecation")
		public static void criarAreaBatle() {
		   int aleatorioX = (int)(1.0D + Math.random() * 10.0D);
		   int aleatorioZ = (int)(1.0D + Math.random() * 10.0D);
		   int aleatorioY = 0;
		   World world = Main.getPlugin().getServer().getWorld("world");
		   for (int i = 90; i > 95; i--) {
			   Block blockY = world.getBlockAt(aleatorioX, i, aleatorioZ);
			   int y = blockY.getTypeId();
			   if (y == 0) {
			   aleatorioY = i;
			   }
		   }
		   
		   
		   Location loc = new Location(world, aleatorioX, aleatorioY, aleatorioZ);
		   AreaBattle(loc, 50, Material.AIR, 90);
		   Location loc2 = new Location(world, aleatorioX, aleatorioY + 90, aleatorioZ);
		   AreaBattle(loc2, 50, Material.AIR, 90);
		   Location loc3 = new Location(world, aleatorioX, aleatorioY + 90, aleatorioZ);
		   AreaBattle(loc3, 50, Material.AIR, 90);
		   Location loc4 = new Location(world, aleatorioX, aleatorioY + 90, aleatorioZ);
		   AreaBattle(loc4, 50, Material.AIR, 90); 
		   for (Player player : Bukkit.getOnlinePlayers()) {
			   Location loctp = new Location(Bukkit.getWorld("world"), aleatorioX, aleatorioY + 4, aleatorioZ);
			   loctp.getWorld().strikeLightningEffect(loctp);
			   player.teleport(loctp);
			   Bukkit.getServer().getWorld("world").setTime(20000L);
		   
		   }
	  }
	
	private void minifeastSpawns(int tempo) {
		Integer[] timeevents = new Integer[] {210, 450, 750, 1050, 1350, 1800, 2000};
		for (int i = 0; i < timeevents.length;i++) {
			if (Arrays.asList(timeevents).get(i) == tempo) {
				Bukkit.broadcastMessage(messages[0]);
				//spawnarMinifeast
			}
		}
	}
	
	public static void cancelRunnable() {
		if (runnable != null) {
			Bukkit.getScheduler().cancelTask(runnable);
			runnable = null;
		}
	}

	
}
