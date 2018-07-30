package me.virustotal.dynamicgui.objects;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class CLocation implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2502503455465791560L;
	private String location;
	
	public CLocation(String location)
	{
		this.location = location;
	}
	
	public Location toLocation()
	{
		return new Location(Bukkit.getWorld(this.location.split(",")[3]),Integer.parseInt(this.location.split(",")[0]),Integer.parseInt(this.location.split(",")[1]),Integer.parseInt(this.location.split(",")[2]));
	}
}
