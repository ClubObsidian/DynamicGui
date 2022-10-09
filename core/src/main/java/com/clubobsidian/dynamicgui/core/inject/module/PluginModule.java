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

package com.clubobsidian.dynamicgui.core.inject.module;

import cloud.commandframework.CommandManager;
import com.clubobsidian.dynamicgui.api.command.CommandRegistrar;
import com.clubobsidian.dynamicgui.api.command.GuiCommandSender;
import com.clubobsidian.dynamicgui.api.factory.FunctionDataFactory;
import com.clubobsidian.dynamicgui.api.factory.FunctionTokenFactory;
import com.clubobsidian.dynamicgui.api.factory.FunctionTreeFactory;
import com.clubobsidian.dynamicgui.api.factory.GuiFactory;
import com.clubobsidian.dynamicgui.api.factory.SlotFactory;
import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.gui.Slot;
import com.clubobsidian.dynamicgui.api.manager.entity.EntityManager;
import com.clubobsidian.dynamicgui.api.manager.gui.GuiManager;
import com.clubobsidian.dynamicgui.api.manager.inventory.InventoryManager;
import com.clubobsidian.dynamicgui.api.manager.inventory.ItemStackManager;
import com.clubobsidian.dynamicgui.api.manager.material.MaterialManager;
import com.clubobsidian.dynamicgui.api.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionData;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionToken;
import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.core.DynamicGuiImpl;
import com.clubobsidian.dynamicgui.core.command.CommandRegistrarImpl;
import com.clubobsidian.dynamicgui.core.command.DynamicGuiCommand;
import com.clubobsidian.dynamicgui.core.command.GuiCommand;
import com.clubobsidian.dynamicgui.core.factory.FunctionDataFactoryImpl;
import com.clubobsidian.dynamicgui.core.factory.FunctionTokenFactoryImpl;
import com.clubobsidian.dynamicgui.core.factory.FunctionTreeFactoryImpl;
import com.clubobsidian.dynamicgui.core.factory.GuiFactoryImpl;
import com.clubobsidian.dynamicgui.core.factory.SlotFactoryImpl;
import com.clubobsidian.dynamicgui.api.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.core.manager.SimpleGuiManager;
import com.clubobsidian.dynamicgui.api.platform.Platform;
import com.clubobsidian.dynamicgui.api.plugin.DynamicGuiPlugin;
import com.clubobsidian.trident.EventBus;
import com.clubobsidian.trident.eventbus.methodhandle.MethodHandleEventBus;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;

public abstract class PluginModule implements Module {

    private final Class<? extends EntityManager> entityClass = this.getEntityManager();
    private final Class<? extends InventoryManager> inventoryClass = this.getInventoryManager();
    private final Class<? extends ItemStackManager> itemStackClass = this.getItemStackManager();
    private final Class<? extends MaterialManager> materialClass = this.getMaterialManager();
    private final Class<? extends LocationManager> locationClass = this.getLocationManger();
    private final DynamicGuiPlugin plugin;
    private final Platform platform;
    private final LoggerWrapper<?> logger;
    private final CommandManager<GuiCommandSender> commandManager;

    public PluginModule(DynamicGuiPlugin plugin,
                        Platform platform,
                        LoggerWrapper<?> logger,
                        CommandManager<GuiCommandSender> commandManager) {
        this.plugin = plugin;
        this.platform = platform;
        this.logger = logger;
        this.commandManager = commandManager;
    }

    public abstract Class<? extends EntityManager> getEntityManager();

    public abstract Class<? extends InventoryManager> getInventoryManager();

    public abstract Class<? extends ItemStackManager> getItemStackManager();

    public abstract Class<? extends MaterialManager> getMaterialManager();

    public abstract Class<? extends LocationManager> getLocationManger();

    @Override
    public void configure(Binder binder) {
        binder.bind(new TypeLiteral<LoggerWrapper<?>>() {
        }).toInstance(this.logger);
        binder.bind(EventBus.class).toInstance(new MethodHandleEventBus());

        //Factories
        binder.bind(FunctionTreeFactory.class).to(FunctionTreeFactoryImpl.class);
        binder.bind(FunctionTokenFactory.class).to(FunctionTokenFactoryImpl.class);
        binder.bind(FunctionDataFactory.class).to(FunctionDataFactoryImpl.class);
        binder.bind(SlotFactory.class).to(SlotFactoryImpl.class);
        binder.bind(GuiFactory.class).to(GuiFactoryImpl.class);

        binder.bind(new TypeLiteral<CommandManager<GuiCommandSender>>() {
        }).toInstance(this.commandManager);
        binder.bind(CommandRegistrar.class).to(CommandRegistrarImpl.class).asEagerSingleton();

        binder.bind(EntityManager.class).to(this.entityClass);
        binder.bind(InventoryManager.class).to(this.inventoryClass);
        binder.bind(ItemStackManager.class).to(this.itemStackClass);
        binder.bind(MaterialManager.class).to(this.materialClass);
        binder.bind(LocationManager.class).to(this.locationClass);
        binder.bind(DynamicGuiPlugin.class).toInstance(this.plugin);
        binder.bind(Platform.class).toInstance(this.platform);

        binder.bind(GuiManager.class).to(SimpleGuiManager.class);

        binder.bind(GuiCommand.class).asEagerSingleton();
        binder.bind(DynamicGuiCommand.class).asEagerSingleton();

        binder.bind(DynamicGui.class).to(DynamicGuiImpl.class);

        //Factories
        binder.requestStaticInjection(FunctionData.Builder.class);
        binder.requestStaticInjection(FunctionToken.Builder.class);
        binder.requestStaticInjection(Slot.Builder.class);
        binder.requestStaticInjection(Gui.Builder.class);

        binder.requestStaticInjection(EntityManager.class);
        binder.requestStaticInjection(InventoryManager.class);
        binder.requestStaticInjection(ItemStackManager.class);
        binder.requestStaticInjection(MaterialManager.class);
        binder.requestStaticInjection(LocationManager.class);
        binder.requestStaticInjection(GuiManager.class);
        binder.requestStaticInjection(DynamicGui.class);
    }

    public boolean bootstrap() {
        Guice.createInjector(this);
        return DynamicGui.get().start();
    }
}