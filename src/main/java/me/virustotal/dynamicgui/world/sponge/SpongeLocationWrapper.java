package me.virustotal.dynamicgui.world.sponge;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.virustotal.dynamicgui.world.LocationWrapper;


public class SpongeLocationWrapper<T> extends LocationWrapper<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8377525322712779423L;

	public SpongeLocationWrapper(T location) 
	{
		super(location);
	}

	@SuppressWarnings("unchecked")
	public Location<World> getSpongeLocation()
	{
		return (Location<World>) super.getLocation();
	}
	
	@Override
	public int getX() 
	{
		return this.getSpongeLocation().getBlockX();
	}

	@Override
	public int getY() 
	{
		return this.getSpongeLocation().getBlockY();
	}

	@Override
	public int getZ() 
	{
		return this.getSpongeLocation().getBlockZ();
	}

	@Override
	public String getWorldName() 
	{
		return this.getSpongeLocation().getExtent().getName();
	}
}