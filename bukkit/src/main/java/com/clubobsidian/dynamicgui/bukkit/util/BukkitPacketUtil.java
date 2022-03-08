/*
 *    Copyright 2021 Club Obsidian and contributors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.clubobsidian.dynamicgui.bukkit.util;

import com.clubobsidian.dynamicgui.core.util.ReflectionUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class BukkitPacketUtil {

    private BukkitPacketUtil() {
    }

    private static final String ITEM_STACK_CLASS_NAME = getItemStackClass();
    private static final String ENTITY_PLAYER_CLASS_NAME = getEntityPlayerClassName();
    private static final String ENTITY_HUMAN_CLASS_NAME = getEntityHumanClassName();
    private static final String PLAYER_CONNECTION_CLASS_NAME = getPlayerConnectionClassName();
    private static final String NETWORK_MANAGER_CLASS_NAME = getNetworkManagerClassName();
    private static final String CONTAINER_CLASS_NAME = getContainerClassName();
    private static final String PACKET_CLASS_NAME = getPacketClassName();
    private static final String PACKET_PLAY_OUT_SET_SLOT_CLASS_NAME = getPacketPlayOutSetClassName();

    private static final Constructor<?> PACKET_PLAY_OUT_SET_SLOT_CONSTRUCTOR = getPacketPlayOutSetSlotConstructor();
    private static final int SET_SLOT_CONSTRUCTOR_LENGTH = PACKET_PLAY_OUT_SET_SLOT_CONSTRUCTOR.getParameterCount();

    private static Class<?> craftItemClass;
    private static Class<?> craftPlayerClass;
    private static Class<?> nmsPlayerClass;
    private static Class<?> nmsHumanClass;

    private static Field itemStackHandle;
    private static Field playerConnection;
    private static Field networkManager;
    private static Field windowIdField;
    private static Field activeContainer;
    private static Field stateIdField;

    private static Method playerHandle;
    private static Method sendPacket;

    public static void sendPacket(Player player, Object packet) {
        String version = VersionUtil.getVersion();
        try {
            if (craftPlayerClass == null) {
                craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            }

            if (playerHandle == null) {
                playerHandle = craftPlayerClass.getDeclaredMethod("getHandle");
            }

            if (nmsPlayerClass == null) {
                nmsPlayerClass = Class.forName(ENTITY_PLAYER_CLASS_NAME);
            }

            if (playerConnection == null) {
                Class<?> conClazz = Class.forName(PLAYER_CONNECTION_CLASS_NAME);
                playerConnection = ReflectionUtil.getFieldByType(nmsPlayerClass, conClazz);
            }

            if (networkManager == null) {
                Class<?> playerConnection = Class.forName(PLAYER_CONNECTION_CLASS_NAME);
                Class<?> networkManagerClass = Class.forName(NETWORK_MANAGER_CLASS_NAME);
                networkManager = ReflectionUtil.getFieldByType(playerConnection, networkManagerClass);
            }

            if (sendPacket == null) {
                Class<?> networkManagerClass = Class.forName(NETWORK_MANAGER_CLASS_NAME);
                Class<?> packetClass = Class.forName(PACKET_CLASS_NAME);
                sendPacket = networkManagerClass.getDeclaredMethod("sendPacket", packetClass);
            }

            Object nmsPlayer = playerHandle.invoke(player);
            Object con = playerConnection.get(nmsPlayer);
            Object manager = networkManager.get(con);

            sendPacket.invoke(manager, packet);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void sendSlotPacket(int index, Player player, ItemStack itemStack) {
        String version = VersionUtil.getVersion();
        try {
            if (craftPlayerClass == null) {
                craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            }

            if (craftItemClass == null) {
                craftItemClass = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack");
            }

            if (itemStackHandle == null) {
                itemStackHandle = craftItemClass.getDeclaredField("handle");
                itemStackHandle.setAccessible(true);
            }

            if (playerHandle == null) {
                playerHandle = craftPlayerClass.getDeclaredMethod("getHandle");
            }

            if (nmsPlayerClass == null) {
                nmsPlayerClass = Class.forName(ENTITY_PLAYER_CLASS_NAME);
            }

            if (nmsHumanClass == null) {
                nmsHumanClass = Class.forName(ENTITY_HUMAN_CLASS_NAME);
            }

            if (windowIdField == null) {
                Class<?> containerClass = Class.forName(CONTAINER_CLASS_NAME);
                windowIdField = ReflectionUtil.getDeclaredField(containerClass, "windowId", "j");
            }

            if (activeContainer == null) {
                Class<?> containerClass = Class.forName(CONTAINER_CLASS_NAME);
                activeContainer = ReflectionUtil.getFieldByType(nmsHumanClass, containerClass);
            }


            Object nmsPlayer = playerHandle.invoke(player);
            Object container = activeContainer.get(nmsPlayer);

            Object nmsItemStack = itemStackHandle.get(itemStack);
            int windowId = windowIdField.getInt(container); //container
            //stateId is new in 1.17
            //int windowID, int stateID, int slot, ItemStack itemstack
            Object packet;
            if (SET_SLOT_CONSTRUCTOR_LENGTH == 3) {
                packet = PACKET_PLAY_OUT_SET_SLOT_CONSTRUCTOR.newInstance(windowId, index, nmsItemStack);
            } else {
                if (stateIdField == null) {
                    Class<?> containerClass = Class.forName(CONTAINER_CLASS_NAME);
                    stateIdField = ReflectionUtil.getDeclaredField(containerClass, "q");
                }
                int stateId = stateIdField.getInt(container);
                packet = PACKET_PLAY_OUT_SET_SLOT_CONSTRUCTOR.newInstance(windowId, stateId, index, nmsItemStack);
            }
            sendPacket(player, packet);
        } catch (ClassNotFoundException | NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static Constructor<?> getPacketPlayOutSetSlotConstructor() {
        try {
            Class<?> packetClass = Class.forName(PACKET_PLAY_OUT_SET_SLOT_CLASS_NAME);
            for (Constructor<?> con : packetClass.getDeclaredConstructors()) {
                if (con.getParameterTypes().length > 0 && con.getParameterTypes()[0].equals(int.class)) {
                    return con;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getItemStackClass() {
        try {
            String className = "net.minecraft.world.item.ItemStack";
            Class.forName(className);
            return className;
        } catch (ClassNotFoundException ex) {
            String version = VersionUtil.getVersion();
            String className = "net.minecraft.server." + version + ".ItemStack";
            try {
                Class.forName(className);
                return className;
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
    }

    private static String getEntityPlayerClassName() {
        try {
            String className = "net.minecraft.server.level.EntityPlayer";
            Class.forName(className);
            return className;
        } catch (ClassNotFoundException ex) {
            String version = VersionUtil.getVersion();
            String className = "net.minecraft.server." + version + ".EntityPlayer";
            try {
                Class.forName(className);
                return className;
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
    }

    private static String getEntityHumanClassName() {
        try {
            String className = "net.minecraft.world.entity.player.EntityHuman";
            Class.forName(className);
            return className;
        } catch (ClassNotFoundException ex) {
            String version = VersionUtil.getVersion();
            String className = "net.minecraft.server." + version + ".EntityHuman";
            try {
                Class.forName(className);
                return className;
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
    }

    private static String getPlayerConnectionClassName() {
        try {
            String className = "net.minecraft.server.network.PlayerConnection";
            Class.forName(className);
            return className;
        } catch (ClassNotFoundException ex) {
            String version = VersionUtil.getVersion();
            String className = "net.minecraft.server." + version + ".PlayerConnection";
            try {
                Class.forName(className);
                return className;
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
    }

    private static String getNetworkManagerClassName() {
        try {
            String className = "net.minecraft.network.NetworkManager";
            Class.forName(className);
            return className;
        } catch (ClassNotFoundException ex) {
            String version = VersionUtil.getVersion();
            String className = "net.minecraft.server." + version + ".NetworkManager";
            try {
                Class.forName(className);
                return className;
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
    }

    private static String getContainerClassName() {
        try {
            String className = "net.minecraft.world.inventory.Container";
            Class.forName(className);
            return className;
        } catch (ClassNotFoundException ex) {
            String version = VersionUtil.getVersion();
            String className = "net.minecraft.server." + version + ".Container";
            try {
                Class.forName(className);
                return className;
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
    }

    private static String getPacketClassName() {
        try {
            String className = "net.minecraft.network.protocol.Packet";
            Class.forName(className);
            return className;
        } catch (ClassNotFoundException ex) {
            String version = VersionUtil.getVersion();
            String className = "net.minecraft.server." + version + ".Packet";
            try {
                Class.forName(className);
                return className;
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
    }

    private static String getPacketPlayOutSetClassName() {
        try {
            String className = "net.minecraft.network.protocol.game.PacketPlayOutSetSlot";
            Class.forName(className);
            return className;
        } catch (ClassNotFoundException ex) {
            String version = VersionUtil.getVersion();
            String className = "net.minecraft.server." + version + ".PacketPlayOutSetSlot";
            try {
                Class.forName(className);
                return className;
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
    }
}