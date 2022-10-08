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

import com.clubobsidian.dynamicgui.api.command.CommandRegistrar;
import com.clubobsidian.dynamicgui.core.command.DynamicGuiCommand;
import com.clubobsidian.dynamicgui.core.command.GuiCommand;
import com.clubobsidian.dynamicgui.core.config.ChatColorTransformer;
import com.clubobsidian.dynamicgui.core.config.Message;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.listener.EntityClickListener;
import com.clubobsidian.dynamicgui.core.listener.GuiListener;
import com.clubobsidian.dynamicgui.core.listener.InventoryCloseListener;
import com.clubobsidian.dynamicgui.core.listener.InventoryInteractListener;
import com.clubobsidian.dynamicgui.core.listener.PlayerInteractListener;
import com.clubobsidian.dynamicgui.core.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.AnimationReplacerManager;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.FunctionManager;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.SimpleGuiManager;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.ReplacerManager;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.SlotManager;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.cooldown.CooldownManager;
import com.clubobsidian.dynamicgui.core.messaging.MessagingRunnable;
import com.clubobsidian.dynamicgui.core.platform.Platform;
import com.clubobsidian.dynamicgui.core.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.core.proxy.Proxy;
import com.clubobsidian.dynamicgui.core.registry.replacer.impl.CooldownReplacerRegistry;
import com.clubobsidian.dynamicgui.core.registry.replacer.impl.DynamicGuiAnimationReplacerRegistry;
import com.clubobsidian.dynamicgui.core.registry.replacer.impl.DynamicGuiReplacerRegistry;
import com.clubobsidian.dynamicgui.core.registry.replacer.impl.MetadataReplacerRegistry;
import com.clubobsidian.dynamicgui.core.replacer.Replacer;
import com.clubobsidian.trident.EventBus;
import com.clubobsidian.trident.eventbus.methodhandle.MethodHandleEventBus;
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

import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicGui {

    @Inject
    private static DynamicGui instance;

    public static DynamicGui get() {
        return instance;
    }

    private Message message;
    private Proxy proxy;
    private String dateTimeFormat;
    private final Map<String, Integer> serverPlayerCount = new ConcurrentHashMap<>();
    private final EventBus eventBus = new MethodHandleEventBus();
    private final DynamicGuiPlugin plugin;
    private final Platform platform;
    private final LoggerWrapper<?> loggerWrapper;
    private final Injector injector;
    private final CommandRegistrar commandRegistrar;
    private boolean initialized;

    @Inject
    private DynamicGui(DynamicGuiPlugin plugin,
                       Platform platform,
                       LoggerWrapper<?> loggerWrapper,
                       Injector injector,
                       CommandRegistrar commandRegistrar) {
        this.plugin = plugin;
        this.platform = platform;
        this.loggerWrapper = loggerWrapper;
        this.commandRegistrar = commandRegistrar;
        this.injector = injector;
        this.initialized = false;
        this.setupFileStructure();
        this.saveDefaultConfig();
    }

    public boolean start() {
        if(!this.initialized) {
            this.initialized = true;
            this.loadConfig();
            this.loadFunctions();
            this.loadGuis();
            this.checkForProxy();
            this.registerListeners();
            this.registerCommands();
            ReplacerManager.get().registerReplacerRegistry(DynamicGuiReplacerRegistry.get());
            ReplacerManager.get().registerReplacerRegistry(CooldownReplacerRegistry.get());
            ReplacerManager.get().registerReplacerRegistry(MetadataReplacerRegistry.get());
            AnimationReplacerManager.get().registerReplacerRegistry(DynamicGuiAnimationReplacerRegistry.get());
            SlotManager.get();
            CooldownManager.get();
            return true;
        }
        return false;
    }

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

    private void loadConfig() {
        this.message = new Message();
        Configuration config = Configuration.load(this.plugin.getConfigFile());
        ConfigurationSection messageSection = config.getConfigurationSection("message");
        Collection<NodeTransformer> transformers = new ArrayList<>();
        transformers.add(new ChatColorTransformer());
        messageSection.inject(this.message, transformers);
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

        String dateTimeFormat = config.getString("date-time-format");
        if (dateTimeFormat == null) {
            dateTimeFormat = "MM dd, yyyy HH:mm:ss";
            config.set("date-time-format", dateTimeFormat);
            config.save();
        } else {
            dateTimeFormat = dateTimeFormat.trim();
        }

        this.dateTimeFormat = dateTimeFormat;

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
        SimpleGuiManager.get(); //Initialize manager
    }

    public void checkForProxy() {
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

        if (this.proxy == Proxy.BUNGEECORD) {
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

    private void loadFunctions() {
        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(Function.class.getPackageName())
                .scan()) {
            for (ClassInfo classInfo : scanResult.getSubclasses(Function.class)) {
                try {
                    FunctionManager.get().addFunction((Function) classInfo
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

    public Message getMessage() {
        return this.message;
    }

    @Deprecated
    public boolean getBungeeCord() {
        return this.proxy == Proxy.BUNGEECORD;
    }

    @Deprecated
    public boolean getRedisBungee() {
        return this.proxy == Proxy.REDIS_BUNGEE;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public String getDateTimeFormat() {
        return this.dateTimeFormat;
    }

    public DynamicGuiPlugin getPlugin() {
        return this.plugin;
    }

    public EventBus getEventBus() {
        return this.eventBus;
    }

    public Platform getPlatform() {
        return this.platform;
    }

    public LoggerWrapper<?> getLogger() {
        return this.loggerWrapper;
    }

    public int getGlobalServerPlayerCount() {
        int globalPlayerCount = 0;
        for (int playerCount : this.serverPlayerCount.values()) {
            globalPlayerCount += playerCount;
        }
        return globalPlayerCount;
    }

    public int getServerPlayerCount(String server) {
        return this.serverPlayerCount.getOrDefault(server, -1);
    }

    public Injector getInjector() {
        return this.injector;
    }

    public boolean sendToServer(PlayerWrapper<?> playerWrapper, String server) {
        if (this.platform != null && (this.proxy == Proxy.BUNGEECORD || this.proxy == Proxy.REDIS_BUNGEE)) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(server);
            playerWrapper.sendPluginMessage("BungeeCord", out.toByteArray());
            return true;
        }
        return false;
    }
}