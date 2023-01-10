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

package com.clubobsidian.dynamicgui.core.test.mock.inject;

import cloud.commandframework.CommandManager;
import com.clubobsidian.dynamicgui.api.command.GuiCommandSender;
import com.clubobsidian.dynamicgui.api.manager.entity.EntityManager;
import com.clubobsidian.dynamicgui.api.manager.inventory.InventoryManager;
import com.clubobsidian.dynamicgui.api.manager.inventory.ItemStackManager;
import com.clubobsidian.dynamicgui.api.manager.material.MaterialManager;
import com.clubobsidian.dynamicgui.api.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.core.inject.module.PluginModule;
import com.clubobsidian.dynamicgui.api.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.api.platform.Platform;
import com.clubobsidian.dynamicgui.api.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.core.manager.cloud.CloudManager;
import com.clubobsidian.dynamicgui.core.test.mock.manager.MockCloudManager;
import com.clubobsidian.dynamicgui.core.test.mock.manager.MockEntityManager;
import com.clubobsidian.dynamicgui.core.test.mock.manager.MockInventoryManager;
import com.clubobsidian.dynamicgui.core.test.mock.manager.MockItemStackManager;
import com.clubobsidian.dynamicgui.core.test.mock.manager.MockLocationManager;
import com.clubobsidian.dynamicgui.core.test.mock.manager.MockMaterialManager;

public class MockPluginModule extends PluginModule {

    public MockPluginModule(DynamicGuiPlugin plugin,
                            Platform platform,
                            LoggerWrapper<?> logger,
                            CommandManager<GuiCommandSender> commandManager) {
        super(plugin, platform, logger, commandManager);
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

    @Override
    public Class<? extends CloudManager> getCloudManager() {
        return MockCloudManager.class;
    }
}
