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

package com.clubobsidian.dynamicgui.bukkit.manager.entity;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.manager.entity.EntityManager;
import com.clubobsidian.dynamicgui.bukkit.entity.BukkitPlayerWrapper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BukkitEntityManager extends EntityManager {

    private List<String> entityTypes;

    public BukkitEntityManager() {
        this.loadEntityTypes();
    }

    private void loadEntityTypes() {
        this.entityTypes = new ArrayList<>();
        for (EntityType type : EntityType.values()) {
            this.entityTypes.add(type.name());
        }
    }

    @Override
    public PlayerWrapper<?> createPlayerWrapper(@NotNull Object player) {
        Objects.requireNonNull(player);
        return new BukkitPlayerWrapper<>((Player) player);
    }

    @Override
    public boolean isPlayer(@NotNull Object player) {
        return player instanceof Player;
    }

    @Override
    public List<String> getEntityTypes() {
        return this.entityTypes;
    }
}