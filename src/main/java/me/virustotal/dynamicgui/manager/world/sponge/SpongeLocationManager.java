package me.virustotal.dynamicgui.manager.world.sponge;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.virustotal.dynamicgui.manager.world.LocationManager;
import me.virustotal.dynamicgui.world.location.LocationWrapper;
import me.virustotal.dynamicgui.world.location.sponge.SpongeLocationWrapper;

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