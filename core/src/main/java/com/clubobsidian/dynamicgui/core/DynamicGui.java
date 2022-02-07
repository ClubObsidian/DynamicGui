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
package com.clubobsidian.dynamicgui.core;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.impl.AddPermissionFunction;
import com.clubobsidian.dynamicgui.core.function.impl.DelayFunction;
import com.clubobsidian.dynamicgui.core.function.impl.CheckItemTypeInHandFunction;
import com.clubobsidian.dynamicgui.core.function.impl.CheckLevelFunction;
import com.clubobsidian.dynamicgui.core.function.impl.CheckMoveableFunction;
import com.clubobsidian.dynamicgui.core.function.impl.CheckPlayerWorldFunction;
import com.clubobsidian.dynamicgui.core.function.impl.ConsoleCmdFunction;
import com.clubobsidian.dynamicgui.core.function.impl.GetGameRuleFunction;
import com.clubobsidian.dynamicgui.core.function.impl.IsBedrockPlayerFunction;
import com.clubobsidian.dynamicgui.core.function.impl.LogFunction;
import com.clubobsidian.dynamicgui.core.function.impl.MoneyBalanceFunction;
import com.clubobsidian.dynamicgui.core.function.impl.MoneyDepositFunction;
import com.clubobsidian.dynamicgui.core.function.impl.MoneyWithdrawFunction;
import com.clubobsidian.dynamicgui.core.function.impl.NoPermissionFunction;
import com.clubobsidian.dynamicgui.core.function.impl.ParticleFunction;
import com.clubobsidian.dynamicgui.core.function.impl.PermissionFunction;
import com.clubobsidian.dynamicgui.core.function.impl.PlayerCmdFunction;
import com.clubobsidian.dynamicgui.core.function.impl.PlayerMiniMsgFunction;
import com.clubobsidian.dynamicgui.core.function.impl.PlayerMsgFunction;
import com.clubobsidian.dynamicgui.core.function.impl.RandomFunction;
import com.clubobsidian.dynamicgui.core.function.impl.RemovePermissionFunction;
import com.clubobsidian.dynamicgui.core.function.impl.RemoveSlotFunction;
import com.clubobsidian.dynamicgui.core.function.impl.ResetFrameFunction;
import com.clubobsidian.dynamicgui.core.function.impl.ResetTickFunction;
import com.clubobsidian.dynamicgui.core.function.impl.SendFunction;
import com.clubobsidian.dynamicgui.core.function.impl.ServerBroadcastFunction;
import com.clubobsidian.dynamicgui.core.function.impl.ServerMiniBroadcastFunction;
import com.clubobsidian.dynamicgui.core.function.impl.SetAmountFunction;
import com.clubobsidian.dynamicgui.core.function.impl.SetCloseFunction;
import com.clubobsidian.dynamicgui.core.function.impl.SetDataFunction;
import com.clubobsidian.dynamicgui.core.function.impl.SetEnchantsFunction;
import com.clubobsidian.dynamicgui.core.function.impl.SetGameRuleFunction;
import com.clubobsidian.dynamicgui.core.function.impl.SetGlowFunction;
import com.clubobsidian.dynamicgui.core.function.impl.SetLoreFunction;
import com.clubobsidian.dynamicgui.core.function.impl.SetMoveableFunction;
import com.clubobsidian.dynamicgui.core.function.impl.SetNBTFunction;
import com.clubobsidian.dynamicgui.core.function.impl.SetNameFunction;
import com.clubobsidian.dynamicgui.core.function.impl.SetTypeFunction;
import com.clubobsidian.dynamicgui.core.function.impl.SoundFunction;
import com.clubobsidian.dynamicgui.core.function.impl.condition.CheckTickFunction;
import com.clubobsidian.dynamicgui.core.function.impl.condition.ConditionFunction;
import com.clubobsidian.dynamicgui.core.function.impl.cooldown.IsNotOnCooldownFunction;
import com.clubobsidian.dynamicgui.core.function.impl.cooldown.IsOnCooldownFunction;
import com.clubobsidian.dynamicgui.core.function.impl.cooldown.SetCooldownFunction;
import com.clubobsidian.dynamicgui.core.function.impl.gui.BackFunction;
import com.clubobsidian.dynamicgui.core.function.impl.gui.GuiFunction;
import com.clubobsidian.dynamicgui.core.function.impl.gui.HasBackFunction;
import com.clubobsidian.dynamicgui.core.function.impl.gui.RefreshGuiFunction;
import com.clubobsidian.dynamicgui.core.function.impl.gui.RefreshSlotFunction;
import com.clubobsidian.dynamicgui.core.function.impl.gui.SetBackFunction;
import com.clubobsidian.dynamicgui.core.function.impl.meta.CopyBackMetadataFunction;
import com.clubobsidian.dynamicgui.core.function.impl.meta.HasMetadataFunction;
import com.clubobsidian.dynamicgui.core.function.impl.meta.IsGuiMetadataSet;
import com.clubobsidian.dynamicgui.core.function.impl.meta.SetMetadataFunction;
import com.clubobsidian.dynamicgui.core.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.core.listener.EntityClickListener;
import com.clubobsidian.dynamicgui.core.listener.GuiListener;
import com.clubobsidian.dynamicgui.core.listener.InventoryCloseListener;
import com.clubobsidian.dynamicgui.core.listener.InventoryInteractListener;
import com.clubobsidian.dynamicgui.core.listener.InventoryOpenListener;
import com.clubobsidian.dynamicgui.core.listener.PlayerInteractListener;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.AnimationReplacerManager;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.FunctionManager;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.ReplacerManager;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.SlotManager;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.cooldown.CooldownManager;
import com.clubobsidian.dynamicgui.core.messaging.MessagingRunnable;
import com.clubobsidian.dynamicgui.core.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.core.proxy.Proxy;
import com.clubobsidian.dynamicgui.core.registry.replacer.impl.CooldownReplacerRegistry;
import com.clubobsidian.dynamicgui.core.registry.replacer.impl.DynamicGuiAnimationReplacerRegistry;
import com.clubobsidian.dynamicgui.core.registry.replacer.impl.DynamicGuiReplacerRegistry;
import com.clubobsidian.dynamicgui.core.registry.replacer.impl.MetadataReplacerRegistry;
import com.clubobsidian.dynamicgui.core.replacer.Replacer;
import com.clubobsidian.dynamicgui.core.server.Platform;
import com.clubobsidian.dynamicgui.core.util.ChatColor;
import com.clubobsidian.trident.EventBus;
import com.clubobsidian.trident.eventbus.methodhandle.MethodHandleEventBus;
import com.clubobsidian.wrappy.Configuration;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicGui {

    @Inject
    private static DynamicGui instance;

    public static DynamicGui get() {
        if(!instance.initialized) {
            instance.initialized = true;
            instance.init();
        }

        return instance;
    }

    private String noGui;
    private Proxy proxy;
    private String dateTimeFormat;
    private final Map<String, Integer> serverPlayerCount;
    private final EventBus eventManager;
    private final DynamicGuiPlugin plugin;
    private final Platform platform;
    private final LoggerWrapper<?> loggerWrapper;
    private final Injector injector;
    private boolean initialized;

    @Inject
    private DynamicGui(DynamicGuiPlugin plugin, Platform platform, LoggerWrapper<?> loggerWrapper, Injector injector) {
        this.plugin = plugin;
        this.platform = platform;
        this.loggerWrapper = loggerWrapper;
        this.injector = injector;
        this.serverPlayerCount = new ConcurrentHashMap<>();
        this.initialized = false;
        this.setupFileStructure();
        this.saveDefaultConfig();
        this.eventManager = new MethodHandleEventBus();
    }

    private void init() {
        this.loadConfig();
        this.loadFunctions();
        this.loadGuis();
        this.checkForProxy();
        this.registerListeners();
        ReplacerManager.get().registerReplacerRegistry(DynamicGuiReplacerRegistry.get());
        ReplacerManager.get().registerReplacerRegistry(CooldownReplacerRegistry.get());
        ReplacerManager.get().registerReplacerRegistry(MetadataReplacerRegistry.get());
        AnimationReplacerManager.get().registerReplacerRegistry(DynamicGuiAnimationReplacerRegistry.get());
        SlotManager.get();
        CooldownManager.get();
    }

    public void shutdown() {
        CooldownManager.get().shutdown();
    }

    private void setupFileStructure() {
        if(!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdirs();
        }

        if(!this.plugin.getGuiFolder().exists()) {
            this.plugin.getGuiFolder().mkdirs();
        }

        if(!this.plugin.getMacroFolder().exists()) {
            this.plugin.getMacroFolder().mkdirs();
        }
    }

    private void saveDefaultConfig() {
        if(!this.plugin.getConfigFile().exists()) {
            try {
                FileUtils
                        .copyInputStreamToFile(this.getClass().getClassLoader().getResourceAsStream("config.yml"),
                                this.plugin.getConfigFile());
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadConfig() {
        Configuration config = Configuration.load(this.plugin.getConfigFile());
        this.noGui = ChatColor.translateAlternateColorCodes('&', config.getString("no-gui"));
        String version = config.getString("version");
        if(version != null) {
            version = version.trim();
        }

        String proxyStr = config.getString("proxy");
        if(proxyStr == null) {
            proxyStr = version;
            config.set("proxy", proxyStr);
            config.save();
        } else {
            proxyStr = proxyStr.trim();
        }

        this.proxy = this.findProxyByString(proxyStr);

        String dateTimeFormat = config.getString("date-time-format");
        if(dateTimeFormat == null) {
            dateTimeFormat = "MM dd, yyyy HH:mm:ss";
            config.set("date-time-format", dateTimeFormat);
            config.save();
        } else {
            dateTimeFormat = dateTimeFormat.trim();
        }

        this.dateTimeFormat = dateTimeFormat;

        for(final String server : config.getStringList("servers")) {
            this.serverPlayerCount.put(server, 0);

            DynamicGuiReplacerRegistry.get().addReplacer(new Replacer("%" + server + "-playercount%") {
                @Override
                public String replacement(String text, PlayerWrapper<?> player) {
                    return String.valueOf(serverPlayerCount.get(server));
                }
            });
        }
    }

    private void loadGuis() {
        GuiManager.get(); //Initialize manager
    }

    public void checkForProxy() {
        MessagingRunnable runnable = (playerWrapper, message) ->
        {
            if(message.length > 13) {
                ByteArrayDataInput in = ByteStreams.newDataInput(message);
                String packet = in.readUTF();
                if(packet != null) {
                    if("PlayerCount".equals(packet)) {
                        String server = in.readUTF();
                        int playerCount = in.readInt();
                        this.serverPlayerCount.put(server, playerCount);
                    }
                }
            }
        };

        if(this.proxy == Proxy.BUNGEECORD) {
            this.getLogger().info("BungeeCord is enabled!");
            this.getPlatform().registerOutgoingPluginChannel(this.getPlugin(), "BungeeCord");
            this.getPlatform().registerIncomingPluginChannel(this.getPlugin(), "BungeeCord", runnable);
        } else if(this.proxy == Proxy.REDIS_BUNGEE) {
            this.getLogger().info("RedisBungee is enabled");
            this.getPlatform().registerOutgoingPluginChannel(this.getPlugin(), "RedisBungee");
            this.getPlatform().registerOutgoingPluginChannel(this.getPlugin(), "BungeeCord");
            this.getPlatform().registerIncomingPluginChannel(this.getPlugin(), "RedisBungee", runnable);
        } else {
            this.getLogger().info("A proxy is not in use, please configure the proxy config value if you need proxy support!");
        }

        if(this.proxy != Proxy.NONE) {
            this.startPlayerCountTimer();
        }
    }

    private void registerListeners() {
        this.eventManager.registerEvents(new EntityClickListener());
        this.eventManager.registerEvents(new InventoryInteractListener());
        this.eventManager.registerEvents(new InventoryCloseListener());
        this.eventManager.registerEvents(new InventoryOpenListener());
        this.eventManager.registerEvents(new PlayerInteractListener());
        this.eventManager.registerEvents(new GuiListener());
    }

    private void loadFunctions() {
        FunctionManager.get().addFunction(new CheckTickFunction());
        FunctionManager.get().addFunction(new ConditionFunction());
        FunctionManager.get().addFunction(new ResetFrameFunction());
        FunctionManager.get().addFunction(new ResetTickFunction());

        FunctionManager.get().addFunction(new ConsoleCmdFunction());
        FunctionManager.get().addFunction(new PlayerCmdFunction());

        FunctionManager.get().addFunction(new SetCooldownFunction());
        FunctionManager.get().addFunction(new IsOnCooldownFunction());
        FunctionManager.get().addFunction(new IsNotOnCooldownFunction());

        FunctionManager.get().addFunction(new GuiFunction());
        FunctionManager.get().addFunction(new BackFunction());
        FunctionManager.get().addFunction(new HasBackFunction());
        FunctionManager.get().addFunction(new SetBackFunction());

        FunctionManager.get().addFunction(new RefreshGuiFunction());
        FunctionManager.get().addFunction(new RefreshSlotFunction());

        FunctionManager.get().addFunction(new MoneyWithdrawFunction());
        FunctionManager.get().addFunction(new MoneyDepositFunction());
        FunctionManager.get().addFunction(new MoneyBalanceFunction());

        FunctionManager.get().addFunction(new NoPermissionFunction());
        FunctionManager.get().addFunction(new PermissionFunction());
        FunctionManager.get().addFunction(new AddPermissionFunction());
        FunctionManager.get().addFunction(new RemovePermissionFunction());
        FunctionManager.get().addFunction(new PlayerMsgFunction());
        FunctionManager.get().addFunction(new RandomFunction());
        FunctionManager.get().addFunction(new SendFunction());
        FunctionManager.get().addFunction(new ServerBroadcastFunction());
        FunctionManager.get().addFunction(new ParticleFunction());
        FunctionManager.get().addFunction(new SoundFunction());
        FunctionManager.get().addFunction(new SetNameFunction());
        FunctionManager.get().addFunction(new SetLoreFunction());
        FunctionManager.get().addFunction(new SetTypeFunction());
        FunctionManager.get().addFunction(new SetDataFunction());
        FunctionManager.get().addFunction(new SetAmountFunction());
        FunctionManager.get().addFunction(new SetNBTFunction());
        FunctionManager.get().addFunction(new SetGlowFunction());
        FunctionManager.get().addFunction(new CheckMoveableFunction());
        FunctionManager.get().addFunction(new SetMoveableFunction());
        FunctionManager.get().addFunction(new SetEnchantsFunction());
        FunctionManager.get().addFunction(new SetCloseFunction());
        FunctionManager.get().addFunction(new RemoveSlotFunction());
        FunctionManager.get().addFunction(new CheckLevelFunction());
        FunctionManager.get().addFunction(new LogFunction());

        FunctionManager.get().addFunction(new CheckItemTypeInHandFunction());

        FunctionManager.get().addFunction(new SetGameRuleFunction());
        FunctionManager.get().addFunction(new GetGameRuleFunction());

        FunctionManager.get().addFunction(new CheckPlayerWorldFunction());

        FunctionManager.get().addFunction(new PlayerMiniMsgFunction());
        FunctionManager.get().addFunction(new ServerMiniBroadcastFunction());

        FunctionManager.get().addFunction(new HasMetadataFunction());
        FunctionManager.get().addFunction(new SetMetadataFunction());
        FunctionManager.get().addFunction(new IsGuiMetadataSet());
        FunctionManager.get().addFunction(new CopyBackMetadataFunction());

        FunctionManager.get().addFunction(new IsBedrockPlayerFunction());

        FunctionManager.get().addFunction(new DelayFunction());
    }

    private void startPlayerCountTimer() {
        this.getPlatform().getScheduler().scheduleSyncRepeatingTask(() -> {
            for(String server : serverPlayerCount.keySet()) {
                PlayerWrapper<?> player = Iterables.getFirst(this.getPlatform().getOnlinePlayers(), null);
                if(player != null) {
                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    out.writeUTF("PlayerCount");
                    out.writeUTF(server);
                    String sendTo = "BungeeCord";
                    if(this.proxy == Proxy.REDIS_BUNGEE) {
                        sendTo = "RedisBungee";
                    }
                    player.sendPluginMessage(this.getPlugin(), sendTo, out.toByteArray());
                }
            }
        }, 1L, 20L);
    }

    public String getNoGui() {
        return this.noGui;
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
        return this.eventManager;
    }

    public Platform getPlatform() {
        return this.platform;
    }

    public LoggerWrapper<?> getLogger() {
        return this.loggerWrapper;
    }

    public Integer getGlobalServerPlayerCount() {
        int globalPlayerCount = 0;
        for(int playerCount : this.serverPlayerCount.values()) {
            globalPlayerCount += playerCount;
        }

        return globalPlayerCount;
    }

    public Integer getServerPlayerCount(String server) {
        return this.serverPlayerCount.get(server);
    }

    public Injector getInjector() {
        return this.injector;
    }

    private Proxy findProxyByString(String proxyStr) {
        if(proxyStr.equalsIgnoreCase("bungee") || proxyStr.equalsIgnoreCase("bungeecord")) {
            return Proxy.BUNGEECORD;
        } else if(proxyStr.equalsIgnoreCase("redis") || proxyStr.equalsIgnoreCase("redisbungee")) {
            return Proxy.REDIS_BUNGEE;
        }

        return Proxy.NONE;
    }

    public boolean sendToServer(PlayerWrapper<?> playerWrapper, String server) {
        if(this.platform == null) {
            return false;
        } else if(this.proxy == Proxy.BUNGEECORD || this.proxy == Proxy.REDIS_BUNGEE) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(server);
            playerWrapper.sendPluginMessage(DynamicGui.get().getPlugin(), "BungeeCord", out.toByteArray());
            return true;
        }

        return false;
    }
}