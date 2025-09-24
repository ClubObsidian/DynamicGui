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

package com.clubobsidian.dynamicgui.api.manager.entity;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import org.jetbrains.annotations.NotNull;

import jakarta.inject.Inject;
import java.util.List;

public abstract class EntityManager {

    @Inject
    private static EntityManager instance;

    public static EntityManager get() {
        return instance;
    }

    /**
     * Creates a player wrapper for a given player
     *
     * @param player player to wrap
     * @return the wrapped played
     */
    public abstract PlayerWrapper<?> createPlayerWrapper(@NotNull Object player);


    /**
     * Checks to see if the object is a native
     * player object
     *
     * @param player to check
     * @return if the object is a native player
     */
    public abstract boolean isPlayer(@NotNull Object player);

    /**
     * Gets all the registered entity types
     *
     * @return list of all entity types
     */
    public abstract List<String> getEntityTypes();
}