package me.virustotal.dynamicgui.world.bukkit;

import org.bukkit.Location;

import me.virustotal.dynamicgui.world.LocationWrapper;

public class BukkitLocationWrapper<T extends Location> extends LocationWrapper<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3735326070415778100L;

	public BukkitLocationWrapper(T location) 
	{
		super(location);
	}

	@Override
	public int getX() 
	{
		return this.getLocation().getBlockX();
	}

	@Override
	public int getY() 
	{
		return this.getLocation().getBlockY();
	}

	@Override
	public int getZ() 
	{
		return this.getLocation().getBlockZ();
	}

	@Override
	public String getWorldName() 
	{
		return this.getLocation().getWorld().getName();
	}
}