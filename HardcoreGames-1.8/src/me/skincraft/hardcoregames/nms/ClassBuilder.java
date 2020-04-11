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

package me.skincraft.hardcoregames.nms;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

/**
 *
 * © Copyright 2015 inventivetalent
 *
 * @author inventivetalent
 *
 */
public abstract class ClassBuilder {

	public static Object buildWitherSpawnPacket(int id, Location loc, Object dataWatcher) throws Exception {
		Object packet = NMSClass.PacketPlayOutSpawnEntityLiving.newInstance();
		AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("a")).set(packet, id);
		AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("b")).set(packet, 64);// TODO: Find correct entity type id
		AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("c")).set(packet, (int) loc.getX());
		AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("d")).set(packet, MathUtil.floor(loc.getY() * 32D));
		AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("e")).set(packet, (int) loc.getZ());

		AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("i")).set(packet, (byte) MathUtil.d(loc.getYaw() * 256F / 360F));
		AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("j")).set(packet, (byte) MathUtil.d(loc.getPitch() * 256F / 360F));
		AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("k")).set(packet, (byte) MathUtil.d(loc.getPitch() * 256F / 360F));
		AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("l")).set(packet, dataWatcher);

		return packet;
	}

	public static Object buildWitherSkullSpawnPacket(Object skull) throws Exception {
		@SuppressWarnings("deprecation")
		Object spawnPacketSkull = NMSClass.PacketPlayOutSpawnEntity.getConstructor(NMSClass.Entity, int.class).newInstance(skull, EntityType.WITHER_SKULL.getTypeId());
		AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntity.getDeclaredField("j")).set(spawnPacketSkull, 66);

		return spawnPacketSkull;
	}

	public static Object buildSkullMetaPacket(int id, Object dataWatcher) throws Exception {
		setDataWatcherValue(dataWatcher, 0, (byte) 32);
		Object packet = NMSClass.PacketPlayOutEntityMetadata.getConstructor(int.class, NMSClass.DataWatcher, boolean.class).newInstance(id, dataWatcher, true);

		return packet;
	}

	public static Object buildNameMetadataPacket(int id, Object dataWatcher, int nameIndex, int visibilityIndex, String name) throws Exception {
		dataWatcher = setDataWatcherValue(dataWatcher, nameIndex, name != null ? name : "");// Pass an empty string to avoid exceptions
		dataWatcher = setDataWatcherValue(dataWatcher, visibilityIndex, (byte) (name != null && !name.isEmpty() ? 1 : 0));
		Object metaPacket = NMSClass.PacketPlayOutEntityMetadata.getConstructor(int.class, NMSClass.DataWatcher, boolean.class).newInstance(id, dataWatcher, true);

		return metaPacket;
	}

	public static Object updateEntityLocation(Object entity, Location loc) throws Exception {
		NMSClass.Entity.getDeclaredField("locX").set(entity, loc.getX());
		NMSClass.Entity.getDeclaredField("locY").set(entity, loc.getY());
		NMSClass.Entity.getDeclaredField("locZ").set(entity, loc.getZ());
		return entity;
	}

	public static Object buildDataWatcher(@Nullable Object entity) throws Exception {
		Object dataWatcher = NMSClass.DataWatcher.getConstructor(NMSClass.Entity).newInstance(entity);
		return dataWatcher;
	}

	public static Object buildWatchableObject(int index, Object value) throws Exception {
		return buildWatchableObject(getDataWatcherValueType(value), index, value);
	}

	public static Object buildWatchableObject(int type, int index, Object value) throws Exception {
		return NMSClass.WatchableObject.getConstructor(int.class, int.class, Object.class).newInstance(type, index, value);
	}

	public static Object setDataWatcherValue(Object dataWatcher, int index, Object value) throws Exception {
		int type = getDataWatcherValueType(value);

		Object map = AccessUtil.setAccessible(NMSClass.DataWatcher.getDeclaredField("dataValues")).get(dataWatcher);
		NMUClass.gnu_trove_map_hash_TIntObjectHashMap.getDeclaredMethod("put", int.class, Object.class).invoke(map, index, buildWatchableObject(type, index, value));

		return dataWatcher;
	}

	public static Object getDataWatcherValue(Object dataWatcher, int index) throws Exception {
		Object map = AccessUtil.setAccessible(NMSClass.DataWatcher.getDeclaredField("dataValues")).get(dataWatcher);
		Object value = NMUClass.gnu_trove_map_hash_TIntObjectHashMap.getDeclaredMethod("get", int.class).invoke(map, index);

		return value;
	}

	public static int getWatchableObjectIndex(Object object) throws Exception {
		int index = AccessUtil.setAccessible(NMSClass.WatchableObject.getDeclaredField("b")).getInt(object);
		return index;
	}

	public static int getWatchableObjectType(Object object) throws Exception {
		int type = AccessUtil.setAccessible(NMSClass.WatchableObject.getDeclaredField("a")).getInt(object);
		return type;
	}

	public static Object getWatchableObjectValue(Object object) throws Exception {
		Object value = AccessUtil.setAccessible(NMSClass.WatchableObject.getDeclaredField("c")).get(object);
		return value;
	}

	public static int getDataWatcherValueType(Object value) {
		int type = 0;
		if (value instanceof Number) {
			if (value instanceof Byte) {
				type = 0;
			} else if (value instanceof Short) {
				type = 1;
			} else if (value instanceof Integer) {
				type = 2;
			} else if (value instanceof Float) {
				type = 3;
			}
		} else if (value instanceof String) {
			type = 4;
		} else if (value != null && value.getClass().equals(NMSClass.ItemStack)) {
			type = 5;
		} else if (value != null && (value.getClass().equals(NMSClass.ChunkCoordinates) || value.getClass().equals(NMSClass.BlockPosition))) {
			type = 6;
		} else if (value != null && value.getClass().equals(NMSClass.Vector3f)) {
			type = 7;
		}

		return type;
	}

	public static Object buildArmorStandSpawnPacket(Object armorStand) throws Exception {
		Object spawnPacket = NMSClass.PacketPlayOutSpawnEntityLiving.getConstructor(NMSClass.EntityLiving).newInstance(armorStand);
		AccessUtil.setAccessible(NMSClass.PacketPlayOutSpawnEntityLiving.getDeclaredField("b")).setInt(spawnPacket, 30);

		return spawnPacket;
	}

	public static Object buildTeleportPacket(int id, Location loc, boolean onGround, boolean heightCorrection) throws Exception {
		Object packet = NMSClass.PacketPlayOutEntityTeleport.newInstance();
		AccessUtil.setAccessible(NMSClass.PacketPlayOutEntityTeleport.getDeclaredField("a")).set(packet, id);
		AccessUtil.setAccessible(NMSClass.PacketPlayOutEntityTeleport.getDeclaredField("b")).set(packet, (int) (loc.getX() * 32D));
		AccessUtil.setAccessible(NMSClass.PacketPlayOutEntityTeleport.getDeclaredField("c")).set(packet, (int) (loc.getY() * 32D));
		AccessUtil.setAccessible(NMSClass.PacketPlayOutEntityTeleport.getDeclaredField("d")).set(packet, (int) (loc.getZ() * 32D));
		AccessUtil.setAccessible(NMSClass.PacketPlayOutEntityTeleport.getDeclaredField("e")).set(packet, (byte) (int) (loc.getYaw() * 256F / 360F));
		AccessUtil.setAccessible(NMSClass.PacketPlayOutEntityTeleport.getDeclaredField("f")).set(packet, (byte) (int) (loc.getPitch() * 256F / 360F));

		return packet;
	}
}
