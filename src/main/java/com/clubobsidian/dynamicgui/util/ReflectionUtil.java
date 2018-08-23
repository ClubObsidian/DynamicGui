package com.clubobsidian.dynamicgui.util;

public class ReflectionUtil {

	public static Class<?> getMinecraftClass(String className) throws ClassNotFoundException
	{
		return Class.forName("net.minecraft.server." + VersionUtil.getVersion() + "." + className);
	}
	
	public static Class<?> getCraftClass(String packageName, String className) throws ClassNotFoundException
	{
		return Class.forName("org.bukkit.craftbukkit." + VersionUtil.getVersion() + "." + packageName + "." + className);
	}
	
	public static Class<?> getCraftClass(String className) throws ClassNotFoundException
	{
		return Class.forName("org.bukkit.craftbukkit." + VersionUtil.getVersion() + "." + className);
	}
}
