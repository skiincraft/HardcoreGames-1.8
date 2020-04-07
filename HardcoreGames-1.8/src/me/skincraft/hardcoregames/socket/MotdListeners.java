package me.skincraft.hardcoregames.socket;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.util.CachedServerIcon;
import org.henrya.pingapi.PingEvent;
import org.henrya.pingapi.PingListener;
import org.henrya.pingapi.PingReply;
import org.henrya.pingapi.StringUtils;

import me.skincraft.hardcoregames.Main;
import me.skincraft.hardcoregames.timers.TimersManager;

public class MotdListeners implements PingListener {

	private CachedServerIcon icon;

	public CachedServerIcon getIcon() {
		try {
			this.icon = Bukkit.loadServerIcon(new File("LystMC.png"));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return icon;
	}

	private String convertTime(Integer i) {
		int minutes = i.intValue() / 60;
		int seconds = i.intValue() % 60;
		String disMinu = (minutes < 10 ? "" : "") + minutes;
		String disSec = (seconds < 10 ? "0" : "") + seconds;
		String formattedTime = disMinu + "m " + disSec + "s";
		if (seconds == 0) {
			return disMinu + "m";
		}
		return formattedTime;
	}
	
	@Override
	public void onPing(PingEvent event) {

		PingReply ping = event.getReply();

		if (Bukkit.hasWhitelist()) {
			ping.setProtocolVersion(0);
			ping.setProtocolName("§4RESERVADO");
		}

		ping.setMaxPlayers(80);
		ping.setOnlinePlayers(Bukkit.getOnlinePlayers().size());
		
		TimersManager construtor = new TimersManager();
		String timer = convertTime(construtor.getTimer());

		if (!Bukkit.hasWhitelist()) {
			
			if (construtor.isIniciando()) {
				ping.setMOTD(StringUtils.makeCenteredMotd(Main.getMain().servername + " §3§l» §7§lINICIANDO" + "\n"
						+ "§aIniciando a partida em: §7" + timer));
			}

			if (construtor.isInvencibilidade()) {
				ping.setMOTD(StringUtils.makeCenteredMotd(Main.getMain().servername + " §3§l» §7§lINVENCIBILIDADE" + "\n"
						+ "§bPartida em Invencibilidade §7" + timer));
			}

			if (construtor.isAndamento()) {
				ping.setMOTD(StringUtils.makeCenteredMotd(Main.getMain().servername + " §3§l» §7§lANDAMENTO") + "\n"
						+ "§cPartida em Andamento  §7" + timer);
			}
			
		}

		List<String> list_server = new ArrayList<>();
		if (Bukkit.hasWhitelist()) {
			list_server.add(Main.getMain().servername + "§3§l» §7§lNETWORK");
			list_server.add("");
			list_server.add("§cEste servidor esta reservado para");
			list_server.add("§ceventos, aguarde ou tente mais tarde");
			list_server.add("");
			list_server.add("§cAcesse §f@LystMC§c para informações.");
		} else {
			list_server.add(Main.getMain().servername + "§3§l» §7§lNETWORK");
			list_server.add("");
			list_server.add("§eVersões disponiveis §7[1.7x/1.8x]");
			list_server.add("§eServidores de Hardcore Games.");
			list_server.add("");
			list_server.add("§cAcesse §f@LystMC§c para informações.");

			if (Bukkit.getOnlinePlayers().size() > 1) {
				list_server.add("");
				if (construtor.isIniciando()) {
					list_server.add("§eEste servidor esta iniciando!");
				}

				if (construtor.isInvencibilidade()) {
					list_server.add("§eEste servidor esta em invencibilidade!");
				}

				if (construtor.isAndamento()) {
					list_server.add("§eEste servidor esta em andamento!");
				}

				if (construtor.isFinalizando()) {
					list_server.add("§eEste servidor esta sendo reiniciado!");
				}
			}
		}

		ping.setPlayerSample(list_server);
		ping.setIcon(getIcon());
	}
}
