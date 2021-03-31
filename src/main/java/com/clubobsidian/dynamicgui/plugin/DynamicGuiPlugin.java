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
package com.clubobsidian.dynamicgui.plugin;

import java.io.File;
import java.util.List;

import com.clubobsidian.dynamicgui.economy.Economy;
import com.clubobsidian.dynamicgui.permission.Permission;
import com.clubobsidian.dynamicgui.registry.npc.NPCRegistry;

public interface DynamicGuiPlugin {

    public void start();

    public void stop();

    public Economy getEconomy();

    public Permission getPermission();

    public List<NPCRegistry> getNPCRegistries();

    public File getDataFolder();

    public File getConfigFile();

    public File getGuiFolder();

    public File getMacroFolder();

    public List<String> getRegisteredCommands();

    public void createCommand(String guiName, String alias);

    public void unloadCommands();

}