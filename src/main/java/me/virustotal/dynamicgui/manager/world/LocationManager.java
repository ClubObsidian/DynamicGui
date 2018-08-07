package me.virustotal.dynamicgui.manager.world;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.manager.world.bukkit.BukkitLocationManager;
import me.virustotal.dynamicgui.manager.world.sponge.SpongeLocationManager;
import me.virustotal.dynamicgui.server.ServerType;
import me.virustotal.dynamicgui.world.LocationWrapper;

public abstract class LocationManager {

	private static LocationManager instance;
	
	public static LocationManager get()
	{
		if(instance == null)
		{
			if(ServerType.SPIGOT == DynamicGUI.get().getServer().getType())
			{
				instance = new BukkitLocationManager();
			}
			else if(ServerType.SPONGE == DynamicGUI.get().getServer().getType())
			{
				instance = new SpongeLocationManager();
			}
		}
		return instance;
	}
	
	public Object toLocation(String location)
	{
		if(location == null || location.length() == 0)
			return null;
		if(!location.contains(","))
			return null;
		String[] split = location.split(",");
		if(split.length != 4)
			return null;
		
		try
		{
			int x = Integer.parseInt(split[0]);
			int y = Integer.parseInt(split[1]);
			int z = Integer.parseInt(split[2]);
			String world = split[3];
			return this.toLocation(world, x, y, z);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	public abstract Object toLocation(String world, int x, int y, int z);
	
	public abstract LocationWrapper<?> toLocationWrapper(Object location);
	
	public LocationWrapper<?> toLocationWrapper(String location)
	{
		Object obj = this.toLocation(location);
		return this.toLocationWrapper(obj);
	}
	
	public LocationWrapper<?> toLocationWrapper(String world, int x, int y, int z)
	{
		Object location = this.toLocation(world, x, y, z);
		return this.toLocationWrapper(location);
	}
}