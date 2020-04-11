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

import org.bukkit.entity.Player;

import me.skincraft.hardcoregames.nms.Reflection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BossBarAPI {

	private static final Map<UUID, BossBar> barMap = new ConcurrentHashMap<>();
	
	/**
	 * Sets the boss-bar message for the specified player
	 *
	 * @param player  Receiver of the message
	 * @param message Message content
	 */
	public static void setMessage(Player player, String message) {
		setMessage(player, message, 100);
	}

	/**
	 * Sets the boss-bar message for the specified player
	 *
	 * @param player     Receiver of the message
	 * @param message    Message content
	 * @param percentage Health percentage
	 */
	public static void setMessage(Player player, String message, float percentage) {
		setMessage(player, message, percentage, 0);
	}

	/**
	 * Sets the boss-bar message for the specified player
	 *
	 * @param player     Receiver of the message
	 * @param message    Message content
	 * @param percentage Health percentage
	 * @param timeout    Amount of seconds until the bar is removed
	 */
	public static void setMessage(Player player, String message, float percentage, int timeout) {
		setMessage(player, message, percentage, timeout, 100);
	}

	/**
	 * Sets the boss-bar message for the specified player
	 *
	 * @param player     Receiver of the message
	 * @param message    Message content
	 * @param percentage Health percentage
	 * @param timeout    Amount of seconds until the bar is removed
	 * @param minHealth  minimum health (100 by default)
	 */
	public static void setMessage(Player player, String message, float percentage, int timeout, float minHealth) {
		if (!barMap.containsKey(player.getUniqueId())) {
			barMap.put(player.getUniqueId(), new BossBar(player, message, percentage, timeout, minHealth));
		}
		BossBar bar = barMap.get(player.getUniqueId());
		if (!bar.message.equals(message)) {
			bar.setMessage(message);
		}
		float newHealth = percentage / 100F * bar.getMaxHealth();
		if (bar.health != newHealth) {
			bar.setHealth(newHealth);
		}
		if (!bar.isVisible()) {
			bar.setVisible(true);
		}
	}

	/**
	 * @param player {@link Player}
	 * @return The current message of the player's bar
	 */
	public static String getMessage(Player player) {
		BossBar bar = getBossBar(player);
		if (bar == null) { return null; }
		return bar.getMessage();
	}

	/**
	 * @param player {@link Player} to check
	 * @return <code>true</code> if the player has a bar
	 */
	public static boolean hasBar(@Nonnull Player player) {
		return barMap.containsKey(player.getUniqueId());
	}

	/**
	 * Removes the bar of a player
	 *
	 * @param player Player to remove
	 */
	public static void removeBar(@Nonnull Player player) {
		BossBar bar = getBossBar(player);
		if (bar == null) { return; }
		bar.setVisible(false);
		barMap.remove(player.getUniqueId());
	}

	/**
	 * Changes the displayed health of the bar
	 *
	 * @param player     {@link Player}
	 * @param percentage Health percentage
	 */
	public static void setHealth(Player player, float percentage) {
		BossBar bar = getBossBar(player);
		if (bar == null) { return; }
		bar.setHealth(percentage);
	}

	/**
	 * @param player {@link Player}
	 * @return The health of the player's bar
	 */
	public static float getHealth(@Nonnull Player player) {
		BossBar bar = getBossBar(player);
		if (bar == null) { return -1; }
		return bar.getHealth();
	}

	/**
	 * Get the bar for the specified player
	 *
	 * @param player {@link Player}
	 * @return a {@link BossBar} instance if the player has a bar, <code>null</code> otherwise
	 */
	@Nullable
	public static BossBar getBossBar(@Nonnull Player player) {
		if (player == null) { return null; }
		return barMap.get(player.getUniqueId());
	}

	/**
	 * @return A {@link Collection} of all registered bars
	 */
	public static Collection<BossBar> getBossBars() {
		List<BossBar> list = new ArrayList<>(barMap.values());
		return list;
	}

	protected static void sendPacket(Player p, Object packet) {
		if (p == null || packet == null) { throw new IllegalArgumentException("player and packet cannot be null"); }
		try {
			Object handle = Reflection.getHandle(p);
			Object connection = Reflection.getField(handle.getClass(), "playerConnection").get(handle);
			Reflection.getMethod(connection.getClass(), "sendPacket", Reflection.getNMSClass("Packet")).invoke(connection, new Object[] { packet });
		} catch (Exception e) {
		}
	}

}
