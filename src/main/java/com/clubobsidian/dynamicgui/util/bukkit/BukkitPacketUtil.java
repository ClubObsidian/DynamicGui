package com.clubobsidian.dynamicgui.util.bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public final class BukkitPacketUtil {

	private BukkitPacketUtil() {}
	
 	private static Class<?> craftItemClass;
 	private static Class<?> craftPlayerClass;
 	private static Class<?> nmsPlayerClass;
 	private static Class<?> craftInventoryViewClass;
 	
 	private static Constructor<?> packetPlayOutSetSlotConstructor;
 	
 	private static Field itemStackHandle;
 	private static Field playerConnection;
 	private static Field networkManager;
 	private static Field container;
 	private static Field windowIdMethod;
 	
 	private static Method playerHandle;
 	private static Method sendPacket;
 	
 	public static void sendPacket(Player player, Object packet)
 	{
 		String version = VersionUtil.getVersion();
 		try 
 		{
 			if(craftPlayerClass == null)
 			{
 				craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
 			}
 			
 			if(playerHandle == null)
 			{
 				playerHandle = craftPlayerClass.getDeclaredMethod("getHandle");
 			}
 			
 			if(nmsPlayerClass == null)
 			{
 				nmsPlayerClass = Class.forName("net.minecraft.server." + version + ".EntityPlayer");
 			}
 			
 			if(playerConnection == null)
 			{
 				playerConnection = nmsPlayerClass.getDeclaredField("playerConnection");
 				playerConnection.setAccessible(true);
 			}
 			
 			if(networkManager == null)
 			{
 				Class<?> playerConnection = Class.forName("net.minecraft.server." + version + ".PlayerConnection");
 				networkManager = playerConnection.getDeclaredField("networkManager");
 				networkManager.setAccessible(true);
 			}
 			
 			if(sendPacket == null)
 			{
 				Class<?> networkManagerlass = Class.forName("net.minecraft.server." + version + ".NetworkManager");
 				Class<?> packetClass = Class.forName("net.minecraft.server." + version + ".Packet");
 				sendPacket = networkManagerlass.getDeclaredMethod("sendPacket", packetClass);
 			}
 			
 			
 			Object nmsPlayer = playerHandle.invoke(player);
 			Object con = playerConnection.get(nmsPlayer);
 			Object manager = networkManager.get(con);
 			
 			sendPacket.invoke(manager, packet);
 		} 
 		catch (ClassNotFoundException | NoSuchMethodException | SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) 
 		{
 			e.printStackTrace();
 		}
 	}

 	public static void sendSlotPacket(int index, Player player, ItemStack itemStack)
 	{
 		String version = VersionUtil.getVersion();
 		try 
 		{
 			if(craftItemClass == null)
 			{
 				craftItemClass = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack"); 
 			}

 			if(itemStackHandle == null)
 			{
 				itemStackHandle = craftItemClass.getDeclaredField("handle");
 				itemStackHandle.setAccessible(true);
 			}
 			
 			if(packetPlayOutSetSlotConstructor == null)
 			{
 				Class<?> packetClass = Class.forName("net.minecraft.server." + version + ".PacketPlayOutSetSlot");
 				Class<?> nmsItemClass = Class.forName("net.minecraft.server." + version + ".ItemStack");
 				packetPlayOutSetSlotConstructor = packetClass.getDeclaredConstructor(int.class, int.class, nmsItemClass);
 			}
 			
 			if(craftInventoryViewClass == null)
 			{
 				craftInventoryViewClass = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftInventoryView");
 			}
 			
 			if(container == null)
 			{
 				container = craftInventoryViewClass.getDeclaredField("container");
 				container.setAccessible(true);
 			}
 			
 			if(windowIdMethod == null)
 			{
 				Class<?> containerClass = Class.forName("net.minecraft.server." + version + ".Container");
 				windowIdMethod = containerClass.getDeclaredField("windowId");
 				windowIdMethod.setAccessible(true);
 			}
 			
 			InventoryView view = player.getOpenInventory();
 			
 			Object nmsItemStack = itemStackHandle.get(itemStack);
 			Object containerFromView = container.get(view);
 			int windowId = windowIdMethod.getInt(containerFromView);
 			
 			Object packet = packetPlayOutSetSlotConstructor.newInstance(windowId, index, nmsItemStack);
 			sendPacket(player, packet);
 		}
 		catch (ClassNotFoundException | NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException e) 
 		{
 			e.printStackTrace();
 		}
 	}

}
