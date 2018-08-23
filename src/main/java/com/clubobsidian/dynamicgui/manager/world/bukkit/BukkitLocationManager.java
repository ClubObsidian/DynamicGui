package com.clubobsidian.dynamicgui.manager.world.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.clubobsidian.dynamicgui.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.world.LocationWrapper;
import com.clubobsidian.dynamicgui.world.bukkit.BukkitLocationWrapper;

public class BukkitLocationManager extends LocationManager {

	@Override
	public Object toLocation(String world, int x, int y, int z) 
	{
		World bukkitWorld = Bukkit.getServer().getWorld(world);
		if(bukkitWorld == null)
		{
			return null;
		}
		return new Location(bukkitWorld, x, y, z);
	}

	@Override
	public LocationWrapper<?> toLocationWrapper(Object location) 
	{
		return new BukkitLocationWrapper<Location>((Location) location);
	}
}