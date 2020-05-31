/*
   Copyright 2020 Club Obsidian and contributors.

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

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.clubobsidian.dynamicgui.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.world.LocationWrapper;
import com.clubobsidian.dynamicgui.world.sponge.SpongeWorldWrapper;

public class SpongeLocationManager extends LocationManager {


	@Override
	public LocationWrapper<?> toLocationWrapper(String world, int x, int y, int z) 
	{
		SpongeWorldWrapper worldWrapper = new SpongeWorldWrapper(world);
		return new LocationWrapper<Location<World>>(x, y, z, worldWrapper);
	}

	@Override
	public LocationWrapper<?> toLocationWrapper(Object obj) 
	{
		Location<World> location = (Location<World>) obj;
		String worldName = location.getExtent().getName();
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		
		return this.toLocationWrapper(worldName, x, y, z);
	}
}