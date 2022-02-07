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

package com.clubobsidian.dynamicgui.test.mock.inject;

import com.clubobsidian.dynamicgui.core.inject.module.PluginModule;
import com.clubobsidian.dynamicgui.core.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.core.manager.entity.EntityManager;
import com.clubobsidian.dynamicgui.core.manager.inventory.InventoryManager;
import com.clubobsidian.dynamicgui.core.manager.inventory.ItemStackManager;
import com.clubobsidian.dynamicgui.core.manager.material.MaterialManager;
import com.clubobsidian.dynamicgui.core.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.core.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.core.server.Platform;
import com.clubobsidian.dynamicgui.test.mock.manager.MockEntityManager;
import com.clubobsidian.dynamicgui.test.mock.manager.MockInventoryManager;
import com.clubobsidian.dynamicgui.test.mock.manager.MockItemStackManager;
import com.clubobsidian.dynamicgui.test.mock.manager.MockLocationManager;
import com.clubobsidian.dynamicgui.test.mock.manager.MockMaterialManager;

public class MockPluginModule extends PluginModule {

    public MockPluginModule(DynamicGuiPlugin plugin, Platform platform, LoggerWrapper<?> logger) {
        super(plugin, platform, logger);
    }

    @Override
    public Class<? extends EntityManager> getEntityManager() {
        return MockEntityManager.class;
    }

    @Override
    public Class<? extends InventoryManager> getInventoryManager() {
        return MockInventoryManager.class;
    }

    @Override
    public Class<? extends ItemStackManager> getItemStackManager() {
        return MockItemStackManager.class;
    }

    @Override
    public Class<? extends MaterialManager> getMaterialManager() {
        return MockMaterialManager.class;
    }

    @Override
    public Class<? extends LocationManager> getLocationManger() {
        return MockLocationManager.class;
    }
}