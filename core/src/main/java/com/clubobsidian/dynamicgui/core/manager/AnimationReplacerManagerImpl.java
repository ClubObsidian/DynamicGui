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

package com.clubobsidian.dynamicgui.core.manager;

import com.clubobsidian.dynamicgui.api.component.AnimationHolder;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.manager.replacer.AnimationReplacerManager;
import com.clubobsidian.dynamicgui.api.registry.replacer.AnimationReplacerRegistry;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class AnimationReplacerManagerImpl extends AnimationReplacerManager {

    private final List<AnimationReplacerRegistry> registries;

    @Inject
    private AnimationReplacerManagerImpl() {
        this.registries = new ArrayList<>();
    }

    @Override
    public String replace(AnimationHolder holder, PlayerWrapper<?> playerWrapper, String text) {
        String newText = text;
        for (AnimationReplacerRegistry registry : this.registries) {
            newText = registry.replace(holder, playerWrapper, newText);
        }
        return newText;
    }

    @Override
    public void registerReplacerRegistry(AnimationReplacerRegistry replacerRegistry) {
        this.registries.add(replacerRegistry);
    }
}