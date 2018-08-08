package me.virustotal.dynamicgui.world.sponge;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.virustotal.dynamicgui.world.LocationWrapper;


public class SpongeLocationWrapper<T> extends LocationWrapper<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8377525322712779423L;

	private T location;
	@SuppressWarnings("unchecked")
	public SpongeLocationWrapper(T location) 
	{
		super(((Location<World>)location).getBlockX(), ((Location<World>)location).getBlockY(), ((Location<World>)location).getBlockZ(), ((Location<World>)location).getExtent().getName());
		this.location = location;
	}

	@Override
	public T getLocation() 
	{
		return this.location;
	}
}