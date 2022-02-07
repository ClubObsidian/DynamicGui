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

package com.clubobsidian.dynamicgui.test.mock;

import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.economy.Economy;
import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.dynamicgui.core.gui.InventoryType;
import com.clubobsidian.dynamicgui.core.gui.ModeEnum;
import com.clubobsidian.dynamicgui.core.gui.Slot;
import com.clubobsidian.dynamicgui.core.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.core.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.core.server.Platform;
import com.clubobsidian.dynamicgui.test.mock.entity.player.MockPlayer;
import com.clubobsidian.dynamicgui.test.mock.entity.player.MockPlayerWrapper;
import com.clubobsidian.dynamicgui.test.mock.inject.MockPluginModule;
import com.clubobsidian.dynamicgui.test.mock.inventory.MockItemStack;
import com.clubobsidian.dynamicgui.test.mock.inventory.MockItemStackWrapper;
import com.clubobsidian.dynamicgui.test.mock.logger.MockLogger;
import com.clubobsidian.dynamicgui.test.mock.logger.MockLoggerWrapper;
import com.clubobsidian.dynamicgui.test.mock.plugin.MockDynamicGuiPlugin;
import com.clubobsidian.dynamicgui.test.mock.plugin.MockEconomy;
import com.clubobsidian.dynamicgui.test.mock.plugin.MockPlatform;
import com.clubobsidian.dynamicgui.test.mock.plugin.MockScheduler;
import com.clubobsidian.dynamicgui.test.mock.world.MockLocation;
import com.clubobsidian.dynamicgui.test.mock.world.MockLocationWrapper;
import com.clubobsidian.dynamicgui.test.mock.world.MockWorld;
import com.clubobsidian.dynamicgui.test.mock.world.MockWorldWrapper;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MockFactory {

    public <T> T mock(Class<T> mockClazz) {
        return Mockito.mock(mockClazz, Mockito.withSettings()
                .useConstructor()
                .defaultAnswer(Mockito.CALLS_REAL_METHODS));
    }

    public <T> T mock(Class<T> mockClazz, Object... args) {
        return Mockito.mock(mockClazz, Mockito.withSettings().useConstructor(args)
                .defaultAnswer(Mockito.CALLS_REAL_METHODS));
    }

    public MockPlayerWrapper createPlayer() {
        return this.mock(MockPlayerWrapper.class);
    }

    public MockPlayerWrapper createPlayer(String name, UUID uuid) {
        MockPlayer player = this.mock(MockPlayer.class, name, uuid);
        return this.mock(MockPlayerWrapper.class, player);
    }

    public MockItemStackWrapper createItemStack(String type) {
        MockItemStack itemStack = this.mock(MockItemStack.class, type);
        return this.mock(MockItemStackWrapper.class, itemStack);
    }

    public Gui createGui(String title) {
        return new Gui(title,
                InventoryType.CHEST.toString(),
                title,
                6,
                true,
                ModeEnum.SET,
                new HashMap<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new FunctionTree(),
                new HashMap<>());
    }

    public Slot createSlot(String type) {
        return this.createSlot(type, false);
    }

    public Slot createSlot(String type, boolean movable) {
        return new Slot(0,
                1,
                type,
                "test",
                null,
                (short) 0,
                false,
                movable,
                false,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                null,
                null,
                new FunctionTree(),
                0,
                new HashMap<>());
    }

    public MockWorldWrapper createWorld(String worldName) {
        MockWorld mockWorld = new MockWorld(worldName);
        return new MockWorldWrapper(mockWorld);
    }

    public MockLocationWrapper createLocation(int x, int y, int z, String worldName) {
        MockWorldWrapper worldWrapper = this.createWorld(worldName);
        MockLocation mockLocation = new MockLocation(x, y, z, worldWrapper);
        return new MockLocationWrapper(mockLocation);
    }

    public MockFactory inject() {
        DynamicGuiPlugin plugin = new MockDynamicGuiPlugin();
        Platform platform = new MockPlatform(new MockScheduler());
        LoggerWrapper<?> logger = new MockLoggerWrapper(new MockLogger());
        MockPluginModule module = new MockPluginModule(plugin, platform, logger);
        module.bootstrap();
        DynamicGui.get(); //Initializes dynamic gui
        this.getLogger().getLogger().clear(); //Clear logs for initialization
        return this;
    }

    public MockPlatform getPlatform() {
        Platform platform = DynamicGui.get().getPlatform();
        if(!(platform instanceof MockPlatform)) {
            return null;
        }
        return (MockPlatform) platform;
    }

    public MockLoggerWrapper getLogger() {
        LoggerWrapper<?> logger = DynamicGui.get().getLogger();
        if(!(logger instanceof MockLoggerWrapper)) {
            return null;
        }
        return (MockLoggerWrapper) logger;
    }

    public MockEconomy getEconomy() {
        Economy economy = DynamicGui.get().getPlugin().getEconomy();;
        if(!(economy instanceof MockEconomy)) {
            return null;
        }
        return (MockEconomy) economy;
    }

    public MockDynamicGuiPlugin getPlugin() {
        DynamicGuiPlugin plugin = DynamicGui.get().getPlugin();
        if(!(plugin instanceof MockDynamicGuiPlugin)) {
            return null;
        }
        return (MockDynamicGuiPlugin) plugin;
    }
}