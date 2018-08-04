package me.virustotal.dynamicgui.world;

import java.io.Serializable;

public abstract class LocationWrapper<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3610165660936041660L;

	public abstract int getX();
	public abstract int getY();
	public abstract int getZ();
	public abstract String getWorldName();
	
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
