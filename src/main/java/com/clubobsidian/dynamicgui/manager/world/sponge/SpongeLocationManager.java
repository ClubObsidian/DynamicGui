package com.clubobsidian.dynamicgui.manager.world.sponge;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.clubobsidian.dynamicgui.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.world.LocationWrapper;
import com.clubobsidian.dynamicgui.world.sponge.SpongeLocationWrapper;

public class SpongeLocationManager extends LocationManager {

	@Override
	public Object toLocation(String world, int x, int y, int z) 
	{
		Optional<World> spongeWorld = Sponge.getServer().getWorld(world);
		if(spongeWorld.isPresent())
		{
			return new Location<World>(spongeWorld.get(), x, y, z);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LocationWrapper<?> toLocationWrapper(Object location) 
	{
		return new SpongeLocationWrapper<Location<World>>((Location<World>) location);
	}
}