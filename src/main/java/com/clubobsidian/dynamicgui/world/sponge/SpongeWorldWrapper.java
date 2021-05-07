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
package com.clubobsidian.dynamicgui.world.sponge;

import com.clubobsidian.dynamicgui.world.WorldWrapper;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class SpongeWorldWrapper extends WorldWrapper<World> {

    /**
     *
     */
    private static final long serialVersionUID = -5164322923268176714L;

    public SpongeWorldWrapper(String name) {
        super(name);
    }

    @Override
    public World getWorld() {
        return Sponge.getServer().getWorld(this.getName()).get();
    }

    @Override
    public void setGameRule(String rule, String value) {
        this.getWorld().getProperties().setGameRule(rule, value);
    }

    @Override
    public String getGameRule(String rule) {
        Optional<String> gameRule = this.getWorld().getGameRule(rule);
        if(gameRule.isPresent()) {
            return gameRule.get();
        }

        return null;
    }
}