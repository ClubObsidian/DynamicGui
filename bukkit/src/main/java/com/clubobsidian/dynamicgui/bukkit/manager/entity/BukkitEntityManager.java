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
package com.clubobsidian.dynamicgui.bukkit.manager.entity;

import com.clubobsidian.dynamicgui.bukkit.entity.BukkitPlayerWrapper;
import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.manager.entity.EntityManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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
    public PlayerWrapper<?> createPlayerWrapper(Object player) {
        return new BukkitPlayerWrapper<Player>((Player) player);
    }

    @Override
    public List<String> getEntityTypes() {
        return this.entityTypes;
    }
}