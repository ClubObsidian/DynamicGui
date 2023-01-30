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

package com.clubobsidian.dynamicgui.api.plugin;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.economy.Economy;
import com.clubobsidian.dynamicgui.api.permission.Permission;
import com.clubobsidian.dynamicgui.api.registry.npc.NPCRegistry;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

import java.io.File;
import java.util.List;

public interface DynamicGuiPlugin {

    void start();

    void stop();

    @Deprecated(since = "6.0.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "7.0.0")
    default Economy getEconomy() {
        return DynamicGui.get().getEconomy();
    }

    @Deprecated(since = "6.0.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "7.0.0")
    default Permission getPermission() {
        return DynamicGui.get().getPermission();
    }

    @Deprecated(since = "6.0.0")
    @ApiStatus.ScheduledForRemoval(inVersion = "7.0.0")
    @Unmodifiable default List<NPCRegistry> getNPCRegistries() {
        return DynamicGui.get().getNpcRegistries();
    }

    File getDataFolder();

    default File getConfigFile() {
        return new File(this.getDataFolder(), "config.yml");
    }

    default File getGuiFolder() {
        return new File(this.getDataFolder(), "guis");
    }

    default File getMacroFolder() {
        return new File(this.getDataFolder(), "macros");
    }
}