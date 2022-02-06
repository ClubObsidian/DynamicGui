/*
 *    Copyright 2021 Club Obsidian and contributors.
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
package com.clubobsidian.dynamicgui.world;

import java.io.Serializable;

public abstract class WorldWrapper<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2342470292001970943L;

    private final String name;

    public WorldWrapper(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract T getWorld();

    public abstract void setGameRule(String key, String value);

    public abstract String getGameRule(String rule);

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(obj == null) {
            return false;
        } else if(getClass() != obj.getClass()) {
            return false;
        }

        WorldWrapper<T> otherWrapper = (WorldWrapper<T>) obj;
        T world = this.getWorld();
        if(world == null) {
            return false;
        }

        String otherName = otherWrapper.getName();

        return this.getName().equals(otherName);
    }
}