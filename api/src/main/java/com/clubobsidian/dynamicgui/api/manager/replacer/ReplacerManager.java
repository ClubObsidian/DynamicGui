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

package com.clubobsidian.dynamicgui.api.manager.replacer;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.registry.replacer.ReplacerRegistry;

import javax.inject.Inject;

public abstract class ReplacerManager {

    @Inject
    private static ReplacerManager instance;

    public static ReplacerManager get() {
        return instance;
    }

    /**
     *
     *
     * @param text
     * @param playerWrapper
     * @return
     */
    public abstract String replace(String text, PlayerWrapper<?> playerWrapper);

    public abstract void registerReplacerRegistry(ReplacerRegistry replacerRegistry);
}