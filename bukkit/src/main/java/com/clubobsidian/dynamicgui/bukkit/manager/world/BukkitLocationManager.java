/*
 *    Copyright 2022 virustotalop and contributors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.clubobsidian.dynamicgui.bukkit.manager.world;

import com.clubobsidian.dynamicgui.api.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.api.world.LocationWrapper;
import com.clubobsidian.dynamicgui.bukkit.world.BukkitWorldWrapper;
import org.bukkit.Location;
import org.bukkit.World;

public class BukkitLocationManager extends LocationManager {

    @Override
    public LocationWrapper<?> toLocationWrapper(String world, int x, int y, int z) {
        BukkitWorldWrapper worldWrapper = new BukkitWorldWrapper(world);
        return new LocationWrapper<World>(x, y, z, worldWrapper);
    }

    @Override
    public LocationWrapper<?> toLocationWrapper(Object obj) {
        if (!(obj instanceof Location)) {
            return null;
        }

        Location location = (Location) obj;
        String worldName = location.getWorld().getName();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        return this.toLocationWrapper(worldName, x, y, z);
    }
}