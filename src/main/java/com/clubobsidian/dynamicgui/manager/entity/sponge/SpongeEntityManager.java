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
package com.clubobsidian.dynamicgui.manager.entity.sponge;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.sponge.SpongePlayerWrapper;
import com.clubobsidian.dynamicgui.manager.entity.EntityManager;

public class SpongeEntityManager extends EntityManager {

    private List<String> entityTypes;

    public SpongeEntityManager() {
        this.loadEntityTypes();
    }

    private void loadEntityTypes() {
        this.entityTypes = new ArrayList<>();
        for (Field field : EntityTypes.class.getDeclaredFields()) {
            this.entityTypes.add(field.getName());
        }
    }

    @Override
    public PlayerWrapper<Player> createPlayerWrapper(Object player) {
        return new SpongePlayerWrapper<Player>((Player) player);
    }

    @Override
    public List<String> getEntityTypes() {
        return this.entityTypes;
    }
}