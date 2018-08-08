package me.virustotal.dynamicgui.world;

import java.io.Serializable;

public abstract class LocationWrapper<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3610165660936041660L;
	
	private int x;
	private int y;
	private int z;
	private String worldName;
	public LocationWrapper(int x, int y, int z, String worldName)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.worldName = worldName;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	public int getZ()
	{
		return this.z;
	}
	
	public String getWorldName()
	{
		return this.worldName;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj == null)
			return false;
		if(!(obj instanceof LocationWrapper))
			return false;
		
		LocationWrapper<?> other = (LocationWrapper<?>) obj;
		if(other.getX() != this.getX())
			return false;
		if(other.getY() != this.getY())
			return false;
		if(other.getZ() != this.getZ());
		
		return other.getWorldName().equals(this.getWorldName());
	}
}