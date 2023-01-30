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

package com.clubobsidian.dynamicgui.core;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.command.CommandRegistrar;
import com.clubobsidian.dynamicgui.api.config.Config;
import com.clubobsidian.dynamicgui.api.config.Message;
import com.clubobsidian.dynamicgui.api.economy.Economy;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.function.Function;
import com.clubobsidian.dynamicgui.api.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.api.manager.FunctionManager;
import com.clubobsidian.dynamicgui.api.manager.cooldown.CooldownManager;
import com.clubobsidian.dynamicgui.api.manager.gui.GuiManager;
import com.clubobsidian.dynamicgui.api.manager.gui.SlotManager;
import com.clubobsidian.dynamicgui.api.manager.replacer.AnimationReplacerManager;
import com.clubobsidian.dynamicgui.api.manager.replacer.ReplacerManager;
import com.clubobsidian.dynamicgui.api.messaging.MessagingRunnable;
import com.clubobsidian.dynamicgui.api.permission.Permission;
import com.clubobsidian.dynamicgui.api.platform.Platform;
import com.clubobsidian.dynamicgui.api.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.api.proxy.Proxy;
import com.clubobsidian.dynamicgui.api.registry.npc.NPCRegistry;
import com.clubobsidian.dynamicgui.api.registry.replacer.CooldownReplacerRegistry;
import com.clubobsidian.dynamicgui.api.registry.replacer.DynamicGuiReplacerRegistry;
import com.clubobsidian.dynamicgui.api.registry.replacer.MetadataReplacerRegistry;
import com.clubobsidian.dynamicgui.api.replacer.Replacer;
import com.clubobsidian.dynamicgui.core.command.DynamicGuiCommand;
import com.clubobsidian.dynamicgui.core.command.GuiCommand;
import com.clubobsidian.dynamicgui.core.command.cloud.CloudArgument;
import com.clubobsidian.dynamicgui.core.config.ChatColorTransformer;
import com.clubobsidian.dynamicgui.core.config.ConfigImpl;
import com.clubobsidian.dynamicgui.core.config.ConfigMessage;
import com.clubobsidian.dynamicgui.core.listener.EntityClickListener;
import com.clubobsidian.dynamicgui.core.listener.GuiListener;
import com.clubobsidian.dynamicgui.core.listener.InventoryCloseListener;
import com.clubobsidian.dynamicgui.core.listener.InventoryInteractListener;
import com.clubobsidian.dynamicgui.core.listener.PlayerInteractListener;
import com.clubobsidian.dynamicgui.core.manager.cloud.CloudManager;
import com.clubobsidian.dynamicgui.core.registry.replacer.DynamicGuiAnimationReplacerRegistry;
import com.clubobsidian.trident.EventBus;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;
import com.clubobsidian.wrappy.transformer.NodeTransformer;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.inject.Injector;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Unmodifiable;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicGuiImpl extends DynamicGui {

    private Config config;
    private Proxy proxy;
    private final Map<String, Integer> serverPlayerCount = new ConcurrentHashMap<>();
    private final EventBus eventBus;
    private final CloudManager cloudManager;
    private final DynamicGuiPlugin plugin;
    private final Economy economy;
    private final Permission permission;
    private final Platform platform;
    private final LoggerWrapper<?> loggerWrapper;
    private final Injector injector;
    private final CommandRegistrar commandRegistrar;
    private final List<NPCRegistry> npcRegistries = new ArrayList<>();
    private boolean initialized;

    @Inject
    private DynamicGuiImpl(DynamicGuiPlugin plugin,
                           Economy economy,
                           Permission permission,
                           Platform platform,
                           LoggerWrapper<?> loggerWrapper,
                           Injector injector,
                           CommandRegistrar commandRegistrar,
                           EventBus eventBus,
                           CloudManager cloudManager) {
        this.plugin = plugin;
        this.economy = economy;
        this.permission = permission;
        this.platform = platform;
        this.loggerWrapper = loggerWrapper;
        this.commandRegistrar = commandRegistrar;
        this.eventBus = eventBus;
        this.cloudManager = cloudManager;
        this.injector = injector;
        this.initialized = false;
        this.setupFileStructure();
        this.saveDefaultConfig();
    }

    @Override
    public boolean start() {
        if (!this.initialized) {
            this.initialized = true;
            this.economy.setup();
            this.loadCloudArgs();
            this.loadConfig();
            this.loadFunctions();
            this.loadGuis();
            this.checkForProxy();
            this.registerListeners();
            this.registerCommands();
            this.registerReplacerManagers();
            SlotManager.get();
            return true;
        }
        return false;
    }

    @Override
    public void stop() {
        CooldownManager.get().shutdown();
        this.commandRegistrar.unregisterCommand("gui");
        this.commandRegistrar.unregisterCommand("dynamicgui");
        this.commandRegistrar.unregisterCommand("dyngui");
        this.commandRegistrar.unregisterGuiAliases();
    }

    private void setupFileStructure() {
        if (!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdirs();
        }

        if (!this.plugin.getGuiFolder().exists()) {
            this.plugin.getGuiFolder().mkdirs();
        }

        if (!this.plugin.getMacroFolder().exists()) {
            this.plugin.getMacroFolder().mkdirs();
        }
    }

    private void saveDefaultConfig() {
        if (!this.plugin.getConfigFile().exists()) {
            try {
                FileUtils.copyInputStreamToFile(this.getClass()
                                .getClassLoader()
                                .getResourceAsStream("config.yml"),
                        this.plugin.getConfigFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadCloudArgs() {
        CloudArgument.register(CloudArgument.PLAYER_ARG_NAME, this.cloudManager.createPlayerArg());
    }

    private void loadConfig() {
        Configuration config = Configuration.load(this.plugin.getConfigFile());

        Message message = new ConfigMessage();
        ConfigurationSection messageSection = config.getConfigurationSection("message");
        Collection<NodeTransformer> transformers = new ArrayList<>();
        transformers.add(new ChatColorTransformer());
        messageSection.inject(message, transformers);

        this.config = new ConfigImpl(message);
        config.inject(this.config);

        String version = config.getString("version");
        if (version != null) {
            version = version.trim();
        }

        String proxyStr = config.getString("proxy");
        if (proxyStr == null) {
            proxyStr = version;
            config.set("proxy", proxyStr);
            config.save();
        } else {
            proxyStr = proxyStr.trim();
        }

        this.proxy = Proxy.fromString(proxyStr);

        for (final String server : config.getStringList("servers")) {
            this.serverPlayerCount.put(server, 0);

            DynamicGuiReplacerRegistry.get().addReplacer(new Replacer("%" + server + "-playercount%") {
                @Override
                public String replacement(String text, PlayerWrapper<?> player) {
                    return String.valueOf(getServerPlayerCount(server));
                }
            });
        }
    }

    private void loadGuis() {
        GuiManager.get(); //Initialize manager
    }

    private void checkForProxy() {
        MessagingRunnable runnable = (playerWrapper, message) -> {
            if (message.length > 13) {
                ByteArrayDataInput in = ByteStreams.newDataInput(message);
                String packet = in.readUTF();
                if (packet != null) {
                    if ("PlayerCount".equals(packet)) {
                        String server = in.readUTF();
                        int playerCount = in.readInt();
                        this.serverPlayerCount.put(server, playerCount);
                    }
                }
            }
        };

        if (this.proxy == Proxy.BUNGEE) {
            this.getLogger().info("BungeeCord is enabled!");
            this.getPlatform().registerOutgoingPluginChannel(this.getPlugin(), "BungeeCord");
            this.getPlatform().registerIncomingPluginChannel(this.getPlugin(), "BungeeCord", runnable);
        } else if (this.proxy == Proxy.REDIS_BUNGEE) {
            this.getLogger().info("RedisBungee is enabled");
            this.getPlatform().registerOutgoingPluginChannel(this.getPlugin(), "RedisBungee");
            this.getPlatform().registerOutgoingPluginChannel(this.getPlugin(), "BungeeCord");
            this.getPlatform().registerIncomingPluginChannel(this.getPlugin(), "RedisBungee", runnable);
        } else {
            this.getLogger().info("A proxy is not in use, please configure the proxy config value if you need proxy support!");
        }

        if (this.proxy != Proxy.NONE) {
            this.startPlayerCountTimer();
        }
    }

    private void registerListeners() {
        this.eventBus.registerEvents(new EntityClickListener());
        this.eventBus.registerEvents(new InventoryInteractListener());
        this.eventBus.registerEvents(new InventoryCloseListener());
        this.eventBus.registerEvents(new PlayerInteractListener());
        this.eventBus.registerEvents(new GuiListener());
    }

    private void registerCommands() {
        this.commandRegistrar.registerCommand(GuiCommand.class);
        this.commandRegistrar.registerCommand(DynamicGuiCommand.class);
    }

    private void registerReplacerManagers() {
        ReplacerManager.get().registerReplacerRegistry(DynamicGuiReplacerRegistry.get());
        ReplacerManager.get().registerReplacerRegistry(CooldownReplacerRegistry.get());
        ReplacerManager.get().registerReplacerRegistry(MetadataReplacerRegistry.get());
        AnimationReplacerManager.get().registerReplacerRegistry(DynamicGuiAnimationReplacerRegistry.get());
    }

    private void loadFunctions() {
        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages("com.clubobsidian.dynamicgui.core.function")
                .scan()) {
            for (ClassInfo classInfo : scanResult.getSubclasses(Function.class)) {
                try {
                    FunctionManager.get().registerFunction((Function) classInfo
                            .loadClass()
                            .getDeclaredConstructor()
                            .newInstance());
                } catch (InstantiationException | IllegalAccessException |
                         InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startPlayerCountTimer() {
        this.getPlatform().getScheduler().scheduleSyncRepeatingTask(() -> {
            for (String server : this.serverPlayerCount.keySet()) {
                PlayerWrapper<?> player = Iterables.getFirst(this.getPlatform().getOnlinePlayers(), null);
                if (player != null) {
                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    out.writeUTF("PlayerCount");
                    out.writeUTF(server);
                    String sendTo = "BungeeCord";
                    if (this.proxy == Proxy.REDIS_BUNGEE) {
                        sendTo = "RedisBungee";
                    }
                    player.sendPluginMessage(sendTo, out.toByteArray());
                }
            }
        }, 1L, 20L);
    }

    @Override
    public Config getConfig() {
        return this.config;
    }

    @Override
    public Proxy getProxy() {
        return this.proxy;
    }

    @Override
    public DynamicGuiPlugin getPlugin() {
        return this.plugin;
    }

    @Override
    public Economy getEconomy() {
        return this.economy;
    }

    @Override
    public Permission getPermission() {
        return this.permission;
    }

    @Override
    public @Unmodifiable List<NPCRegistry> getNpcRegistries() {
        return Collections.unmodifiableList(this.npcRegistries);
    }

    @Override
    public void registerNPCRegistry(NPCRegistry npcRegistry) {
        this.npcRegistries.add(npcRegistry);
    }

    @Override
    public Platform getPlatform() {
        return this.platform;
    }

    @Override
    public LoggerWrapper<?> getLogger() {
        return this.loggerWrapper;
    }

    @Override
    public int getGlobalPlayerCount() {
        if (this.proxy == Proxy.NONE) {
            return this.platform.getLocalPlayerCount();
        }
        int globalPlayerCount = 0;
        for (int playerCount : this.serverPlayerCount.values()) {
            globalPlayerCount += playerCount;
        }
        return globalPlayerCount;
    }

    @Override
    public int getServerPlayerCount(String server) {
        return this.serverPlayerCount.getOrDefault(server, -1);
    }

    @Override
    public boolean sendToServer(PlayerWrapper<?> playerWrapper, String server) {
        if (this.platform != null && (this.proxy == Proxy.BUNGEE || this.proxy == Proxy.REDIS_BUNGEE)) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(server);
            playerWrapper.sendPluginMessage("BungeeCord", out.toByteArray());
            return true;
        }
        return false;
    }

    @Override
    public void inject(Object obj) {
        this.injector.injectMembers(obj);
    }
}