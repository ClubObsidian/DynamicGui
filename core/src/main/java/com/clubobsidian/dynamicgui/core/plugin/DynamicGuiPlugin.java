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

import com.clubobsidian.dynamicgui.core.economy.Economy;
import com.clubobsidian.dynamicgui.core.permission.Permission;
import com.clubobsidian.dynamicgui.core.registry.npc.NPCRegistry;

import java.io.File;
import java.util.List;

public interface DynamicGuiPlugin {

    void start();

    void stop();

    Economy getEconomy();

    Permission getPermission();

    List<NPCRegistry> getNPCRegistries();

    File getDataFolder();

    File getConfigFile();

    File getGuiFolder();

    File getMacroFolder();

    List<String> getRegisteredCommands();

    void createCommand(String guiName, String alias);

    void unloadCommands();

}