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

package com.clubobsidian.dynamicgui.bukkit.plugin;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.command.GuiCommandSender;
import com.clubobsidian.dynamicgui.api.economy.Economy;
import com.clubobsidian.dynamicgui.api.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.api.manager.ModelManager;
import com.clubobsidian.dynamicgui.api.manager.replacer.ReplacerManager;
import com.clubobsidian.dynamicgui.api.permission.Permission;
import com.clubobsidian.dynamicgui.api.platform.Platform;
import com.clubobsidian.dynamicgui.api.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.bukkit.cloud.DynamicGuiPaperCommandManager;
import com.clubobsidian.dynamicgui.bukkit.command.BukkitGuiCommandSender;
import com.clubobsidian.dynamicgui.bukkit.economy.VaultEconomy;
import com.clubobsidian.dynamicgui.bukkit.inject.BukkitPluginModule;
import com.clubobsidian.dynamicgui.bukkit.listener.*;
import com.clubobsidian.dynamicgui.bukkit.permission.FoundryPermission;
import com.clubobsidian.dynamicgui.bukkit.permission.VaultPermission;
import com.clubobsidian.dynamicgui.bukkit.platform.BukkitPlatform;
import com.clubobsidian.dynamicgui.bukkit.registry.model.ItemsAdderModelProvider;
import com.clubobsidian.dynamicgui.bukkit.registry.model.OraxenModelProvider;
import com.clubobsidian.dynamicgui.bukkit.registry.npc.CitizensRegistry;
import com.clubobsidian.dynamicgui.bukkit.registry.replacer.PlaceholderApiReplacerRegistry;
import com.clubobsidian.dynamicgui.core.economy.NoOpEconomy;
import com.clubobsidian.dynamicgui.core.logger.JavaLoggerWrapper;
import com.clubobsidian.dynamicgui.core.permission.NoOpPermission;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.brigadier.BrigadierSetting;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.bukkit.internal.BukkitBackwardsBrigadierSenderMapper;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.LegacyPaperCommandManager;
import org.incendo.cloud.paper.PaperCommandManager;
import org.incendo.cloud.setting.ManagerSetting;

import java.util.logging.Level;

public class DynamicGuiBukkitPlugin extends JavaPlugin implements DynamicGuiPlugin {


    @Override
    public void onEnable() {
        this.start();
    }

    @Override
    public void start() {
        Platform platform = new BukkitPlatform(this);
        LoggerWrapper<?> logger = new JavaLoggerWrapper<>(this.getLogger());
        CommandManager<GuiCommandSender> commandManager = this.createCommandSender();
        PluginManager pm = this.getServer().getPluginManager();
        boolean vault = false;
        boolean foundry = false;
        if (pm.getPlugin("Vault") != null) {
            vault = true;
        }
        if (pm.getPlugin("Foundry") != null) {
            foundry = true;
        }
        Permission permission = vault && foundry
                ? new FoundryPermission()
                : vault
                ? new VaultPermission()
                : new NoOpPermission();

        if (permission instanceof NoOpPermission) {
            this.getLogger().log(Level.SEVERE, "No permission provider found, please install vault...");
        }
        Economy economy = vault ? new VaultEconomy() : new NoOpEconomy();
        if (economy instanceof NoOpPermission) {
            this.getLogger().log(Level.SEVERE, "Vault is not installed, economy functions will not work");
        }

        new BukkitPluginModule(this, platform, logger, commandManager, economy, permission).bootstrap();

        //Hack for adding citizens late
        //For some reason citizens sometimes will load after DynamicGui
        this.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
            if (this.getServer().getPluginManager().getPlugin("Citizens") != null) {
                DynamicGui.get().registerNPCRegistry(new CitizensRegistry());
            }
        }, 1);

        this.registerModelProviders(pm);

        if (this.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            ReplacerManager.get().registerReplacerRegistry(new PlaceholderApiReplacerRegistry());
        }

        this.registerListener(new EntityClickListener());
        this.registerListener(new InventoryInteractListener());
        this.registerListener(new InventoryCloseListener());
        this.registerListener(new InventoryOpenListener());
        this.registerListener(new PlayerInteractListener());
    }

    private void registerListener(Listener listener) {
        DynamicGui.get().inject(listener);
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    private CommandManager<GuiCommandSender> createCommandSender() {
        try {
            LegacyPaperCommandManager<GuiCommandSender> commandManager = new DynamicGuiPaperCommandManager<>(
                    this,
                    ExecutionCoordinator.simpleCoordinator(),
                    new SenderMapper<>() {
                        @Override
                        public @NonNull GuiCommandSender map(@NonNull CommandSender base) {
                            return new BukkitGuiCommandSender(base);
                        }

                        @Override
                        public @NonNull CommandSender reverse(@NonNull GuiCommandSender mapped) {
                            return mapped.getNativeSender();
                        }
                    }
            );
            //Unfortunately is tied to bukkit so there is no way to do this in core
            if (commandManager.hasBrigadierManager()) {
                commandManager.brigadierManager().settings().set(BrigadierSetting.FORCE_EXECUTABLE, true);
            }
            if (commandManager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
                commandManager.registerAsynchronousCompletions();
            }
            commandManager.settings().set(ManagerSetting.ALLOW_UNSAFE_REGISTRATION, true);
            commandManager.settings().set(ManagerSetting.OVERRIDE_EXISTING_COMMANDS, true);
            return commandManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void registerModelProviders(PluginManager pm) {
        if (pm.getPlugin("Oraxen") != null) {
            ModelManager.get().register(new OraxenModelProvider());
        }
        if (pm.getPlugin("ItemsAdder") != null) {
            ModelManager.get().register(new ItemsAdderModelProvider());
        }
    }

    @Override
    public void onDisable() {
        this.stop();
    }

    @Override
    public void stop() {
        DynamicGui.get().stop();
    }
}