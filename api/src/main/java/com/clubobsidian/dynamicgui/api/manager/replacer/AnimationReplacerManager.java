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

package com.clubobsidian.dynamicgui.api.manager.replacer;

import com.clubobsidian.dynamicgui.api.component.AnimationHolder;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.registry.replacer.AnimationReplacerRegistry;
import org.jetbrains.annotations.ApiStatus;

import javax.inject.Inject;

@ApiStatus.Experimental
public abstract class AnimationReplacerManager {

    @Inject
    private static AnimationReplacerManager instance;

    public static AnimationReplacerManager get() {
        return instance;
    }

    public abstract String replace(AnimationHolder holder, PlayerWrapper<?> playerWrapper, String text);

    public abstract void registerReplacerRegistry(AnimationReplacerRegistry replacerRegistry);
}