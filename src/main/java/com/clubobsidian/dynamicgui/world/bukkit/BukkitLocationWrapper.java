package com.clubobsidian.dynamicgui.world.bukkit;

import org.bukkit.Location;

import com.clubobsidian.dynamicgui.world.LocationWrapper;

public class BukkitLocationWrapper<T extends Location> extends LocationWrapper<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3735326070415778100L;

	public BukkitLocationWrapper(T location) 
	{
		super(location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getWorld().getName());
	}

}