package com.clubobsidian.dynamicgui.util.bukkit;

public final class BukkitReflectionUtil {

	private BukkitReflectionUtil() {}
	
	public static Class<?> getCraftClass(String className)
	{
		String version = VersionUtil.getVersion();
		try 
		{
			Class<?> clazz = Class.forName("org.bukkit.craftbukkit." + version + "." + className);
			return clazz;
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static Class<?> getNMSClass(String className)
	{
		String version = VersionUtil.getVersion();
		try 
		{
			Class<?> clazz = Class.forName("net.minecraft.server." + version + "." + className);
			return clazz;
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static Class<?> getCraftClass(String packageName, String className)
	{
		String version = VersionUtil.getVersion();
		try 
		{
			Class<?> clazz = Class.forName("org.bukkit.craftbukkit." + version + "." + packageName + "." + className);
			return clazz;
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}