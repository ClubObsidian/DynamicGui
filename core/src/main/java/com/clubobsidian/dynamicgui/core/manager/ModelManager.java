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

import com.clubobsidian.dynamicgui.core.registry.model.ModelProvider;
import com.clubobsidian.dynamicgui.core.registry.model.vanilla.VanillaModelProvider;

import java.util.HashMap;
import java.util.Map;

public class ModelManager {

    private static ModelManager instance;

    public static ModelManager get() {
        if (instance == null) {
            instance = new ModelManager();
        }
        return instance;
    }

    private Map<String, ModelProvider> registries = new HashMap<>();

    private ModelManager() {
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
