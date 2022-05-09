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

package com.clubobsidian.dynamicgui.core.manager.dynamicgui;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.gui.property.animation.AnimationHolder;
import com.clubobsidian.dynamicgui.core.registry.replacer.AnimationReplacerRegistry;

import java.util.ArrayList;
import java.util.List;

public class AnimationReplacerManager {

    private static AnimationReplacerManager instance;

    private final List<AnimationReplacerRegistry> registries;

    private AnimationReplacerManager() {
        this.registries = new ArrayList<>();
    }

    public static AnimationReplacerManager get() {
        if (instance == null) {
            instance = new AnimationReplacerManager();
        }
        return instance;
    }

    public String replace(AnimationHolder holder, PlayerWrapper<?> playerWrapper, String text) {
        String newText = text;
        for (AnimationReplacerRegistry registry : this.registries) {
            newText = registry.replace(holder, playerWrapper, newText);
        }
        return newText;
    }

    public void registerReplacerRegistry(AnimationReplacerRegistry replacerRegistry) {
        this.registries.add(replacerRegistry);
    }
}