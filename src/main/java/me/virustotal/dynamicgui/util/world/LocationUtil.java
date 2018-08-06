package me.virustotal.dynamicgui.util.world;

import me.virustotal.dynamicgui.server.ServerType;
import me.virustotal.dynamicgui.world.LocationWrapper;

public final class LocationUtil {

	private LocationUtil() {}
	
	public static Object toLocation(String location)
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
			return LocationUtil.toLocation(world, x, y, z);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	
	
	public static Object toLocation(String world, int x, int y, int z)
	{
		if(ServerType.SPIGOT == ServerType.get())
		{
			
		}
		else if(ServerType.SPONGE == ServerType.get())
		{
			
		}
		return null;
	}
	
	public static LocationWrapper<?> toLocationWrapper(Object location)
	{
		
	}	
	
	public static LocationWrapper<?> toLocationWrapper(String location)
	{
		Object obj = toLocation(location);
		return LocationUtil.toLocationWrapper(obj);
	}
	
	public static LocationWrapper<?> toLocationWrapper(String world, int x, int y, int z)
	{
		Object location = LocationUtil.toLocation(world, x, y, z);
		return LocationUtil.toLocationWrapper(location);
	}
}