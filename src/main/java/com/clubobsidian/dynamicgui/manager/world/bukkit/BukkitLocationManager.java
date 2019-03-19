/*
   Copyright 2019 Club Obsidian and contributors.

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
package com.clubobsidian.dynamicgui.manager.world.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.clubobsidian.dynamicgui.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.world.LocationWrapper;
import com.clubobsidian.dynamicgui.world.bukkit.BukkitLocationWrapper;

public class BukkitLocationManager extends LocationManager {

	@Override
	public Object toLocation(String world, int x, int y, int z) 
	{
		World bukkitWorld = Bukkit.getServer().getWorld(world);
		if(bukkitWorld == null)
		{
			return null;
		}
		return new Location(bukkitWorld, x, y, z);
	}

	@Override
	public LocationWrapper<?> toLocationWrapper(Object location) 
	{
		return new BukkitLocationWrapper<Location>((Location) location);
	}
}