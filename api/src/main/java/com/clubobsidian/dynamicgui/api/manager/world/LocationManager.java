/*
 *    Copyright 2018-2023 virustotalop
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

package com.clubobsidian.dynamicgui.api.manager.world;

import com.clubobsidian.dynamicgui.api.world.LocationWrapper;

import javax.inject.Inject;

public abstract class LocationManager {

    @Inject
    private static LocationManager instance;

    public static LocationManager get() {
        return instance;
    }

    public abstract LocationWrapper<?> toLocationWrapper(String world, int x, int y, int z);

    public abstract LocationWrapper<?> toLocationWrapper(Object obj);

    public LocationWrapper<?> toLocationWrapper(String locationStr) {
        String[] split = locationStr.split(",");
        Integer x = Integer.valueOf(split[0]);
        Integer y = Integer.valueOf(split[1]);
        Integer z = Integer.valueOf(split[2]);
        String world = split[3];
        return this.toLocationWrapper(world, x, y, z);
    }
}