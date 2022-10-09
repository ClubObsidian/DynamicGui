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

package com.clubobsidian.dynamicgui.core.plugin;

import com.clubobsidian.dynamicgui.api.economy.Economy;
import com.clubobsidian.dynamicgui.api.permission.Permission;
import com.clubobsidian.dynamicgui.api.registry.npc.NPCRegistry;

import java.io.File;
import java.util.List;

public interface DynamicGuiPlugin {

    void start();

    void stop();

    Economy getEconomy();

    Permission getPermission();

    List<NPCRegistry> getNPCRegistries();

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

    //void unregisterNativeCommand(String commandName);
}