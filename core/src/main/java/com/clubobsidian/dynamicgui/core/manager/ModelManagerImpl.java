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

import com.clubobsidian.dynamicgui.api.manager.ModelManager;
import com.clubobsidian.dynamicgui.api.model.ModelProvider;
import com.clubobsidian.dynamicgui.core.model.vanilla.VanillaModelProvider;

import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class ModelManagerImpl extends ModelManager {

    private final Map<String, ModelProvider> registries = new HashMap<>();

    @Inject
    private ModelManagerImpl() {
        this.register(new VanillaModelProvider());
    }

    public ModelProvider getProvider(final String registryName) {
        if (registryName == null) {
            return null;
        }
        String lowerName = registryName.toLowerCase();
        return this.registries.get(lowerName);
    }

    public void register(ModelProvider provider) {
        this.registries.put(provider.name().toLowerCase(), provider);
    }
}
