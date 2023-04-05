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

package com.clubobsidian.dynamicgui.core.test.mock.plugin;

import com.clubobsidian.dynamicgui.api.plugin.DynamicGuiPlugin;

import java.io.File;

public class MockDynamicGuiPlugin implements DynamicGuiPlugin {

    private final File dataFolder = new File("mock_data");
    private final File guiFolder = new File(this.dataFolder, "guis");
    private final File macroFolder = new File(this.dataFolder, "macros");
    private final File mainFolder = new File("src", "main");
    private final File resourcesFolder = new File(this.mainFolder, "resources");
    private final File configFile = new File(this.resourcesFolder, "config.yml");

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public File getDataFolder() {
        return this.dataFolder;
    }

    @Override
    public File getConfigFile() {
        return this.configFile;
    }

    @Override
    public File getGuiFolder() {
        return this.guiFolder;
    }

    @Override
    public File getMacroFolder() {
        return this.macroFolder;
    }
}
