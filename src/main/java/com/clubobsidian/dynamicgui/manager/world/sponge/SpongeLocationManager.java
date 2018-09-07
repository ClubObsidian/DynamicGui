/*   Copyright 2018 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
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
