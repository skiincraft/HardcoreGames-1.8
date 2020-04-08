package me.skincraft.hardcoregames.events;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.enums.ItemList;
import me.skincraft.hardcoregames.managers.GroupsManager;
import me.skincraft.hardcoregames.managers.GroupsManager.Cargos;
import me.skincraft.hardcoregames.managers.KitManager;
import me.skincraft.hardcoregames.managers.KitManager.Kits;
import me.skincraft.hardcoregames.managers.PlayerHGManager;
import me.skincraft.hardcoregames.managers.PlayerHGManager.PlayerState;
import me.skincraft.hardcoregames.managers.StatusManagers;
import me.skincraft.hardcoregames.managers.StatusManagers.SOptions;
import me.skincraft.hardcoregames.mysql.SQLPlayers;
import me.skincraft.hardcoregames.mysql.SQLPlayers.SQLOptions;
import me.skincraft.hardcoregames.timers.State;
import me.skincraft.hardcoregames.timers.TimersManager;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {
	
	private enum DeathTypes {
		ARROW, CACTUS, DROWNED, EXPLOSION, FALLING, FALLINGBLOCKS,
		FIRE, FIREWORK, LAVA, LIGHTNING, MAGMABLOCK, POTIONS, MISC,
		STARVING, SUFFOCATION, VOID, WITHER, DIED;
	}
	
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
	
	public String[] messages(DeathTypes death){
		
		if (death.equals(DeathTypes.ARROW)) {
			String[] arrow = new String[] 
					{"was shot by arrow"};
			return arrow;
		}
		
		if (death.equals(DeathTypes.CACTUS)) {
			String[] cactus = new String[] 
					{"was pricked to death",
							"hugged a cactus",
							"was stabbed to death"};
			return cactus;
		}
		
		if (death.equals(DeathTypes.DIED)) {
			String[] died = new String[] {"died"};
			return died;
		} //Simplesmente morreu
		
		if (death.equals(DeathTypes.DROWNED)) {
			String[] drowned = new String[] 
					{"drowned"};
			return drowned;
		}//afogado
		
		if (death.equals(DeathTypes.EXPLOSION)) {
			String[] explosion = new String[] 
					{"blew up"};
			return explosion;
		}
		
		if (death.equals(DeathTypes.FALLING)) {
			String[] falling = new String[]
					{"hit the ground too hard",
							"fell from a high place",
							"fell off a ladder",
							"fell off some vines",
							"fell out of the water",
							"fell into a patch of fire",
							"fell into a patch of cacti"};
			return falling;
		}
		
		if (death.equals(DeathTypes.FALLINGBLOCKS)) {
			String[] fallingblocks = new String[] 
					{"was squashed by a falling anvil",
							"was squashed by a falling block"};
			return fallingblocks;
		}
		
		if (death.equals(DeathTypes.FIRE)) {
			String[] fire = new String[]
					{"went up in flames",
							"walked into fire",
							"burned to death"};
			return fire;	
		}
		
		if (death.equals(DeathTypes.FIREWORK)) {
			String[] firework = new String[]
					{"went off with a bang"};
			return firework;
		}
		
		if (death.equals(DeathTypes.LAVA)) {
			String[] lava = new String[] 
					{"tried to swim in lava"};
			return lava;
		}
		
		if (death.equals(DeathTypes.LIGHTNING)) {
			String[] lightning = new String[] 
					{"was struck by lightning"};
			return lightning;
		}
		
		if (death.equals(DeathTypes.MAGMABLOCK)) {
			String[] magmablock = new String[]
					{"discovered floor was lava"};
			return magmablock;
		}
		
		if (death.equals(DeathTypes.POTIONS)) {
			String[] potions = new String[] 
					{"was killed by magic"};
			return potions;
		}
		
		if (death.equals(DeathTypes.STARVING)) {
			String[] starving = new String[] 
					{"starved to death"};
			return starving;//fome
		}
		
		if (death.equals(DeathTypes.SUFFOCATION)) {
			String[] suffocation = new String[] 
					{"suffocated in a wall",
							"was squished too much"};
			return suffocation;
		}
		
		if (death.equals(DeathTypes.VOID)) {
			String[] voiD = new String[] 
					{"fell out of the world",
							"fell from a high place and fell out of the world"};
			return voiD;
		}
		
		if (death.equals(DeathTypes.WITHER)) {
			String[] wither = new String[]
					{"withered away"};
			return wither;
		}
		
		return null;
	}
	
	public String[] entityMessages() {
		String[] types = new String[] 
				{"was shot by",
						"walked into a cactus while trying to escape",
						"drowned whilst trying to escape",
						"was blown up by",
						"was doomed to fall by",
						"was shot off some vines by",
						"was shot off a ladder by",
						"was blown from a high place by",
						"was burnt to a crisp whilst fighting",
						"walked into a fire whilst fighting",
						"tried to swim in lava while trying to escape",
						"was slain by",
						"got finished off by",
						"was fireballed by",
						"was killed by",
						"was killed while trying to hurt",
						"was pummeled by"};
		return types;
	}
	
	public static String itemName(ItemStack i) {
		
		ItemList.sendList();
		
		int size = ItemList.itemMaterial.size();
		List<Material> itemM = ItemList.itemMaterial;
		List<String> itemN = ItemList.itemName;
		
				
		String nome = null;
		for (int o = 1; o < size; o++) {
			if (i.getType() == itemM.get(o)) {
				nome = itemN.get(o);
				return nome;
			}
		}
		return i.getType().toString();
	}
	
	public String getHabilidadePrimaria(Player p) {
		return new KitManager(p).getPlayerKit1().getDisplayName();
	}

	public String getHabilidades(Player p) {
		KitManager kit = new KitManager(p);
		if (kit.getPlayerKit2().equals(Kits.Nenhum)) {
			return "§c" + kit.getPlayerKit1().getDisplayName();
		}
		return "§b" + kit.getPlayerKit1().getDisplayName() + "§7/§b" + kit.getPlayerKit2().getDisplayName();
	}

	public String getHabilidadeSecundaria(Player p) {
		return new KitManager(p).getPlayerKit2().getDisplayName();
	}
	
	String arma;
	@EventHandler
	public void playerDeathMessages(PlayerDeathEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		
		if (!(e.getEntity().getKiller() instanceof Player)) {
			return;
		}
		
		Player player = e.getEntity();
		Player killer = e.getEntity().getKiller();		
		
		e.getEntity().getLocation().getWorld().strikeLightning(player.getLocation());
		String[] en = entityMessages();
		for (int i = 0;i < en.length;i++) {
			if (e.getDeathMessage()
					.contains(Arrays.asList(en).get(i))) {
				
				GroupsManager group = new GroupsManager(new SQLPlayers(player));
				if (group.hasPermission(Cargos.VIP,"vip.renascer")) {
					if (new TimersManager().getTimer() <= 300) {
						e.setDeathMessage(null);
						return;
					}
				}
				
				e.setDeathMessage(null);
				
				//Adicionar XP e KILL
				statusAdd(killer);
				statusRemove(player);
				arma = itemName(killer.getItemInHand());
				
				String killerkit = getHabilidades(killer);
				String playerkit = getHabilidades(player);
				
				Bukkit.broadcastMessage(
						"§8" + killer.getName() + "§7(" + killerkit + "§7)" +
						" §b matou o " +
						"§8" + player.getName() + "§7(" + playerkit + "§7)" +
						" usando sua " + arma);
				
				Bukkit.broadcastMessage("§e" + PlayerHGManager.getSize(PlayerState.ALIVE) + " jogadores restantes.");
				return;
			}
		}
	}
	
	public static Map<String, String> lastDamage = new HashMap<>();
	@EventHandler
	public void lastDamageCapture(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		
		Player player = (Player) e.getEntity();
		Player damager = (Player) e.getDamager();
		
		if (lastDamage.containsKey(player.getName())) {
			if (lastDamage.get(player.getName())
					.equalsIgnoreCase(damager.getName())) {
				return;
			}
			lastDamage.remove(player.getName());
		}
		
		lastDamage.put(player.getName(), damager.getName());
		
		Bukkit.getScheduler().runTaskLater(Main.getMain(), new Runnable() {
			
			@Override
			public void run() {
				if (lastDamage.get(player.getName()).equals(damager.getName())) {
				lastDamage.remove(player.getName());
				}
				
			}
		}, 10*20L);
	}
	
	
	@EventHandler
	public void playerDeathLastDamageMessage(PlayerDeathEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player player = (Player)e.getEntity();
		
		if (player.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK) {
			return;
		}
		
		if (!lastDamage.containsKey(player.getName())) {
			return;
		}
		
		if (!(new TimersManager().getState() == State.Andamento)) {
			return;
		}
		e.getEntity().getLocation().getWorld().strikeLightning(player.getLocation());
		
		GroupsManager group = new GroupsManager(new SQLPlayers(player));
		if (group.hasPermission(Cargos.VIP,"vip.renascer")) {
			if (new TimersManager().getTimer() <= 300) {
				e.setDeathMessage(null);
				return;
			}
		}
		
		e.setDeathMessage(null);
		Player killer = Bukkit.getPlayer(lastDamage.get(player.getName())); 
		arma = itemName(killer.getItemInHand());
		
		//Adicionar XP e KILL
		statusAdd(killer);
		statusRemove(player);
		
		String killerkit = getHabilidades(killer);
		String playerkit = getHabilidades(player);
		
		Bukkit.broadcastMessage(
				"§8" + killer.getName() + "§7(" + killerkit + "§7)" +
				" §b matou o " +
				"§8" + player.getName() + "§7(" + playerkit + "§7)" +
				" usando sua " + arma);
		
		Bukkit.broadcastMessage("§e" + PlayerHGManager.getSize(PlayerState.ALIVE) + " jogadores restantes.");
		return;
	}
	
	public void otherDeathMessages(PlayerDeathEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player player = (Player)e.getEntity();
		if (lastDamage.containsKey(player.getName())) {
			return;
		}
		
		if (!(new TimersManager().getState() == State.Andamento)) {
			return;
		}
		e.getEntity().getLocation().getWorld().strikeLightning(player.getLocation());
		GroupsManager group = new GroupsManager(new SQLPlayers(player));
		if (group.hasPermission(Cargos.VIP,"vip.renascer")) {
			if (new TimersManager().getTimer() <= 300) {
				e.setDeathMessage(null);
				return;
			}
		}
		
		List<String> arrow = Arrays.asList(messages(DeathTypes.ARROW));
		
		for (int i = 0; i < arrow.size();i++) {
			if (e.getDeathMessage().contains(arrow.get(i))) {
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 morreu por uma flecha.");
				return;
			}
		}
		
		List<String> cactus = Arrays.asList(messages(DeathTypes.CACTUS));
		
		for (int i = 0; i < cactus.size();i++) {
			if (e.getDeathMessage().contains(cactus.get(i))) {
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 morreu espetado por um cacto.");
				return;
			}
		}
		
		List<String> died = Arrays.asList(messages(DeathTypes.DIED));
		
		for (int i = 0; i < died.size();i++) {
			if (e.getDeathMessage().contains(died.get(i))) {
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 simplesmente morreu.");
				return;
			}
		}
		
		List<String> drowned = Arrays.asList(messages(DeathTypes.DROWNED));
		
		for (int i = 0; i < drowned.size();i++) {
			if (e.getDeathMessage().contains(drowned.get(i))) {
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 morreu afogado.");
				return;
			}
		}
		
		List<String> explosion = Arrays.asList(messages(DeathTypes.EXPLOSION));
		
		for (int i = 0; i < explosion.size();i++) {
			if (e.getDeathMessage().contains(explosion.get(i))) {
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 morreu em uma explosão.");
				return;
			}
		}
		
		List<String> falling = Arrays.asList(messages(DeathTypes.FALLING));
		
		for (int i = 0; i < falling.size();i++) {
			if (e.getDeathMessage().contains(falling.get(i))) {
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 morreu caindo de um lugar alto.");
				return;
			}
		}
		
		List<String> fallingblock = Arrays.asList(messages(DeathTypes.FALLINGBLOCKS));
		
		for (int i = 0; i < fallingblock.size();i++) {
			if (e.getDeathMessage().contains(fallingblock.get(i))) {
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 morreu esmagado por um bloco.");
				return;
			}
		}
		
		List<String> fire = Arrays.asList(messages(DeathTypes.FIRE));
		
		for (int i = 0; i < fire.size();i++) {
			if (e.getDeathMessage().contains(fire.get(i))) {
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 morreu pegando fogo.");
				return;
			}
		}
		
		List<String> firework = Arrays.asList(messages(DeathTypes.FIREWORK));
		
		for (int i = 0; i < firework.size();i++) {
			if (e.getDeathMessage().contains(firework.get(i))) {
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 morreu atingido por fogos de artificio.");
				return;
			}
		}
		
		List<String> lava = Arrays.asList(messages(DeathTypes.LAVA));
		
		for (int i = 0; i < lava.size();i++) {
			if (e.getDeathMessage().contains(lava.get(i))) {
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 morreu nadando na lava.");
				return;
			}
		}
		
		List<String> lightning = Arrays.asList(messages(DeathTypes.LIGHTNING));
		
		for (int i = 0; i < lightning.size();i++) {
			if (e.getDeathMessage().contains(lightning.get(i))) {
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 morreu atingido por um raio");
				return;
			}
		}
		
		List<String> magmablock = Arrays.asList(messages(DeathTypes.MAGMABLOCK));
		
		for (int i = 0; i < magmablock.size();i++) {
			if (e.getDeathMessage().contains(magmablock.get(i))) {
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 morreu pisando em bloco de magma");
				return;
			}
		}
		
		List<String> potions = Arrays.asList(messages(DeathTypes.POTIONS));
		
		for (int i = 0; i < potions.size();i++) {
			if (e.getDeathMessage().contains(potions.get(i))) {
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 morreu atingido por magia");
				return;
			}
		}
		
		List<String> starving = Arrays.asList(messages(DeathTypes.STARVING));
		
		for (int i = 0; i < starving.size();i++) {
			if (e.getDeathMessage().contains(starving.get(i))) {
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 morreu com muita fome");
				return;
			}
		}
		
		List<String> suffocation = Arrays.asList(messages(DeathTypes.SUFFOCATION));
		
		for (int i = 0; i < suffocation.size();i++) {
			if (e.getDeathMessage().contains(suffocation.get(i))) {
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 morreu sufocado");
				return;
			}
		}
		
		List<String> voiD = Arrays.asList(messages(DeathTypes.VOID));
		
		for (int i = 0; i < voiD.size();i++) {
			if (e.getDeathMessage().contains(voiD.get(i))) {
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 simplesmente morreu");
				return;
			}
		}
		
		List<String> wither = Arrays.asList(messages(DeathTypes.WITHER));
		
		for (int i = 0; i < wither.size();i++) {
			if (e.getDeathMessage().contains(wither.get(i))) {
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 morreu com efeito do wither");
				return;
			}
		}	
	}
	
	public void animalDeathMessages(PlayerDeathEvent e) {
		Player player = e.getEntity();
		EntityDamageEvent damageEvent = player.getLastDamageCause();
		if (!(damageEvent instanceof EntityDamageByEntityEvent)) {
			return;
		}
		Entity damager = ((EntityDamageByEntityEvent) damageEvent).getDamager();
		if ((damager instanceof Animals)) {
			GroupsManager group = new GroupsManager(new SQLPlayers(player));
			e.getEntity().getLocation().getWorld().strikeLightning(player.getLocation());
			if (group.hasPermission(Cargos.VIP,"vip.renascer")) {
				if (new TimersManager().getTimer() <= 300) {
					e.setDeathMessage(null);
					return;
				}
				
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 morreu para um mob");
				Bukkit.broadcastMessage("§e" + PlayerHGManager.getSize(PlayerState.ALIVE) + " jogadores restantes.");
			}
		}
	}
	
	@EventHandler
	public void monsterDeathMessages(PlayerDeathEvent e) {
		Player player = e.getEntity();
		EntityDamageEvent damageEvent = player.getLastDamageCause();
		if (!(damageEvent instanceof EntityDamageByEntityEvent)) {
			return;
		}
		Entity damager = ((EntityDamageByEntityEvent) damageEvent).getDamager();
		if ((damager instanceof Monster)) {
			GroupsManager group = new GroupsManager(new SQLPlayers(player));
			e.getEntity().getLocation().getWorld().strikeLightning(player.getLocation());
			if (group.hasPermission(Cargos.VIP,"vip.renascer")) {
				if (new TimersManager().getTimer() <= 300) {
					e.setDeathMessage(null);
					return;
				}
				
				e.setDeathMessage(null);
				Bukkit.broadcastMessage("§7" + player.getName() + "§7 morreu para um mob");
				Bukkit.broadcastMessage("§e" + PlayerHGManager.getSize(PlayerState.ALIVE) + " jogadores restantes.");
			}
		}
	}
}
