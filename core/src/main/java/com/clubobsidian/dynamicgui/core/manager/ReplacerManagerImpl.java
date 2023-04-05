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

package com.clubobsidian.dynamicgui.core.manager;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.manager.replacer.ReplacerManager;
import com.clubobsidian.dynamicgui.api.registry.replacer.ReplacerRegistry;
import com.clubobsidian.dynamicgui.core.util.ChatColor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ReplacerManagerImpl extends ReplacerManager {

    private final List<ReplacerRegistry> registries = new ArrayList<>();

    @Inject
    private ReplacerManagerImpl() {
    }

    @Override
    public String replace(String text, PlayerWrapper<?> playerWrapper) {
        String newText = text;
        for (ReplacerRegistry registry : this.registries) {
            newText = registry.replace(playerWrapper, newText);
        }
        return ChatColor.translateAlternateColorCodes(newText);
    }

    @Override
    public void registerReplacerRegistry(ReplacerRegistry replacerRegistry) {
        this.registries.add(replacerRegistry);
    }
}