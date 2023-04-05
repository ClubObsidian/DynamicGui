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

package com.clubobsidian.dynamicgui.api.world;

import java.io.Serializable;

public class LocationWrapper<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3610165660936041660L;

    private final int x;
    private final int y;
    private final int z;
    private final WorldWrapper<T> world;

    @SuppressWarnings({"rawtypes", "unchecked"})
    public LocationWrapper(int x, int y, int z, WorldWrapper world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public WorldWrapper<T> getWorld() {
        return this.world;
    }

    @Override
    public String toString() {
        return "LocationWrapper [x=" + x + ", y=" + y + ", z=" + z + ", world=" + world + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof LocationWrapper))
            return false;

        LocationWrapper<?> other = (LocationWrapper<?>) obj;
        if (other.getX() != this.getX())
            return false;
        if (other.getY() != this.getY())
            return false;
        if (other.getZ() != this.getZ())
            return false;

        return other.getWorld().equals(this.world);
    }
}