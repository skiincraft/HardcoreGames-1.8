/*
 * Copyright 2015 Marvin Schäfer (inventivetalent). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */

package me.skincraft.hardcoregames.bossbar;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.skincraft.hardcoregames.Main;


/**
 * © Copyright 2015 inventivetalent
 *
 * @author inventivetalent
 */
public class BossBarEvents implements Listener {

	protected static BossBarEvents instance;


	

	@EventHandler
	public void onPluginEnable(PluginEnableEvent e) {
		
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent e) {
		BossBarAPI.removeBar(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onKick(PlayerKickEvent e) {
		BossBarAPI.removeBar(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onTeleport(PlayerTeleportEvent e) {
		this.handlePlayerTeleport(e.getPlayer(), e.getFrom(), e.getTo());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onRespawn(PlayerRespawnEvent e) {
		this.handlePlayerTeleport(e.getPlayer(), e.getPlayer().getLocation(), e.getRespawnLocation());
	}

	protected void handlePlayerTeleport(Player player, Location from, Location to) {
		if (!BossBarAPI.hasBar(player)) { return; }
		final BossBar bar = BossBarAPI.getBossBar(player);
		bar.setVisible(false);
		new BukkitRunnable() {

			@Override
			public void run() {
				bar.setVisible(true);
			}
		}.runTaskLater(Main.getMain(), 2);
	}

	@EventHandler
	public void onMove(final PlayerMoveEvent e) {
		final BossBar bar = BossBarAPI.getBossBar(e.getPlayer());
		if (bar != null) {
			new BukkitRunnable() {

				@Override
				public void run() {
					if (!e.getPlayer().isOnline()) { return; }
					bar.updateMovement();
				}
			}.runTaskLater(Main.getMain(), 0);
		}
	}

}
