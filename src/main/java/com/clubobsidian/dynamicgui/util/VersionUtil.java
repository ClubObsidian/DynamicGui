package com.clubobsidian.dynamicgui.util;

import org.bukkit.Bukkit;

public class VersionUtil {

	public synchronized static String getVersion() 
	{
		String version = "";
		if(Bukkit.getServer() == null)
		{
			return null;
		}
		String name = Bukkit.getServer().getClass().getPackage().getName();
		version = name.substring(name.lastIndexOf('.') + 1);
		return version;
	}
}