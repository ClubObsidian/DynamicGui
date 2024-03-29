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

package com.clubobsidian.dynamicgui.api.entity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public abstract class EntityWrapper<T> {

    private final T entity;

    public EntityWrapper(@NotNull T entity) {
        this.entity = Objects.requireNonNull(entity);
    }

    /**
     * Get the UUID of the entity
     * @return the UUID of the entity
     */
    public abstract UUID getUniqueId();

    /**
     * Gets the native underlying entity for the wrapper
     *
     * @return native entity
     */
    public T getNative() {
        return this.entity;
    }
}