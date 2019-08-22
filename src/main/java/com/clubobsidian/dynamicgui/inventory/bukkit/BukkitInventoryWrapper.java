/*
   Copyright 2019 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.clubobsidian.dynamicgui.inventory.bukkit;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.util.ReflectionUtil;
import com.clubobsidian.dynamicgui.util.bukkit.BukkitReflectionUtil;

public class BukkitInventoryWrapper<T extends Inventory> extends InventoryWrapper<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 954426075975347755L;

	private static Class<?> nmsPlayerClass;
	private static Class<?> packetPlayOutOpenWindowClass;
	
	private static Method getHandle;
	private static Method sendPacket;
	
	private static Field playerConnection;
	private static Field containerCounter;
	
	private static Constructor<?> packetConstructor;
	private static Constructor<?> chatMessageConstructor;
	
	private final Object[] extraChatComponents = new Object[0];
	
	public BukkitInventoryWrapper(T inventory) 
	{
		super(inventory);
	}

	@Override
	public ItemStackWrapper<ItemStack> getItem(int index) 
	{
		return new BukkitItemStackWrapper<ItemStack>(this.getInventory().getItem(index));
	}

	@Override
	public void setItem(int index, ItemStackWrapper<?> itemStackWrapper) 
	{
		this.getInventory().setItem(index, (ItemStack) itemStackWrapper.getItemStack()); 
	}
	
	@Override
	public int getSize() 
	{
		return this.getInventory().getSize();
	}
	
	@Override
	public int getContentSize() 
	{
		int contentSize = 0;
		for(ItemStack item : this.getInventory().getContents())
		{
			if(item != null && item.getType() != Material.AIR)
			{
				contentSize += 1;
			}
		}
		return contentSize;
	}

	@Override
	public void setTitle(PlayerWrapper<?> playerWrapper, String title) 
	{	
		if(packetPlayOutOpenWindowClass == null)
		{
			packetPlayOutOpenWindowClass = BukkitReflectionUtil.getNMSClass("PacketPlayOutOpenWindow");
		}
		
		if(packetConstructor == null)
		{
			Class<?> iChatBaseComponentClass = BukkitReflectionUtil.getNMSClass("IChatBaseComponent");
			try 
			{
				packetConstructor = packetPlayOutOpenWindowClass.getDeclaredConstructor(int.class, String.class, iChatBaseComponentClass, int.class);
				packetConstructor.setAccessible(true);
			} 
			catch (NoSuchMethodException | SecurityException e) 
			{
				e.printStackTrace();
			}
		}
		
		if(chatMessageConstructor == null)
		{
			Class<?> chatMessageClass = BukkitReflectionUtil.getNMSClass("ChatMessage");
			chatMessageConstructor = chatMessageClass.getDeclaredConstructors()[0];
		}
		
		if(getHandle == null)
		{
			Class<?> craftPlayer = BukkitReflectionUtil.getCraftClass("entity", "CraftPlayer");
			getHandle = ReflectionUtil.getMethod(craftPlayer, "getHandle");
		}
		
		if(nmsPlayerClass == null)
		{
			nmsPlayerClass = BukkitReflectionUtil.getNMSClass("EntityPlayer");
		}
		
		if(playerConnection == null)
		{
			playerConnection = ReflectionUtil.getFieldByName(nmsPlayerClass, "playerConnection");
		}
		
		if(sendPacket == null)
		{
			Class<?> playerConnectionClass = BukkitReflectionUtil.getNMSClass("PlayerConnection");
			Class<?> packetClass = BukkitReflectionUtil.getNMSClass("Packet");
			sendPacket = ReflectionUtil.getMethod(playerConnectionClass, "sendPacket", packetClass);
		}
		
		if(containerCounter == null)
		{
			containerCounter = ReflectionUtil.getFieldByName(nmsPlayerClass, "containerCounter");
		}
		
		try 
		{
			Object player = playerWrapper.getPlayer();
			Object nmsPlayer = getHandle.invoke(player);
			
			String inventoryType = "minecraft:" + this.getInventory().getType().toString().toLowerCase();
			int size = this.getInventory().getSize();
			int containerId = containerCounter.getInt(nmsPlayer);
			Object chatComponent = chatMessageConstructor.newInstance(title, extraChatComponents);
			Object packet = packetConstructor.newInstance(containerId, inventoryType, chatComponent, size);
			
			if(playerWrapper.getOpenInventoryWrapper().getInventory() != null)
			{
				Object playerCon = playerConnection.get(nmsPlayer);
				sendPacket.invoke(playerCon, packet);
			}
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) 
		{
			e.printStackTrace();
		}
	}
}