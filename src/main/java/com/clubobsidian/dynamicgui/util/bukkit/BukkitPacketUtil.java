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
package com.clubobsidian.dynamicgui.util.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class BukkitPacketUtil {

    private BukkitPacketUtil() {
    }

    private static Class<?> craftItemClass;
    private static Class<?> craftPlayerClass;
    private static Class<?> nmsPlayerClass;
    private static Class<?> nmsHumanClass;

    private static Constructor<?> packetPlayOutSetSlotConstructor;

    private static Field itemStackHandle;
    private static Field playerConnection;
    private static Field networkManager;
    private static Field windowIdField;
    private static Field activeContainer;

    private static Method playerHandle;
    private static Method sendPacket;

    public static void sendPacket(Player player, Object packet) {
        String version = VersionUtil.getVersion();
        try {
            if(craftPlayerClass == null) {
                craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            }

            if(playerHandle == null) {
                playerHandle = craftPlayerClass.getDeclaredMethod("getHandle");
            }

            if(nmsPlayerClass == null) {
                nmsPlayerClass = Class.forName("net.minecraft.server." + version + ".EntityPlayer");
            }

            if(playerConnection == null) {
                playerConnection = nmsPlayerClass.getDeclaredField("playerConnection");
                playerConnection.setAccessible(true);
            }

            if(networkManager == null) {
                Class<?> playerConnection = Class.forName("net.minecraft.server." + version + ".PlayerConnection");
                networkManager = playerConnection.getDeclaredField("networkManager");
                networkManager.setAccessible(true);
            }

            if(sendPacket == null) {
                Class<?> networkManagerlass = Class.forName("net.minecraft.server." + version + ".NetworkManager");
                Class<?> packetClass = Class.forName("net.minecraft.server." + version + ".Packet");
                sendPacket = networkManagerlass.getDeclaredMethod("sendPacket", packetClass);
            }


            Object nmsPlayer = playerHandle.invoke(player);
            Object con = playerConnection.get(nmsPlayer);
            Object manager = networkManager.get(con);

            sendPacket.invoke(manager, packet);
        } catch(ClassNotFoundException | NoSuchMethodException | SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void sendSlotPacket(int index, Player player, ItemStack itemStack) {
        String version = VersionUtil.getVersion();
        try {
            if(craftPlayerClass == null) {
                craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            }

            if(craftItemClass == null) {
                craftItemClass = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack");
            }

            if(itemStackHandle == null) {
                itemStackHandle = craftItemClass.getDeclaredField("handle");
                itemStackHandle.setAccessible(true);
            }

            if(playerHandle == null) {
                playerHandle = craftPlayerClass.getDeclaredMethod("getHandle");
            }

            if(nmsPlayerClass == null) {
                nmsPlayerClass = Class.forName("net.minecraft.server." + version + ".EntityPlayer");
            }

            if(nmsHumanClass == null) {
                nmsHumanClass = Class.forName("net.minecraft.server." + version + ".EntityHuman");
            }

            if(packetPlayOutSetSlotConstructor == null) {
                Class<?> packetClass = Class.forName("net.minecraft.server." + version + ".PacketPlayOutSetSlot");
                Class<?> nmsItemClass = Class.forName("net.minecraft.server." + version + ".ItemStack");
                packetPlayOutSetSlotConstructor = packetClass.getDeclaredConstructor(int.class, int.class, nmsItemClass);
            }

            if(windowIdField == null) {
                Class<?> containerClass = Class.forName("net.minecraft.server." + version + ".Container");
                windowIdField = containerClass.getDeclaredField("windowId");
                windowIdField.setAccessible(true);
            }

            if(activeContainer == null) {
                activeContainer = nmsHumanClass.getDeclaredField("activeContainer");
            }

            Object nmsPlayer = playerHandle.invoke(player);
            Object container = activeContainer.get(nmsPlayer);

            Object nmsItemStack = itemStackHandle.get(itemStack);
            int windowId = windowIdField.getInt(container); //container
            Object packet = packetPlayOutSetSlotConstructor.newInstance(windowId, index, nmsItemStack);
            sendPacket(player, packet);
        } catch(ClassNotFoundException | NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}