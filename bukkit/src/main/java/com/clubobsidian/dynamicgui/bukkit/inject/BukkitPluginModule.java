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

package com.clubobsidian.dynamicgui.bukkit.inject;

import cloud.commandframework.CommandManager;
import com.clubobsidian.dynamicgui.api.command.GuiCommandSender;
import com.clubobsidian.dynamicgui.api.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.api.manager.entity.EntityManager;
import com.clubobsidian.dynamicgui.api.manager.inventory.InventoryManager;
import com.clubobsidian.dynamicgui.api.manager.inventory.ItemStackManager;
import com.clubobsidian.dynamicgui.api.manager.material.MaterialManager;
import com.clubobsidian.dynamicgui.api.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.api.platform.Platform;
import com.clubobsidian.dynamicgui.api.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.bukkit.manager.entity.BukkitEntityManager;
import com.clubobsidian.dynamicgui.bukkit.manager.inventory.BukkitInventoryManager;
import com.clubobsidian.dynamicgui.bukkit.manager.inventory.BukkitItemStackManager;
import com.clubobsidian.dynamicgui.bukkit.manager.material.BukkitMaterialManager;
import com.clubobsidian.dynamicgui.bukkit.manager.world.BukkitLocationManager;
import com.clubobsidian.dynamicgui.core.inject.module.PluginModule;

public class BukkitPluginModule extends PluginModule {

    public BukkitPluginModule(DynamicGuiPlugin plugin,
                              Platform platform,
                              LoggerWrapper<?> logger,
                              CommandManager<GuiCommandSender> commandManager) {
        super(plugin, platform, logger, commandManager);
    }

    @Override
    public Class<? extends EntityManager> getEntityManager() {
        return BukkitEntityManager.class;
    }

    @Override
    public Class<? extends InventoryManager> getInventoryManager() {
        return BukkitInventoryManager.class;
    }

    @Override
    public Class<? extends ItemStackManager> getItemStackManager() {
        return BukkitItemStackManager.class;
    }

    @Override
    public Class<? extends MaterialManager> getMaterialManager() {
        return BukkitMaterialManager.class;
    }

    @Override
    public Class<? extends LocationManager> getLocationManger() {
        return BukkitLocationManager.class;
    }
}
