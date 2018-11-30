package com.clubobsidian.dynamicgui.util.bukkit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.inventory.ItemStack;

import com.clubobsidian.dynamicgui.DynamicGui;

public final class BukkitNBTUtil {

	private BukkitNBTUtil() {}

	private static Method parse;
	private static Method asNMSCopy;
	private static Method setTag;
	private static Method asBukkitCopy;
	
	public static Object parse(String nbtStr)
	{
		if(parse == null)
		{
			String version = VersionUtil.getVersion();
			DynamicGui.get().getLogger().info("Version: " + version);
			try 
			{
				String parserClassName = "net.minecraft.server." + version + ".MojangsonParser";
				DynamicGui.get().getLogger().info("Parser class: " + parserClassName);
				Class<?> mojangParser = Class.forName(parserClassName);
				parse = mojangParser.getDeclaredMethod("parse", String.class);
				parse.setAccessible(true);
			} 
			catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) 
			{
				e.printStackTrace();
			} 
		}
		try
		{
			return parse.invoke(null, nbtStr);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static ItemStack setTag(ItemStack itemStack, String nbt)
	{
		String version = VersionUtil.getVersion();
		DynamicGui.get().getLogger().info("Version: " + version);
		try 
		{
			if(asNMSCopy == null)
			{
				String craftItemStackClassName = "org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack";
				Class<?> craftItemStackClass = Class.forName(craftItemStackClassName);
				asNMSCopy = craftItemStackClass.getDeclaredMethod("asNMSCopy", ItemStack.class);
				asNMSCopy.setAccessible(true);
			}
			
			if(setTag == null)
			{
				String itemStackClassName = "net.minecraft.server." + version + ".ItemStack";
				Class<?> nmsItemStackClass = Class.forName(itemStackClassName);
				
				String nbtTagCompoundClassName = "net.minecraft.server." + version + ".NBTTagCompound";
				Class<?> nbtTagCompoundClass = Class.forName(nbtTagCompoundClassName);
				setTag = nmsItemStackClass.getDeclaredMethod("setTag", nbtTagCompoundClass);
				setTag.setAccessible(true);
			}
			
			if(asBukkitCopy == null)
			{
				String itemStackClassName = "net.minecraft.server." + version + ".ItemStack";
				Class<?> nmsItemStackClass = Class.forName(itemStackClassName);
				String craftItemStackClassName = "org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack";
				Class<?> craftItemStackClass = Class.forName(craftItemStackClassName);
				asBukkitCopy = craftItemStackClass.getDeclaredMethod("asBukkitCopy", nmsItemStackClass);
				asBukkitCopy.setAccessible(true);
			}
			
			Object nmsItemStack = asNMSCopy.invoke(null, itemStack);
			Object nbtCompound = BukkitNBTUtil.parse(nbt);
			setTag.invoke(nmsItemStack, nbtCompound);
			ItemStack bukkitItemStack = (ItemStack) asBukkitCopy.invoke(null, nmsItemStack);
			return bukkitItemStack;
		} 
		catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			e.printStackTrace();
		} 
		return null;
	}
}