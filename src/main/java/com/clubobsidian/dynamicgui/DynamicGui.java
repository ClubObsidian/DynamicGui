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
package com.clubobsidian.dynamicgui;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.clubobsidian.dynamicgui.function.impl.meta.CopyBackMetadataFunction;
import com.clubobsidian.dynamicgui.function.impl.IsBedrockPlayerFunction;
import com.clubobsidian.dynamicgui.function.impl.meta.IsGuiMetadataSet;
import com.clubobsidian.dynamicgui.registry.replacer.impl.MetadataReplacerRegistry;
import org.apache.commons.io.FileUtils;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.impl.AddPermissionFunction;
import com.clubobsidian.dynamicgui.function.impl.CheckItemTypeInHandFunction;
import com.clubobsidian.dynamicgui.function.impl.CheckLevelFunction;
import com.clubobsidian.dynamicgui.function.impl.CheckMoveableFunction;
import com.clubobsidian.dynamicgui.function.impl.CheckPlayerWorldFunction;
import com.clubobsidian.dynamicgui.function.impl.ConsoleCmdFunction;
import com.clubobsidian.dynamicgui.function.impl.GetGameRuleFunction;
import com.clubobsidian.dynamicgui.function.impl.LogFunction;
import com.clubobsidian.dynamicgui.function.impl.MoneyBalanceFunction;
import com.clubobsidian.dynamicgui.function.impl.MoneyDepositFunction;
import com.clubobsidian.dynamicgui.function.impl.NoPermissionFunction;
import com.clubobsidian.dynamicgui.function.impl.ParticleFunction;
import com.clubobsidian.dynamicgui.function.impl.MoneyWithdrawFunction;
import com.clubobsidian.dynamicgui.function.impl.PermissionFunction;
import com.clubobsidian.dynamicgui.function.impl.PlayerCmdFunction;
import com.clubobsidian.dynamicgui.function.impl.PlayerMiniMsgFunction;
import com.clubobsidian.dynamicgui.function.impl.PlayerMsgFunction;
import com.clubobsidian.dynamicgui.function.impl.RandomFunction;
import com.clubobsidian.dynamicgui.function.impl.RemovePermissionFunction;
import com.clubobsidian.dynamicgui.function.impl.RemoveSlotFunction;
import com.clubobsidian.dynamicgui.function.impl.ResetFrameFunction;
import com.clubobsidian.dynamicgui.function.impl.ResetTickFunction;
import com.clubobsidian.dynamicgui.function.impl.SendFunction;
import com.clubobsidian.dynamicgui.function.impl.ServerBroadcastFunction;
import com.clubobsidian.dynamicgui.function.impl.ServerMiniBroadcastFunction;
import com.clubobsidian.dynamicgui.function.impl.SetAmountFunction;
import com.clubobsidian.dynamicgui.function.impl.SetCloseFunction;
import com.clubobsidian.dynamicgui.function.impl.SetDataFunction;
import com.clubobsidian.dynamicgui.function.impl.SetEnchantsFunction;
import com.clubobsidian.dynamicgui.function.impl.SetGameRuleFunction;
import com.clubobsidian.dynamicgui.function.impl.SetGlowFunction;
import com.clubobsidian.dynamicgui.function.impl.SetLoreFunction;
import com.clubobsidian.dynamicgui.function.impl.SetMoveableFunction;
import com.clubobsidian.dynamicgui.function.impl.SetNBTFunction;
import com.clubobsidian.dynamicgui.function.impl.SetNameFunction;
import com.clubobsidian.dynamicgui.function.impl.SetTypeFunction;
import com.clubobsidian.dynamicgui.function.impl.SoundFunction;
import com.clubobsidian.dynamicgui.function.impl.StatisticFunction;
import com.clubobsidian.dynamicgui.function.impl.condition.CheckTickFunction;
import com.clubobsidian.dynamicgui.function.impl.condition.ConditionFunction;
import com.clubobsidian.dynamicgui.function.impl.cooldown.IsNotOnCooldownFunction;
import com.clubobsidian.dynamicgui.function.impl.cooldown.IsOnCooldownFunction;
import com.clubobsidian.dynamicgui.function.impl.cooldown.SetCooldownFunction;
import com.clubobsidian.dynamicgui.function.impl.gui.BackFunction;
import com.clubobsidian.dynamicgui.function.impl.gui.GuiFunction;
import com.clubobsidian.dynamicgui.function.impl.gui.HasBackFunction;
import com.clubobsidian.dynamicgui.function.impl.gui.RefreshGuiFunction;
import com.clubobsidian.dynamicgui.function.impl.gui.RefreshSlotFunction;
import com.clubobsidian.dynamicgui.function.impl.gui.SetBackFunction;
import com.clubobsidian.dynamicgui.function.impl.meta.HasMetadataFunction;
import com.clubobsidian.dynamicgui.function.impl.meta.SetMetadataFunction;
import com.clubobsidian.dynamicgui.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.manager.dynamicgui.SlotManager;
import com.clubobsidian.dynamicgui.manager.dynamicgui.cooldown.CooldownManager;
import com.clubobsidian.dynamicgui.manager.dynamicgui.AnimationReplacerManager;
import com.clubobsidian.dynamicgui.manager.dynamicgui.FunctionManager;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.manager.dynamicgui.ReplacerManager;
import com.clubobsidian.dynamicgui.messaging.MessagingRunnable;
import com.clubobsidian.dynamicgui.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.proxy.Proxy;
import com.clubobsidian.dynamicgui.registry.replacer.impl.CooldownReplacerRegistry;
import com.clubobsidian.dynamicgui.registry.replacer.impl.DynamicGuiAnimationReplacerRegistry;
import com.clubobsidian.dynamicgui.registry.replacer.impl.DynamicGuiReplacerRegistry;
import com.clubobsidian.dynamicgui.replacer.Replacer;
import com.clubobsidian.dynamicgui.server.FakeServer;
import com.clubobsidian.dynamicgui.util.ChatColor;
import com.clubobsidian.trident.EventBus;
import com.clubobsidian.trident.eventbus.reflection.ReflectionEventBus;
import com.clubobsidian.wrappy.Configuration;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class DynamicGui {

    @Inject
    private static DynamicGui instance;

    public static DynamicGui get() {
        if (!instance.initialized) {
            instance.initialized = true;
            instance.init();
        }

        return instance;
    }

    private String noGui;
    private Proxy proxy;
    private Map<String, Integer> serverPlayerCount;
    private EventBus eventManager;
    private DynamicGuiPlugin plugin;
    private FakeServer server;
    private LoggerWrapper<?> loggerWrapper;
    private Injector injector;
    private boolean initialized;

    @Inject
    private DynamicGui(DynamicGuiPlugin plugin, FakeServer server, LoggerWrapper<?> loggerWrapper, Injector injector) {
        this.plugin = plugin;
        this.server = server;
        this.loggerWrapper = loggerWrapper;
        this.injector = injector;
        this.serverPlayerCount = new ConcurrentHashMap<>();
        this.initialized = false;
        this.setupFileStructure();
        this.saveDefaultConfig();
        this.eventManager = new ReflectionEventBus();
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
                FileUtils
                        .copyInputStreamToFile(this.getClass().getClassLoader().getResourceAsStream("config.yml"),
                                this.plugin.getConfigFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadConfig() {
        Configuration config = Configuration.load(this.plugin.getConfigFile());
        this.noGui = ChatColor.translateAlternateColorCodes('&', config.getString("no-gui"));
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

        this.proxy = this.findProxyByString(proxyStr);

        for (final String server : config.getStringList("servers")) {
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
            this.getServer().registerOutgoingPluginChannel(this.getPlugin(), "BungeeCord");
            this.getServer().registerIncomingPluginChannel(this.getPlugin(), "BungeeCord", runnable);
        } else if (this.proxy == Proxy.REDIS_BUNGEE) {
            this.getLogger().info("RedisBungee is enabled");
            this.getServer().registerOutgoingPluginChannel(this.getPlugin(), "RedisBungee");
            this.getServer().registerOutgoingPluginChannel(this.getPlugin(), "BungeeCord");
            this.getServer().registerIncomingPluginChannel(this.getPlugin(), "RedisBungee", runnable);
        } else {
            this.getLogger().info("A proxy is not in use, please configure the proxy config value if you need proxy support!");
        }

        if (this.proxy != Proxy.NONE) {
            this.startPlayerCountTimer();
        }
    }

    private void registerListeners() {
        this.eventManager.registerEvents(new com.clubobsidian.dynamicgui.listener.EntityClickListener());
        this.eventManager.registerEvents(new com.clubobsidian.dynamicgui.listener.InventoryInteractListener());
        this.eventManager.registerEvents(new com.clubobsidian.dynamicgui.listener.InventoryCloseListener());
        this.eventManager.registerEvents(new com.clubobsidian.dynamicgui.listener.InventoryOpenListener());
        this.eventManager.registerEvents(new com.clubobsidian.dynamicgui.listener.PlayerInteractListener());
        this.eventManager.registerEvents(new com.clubobsidian.dynamicgui.listener.GuiListener());
    }

    private void loadFunctions() {
        FunctionManager.get().addFunction(new CheckTickFunction("checktick"));
        FunctionManager.get().addFunction(new ConditionFunction("condition"));
        FunctionManager.get().addFunction(new ResetFrameFunction("resetframe"));
        FunctionManager.get().addFunction(new ResetTickFunction("resettick"));

        FunctionManager.get().addFunction(new ConsoleCmdFunction("executec"));
        FunctionManager.get().addFunction(new PlayerCmdFunction("executep"));
        //FunctionApi.get().addFunction(new ExpPayFunction("payexp"));

        FunctionManager.get().addFunction(new SetCooldownFunction("setcooldown"));
        FunctionManager.get().addFunction(new IsOnCooldownFunction("isoncooldown"));
        FunctionManager.get().addFunction(new IsNotOnCooldownFunction("isnotoncooldown"));

        FunctionManager.get().addFunction(new GuiFunction("gui"));
        FunctionManager.get().addFunction(new BackFunction("back"));
        FunctionManager.get().addFunction(new HasBackFunction("hasback"));
        FunctionManager.get().addFunction(new SetBackFunction("setback"));

        FunctionManager.get().addFunction(new RefreshGuiFunction("refreshgui"));
        FunctionManager.get().addFunction(new RefreshSlotFunction("refreshslot"));

        FunctionManager.get().addFunction(new MoneyWithdrawFunction("moneywithdraw"));
        FunctionManager.get().addFunction(new MoneyDepositFunction("moneydeposit"));
        FunctionManager.get().addFunction(new MoneyBalanceFunction("moneybalance"));

        FunctionManager.get().addFunction(new NoPermissionFunction("nopermission"));
        FunctionManager.get().addFunction(new PermissionFunction("permission"));
        FunctionManager.get().addFunction(new PermissionFunction("haspermission"));
        FunctionManager.get().addFunction(new AddPermissionFunction("addpermission"));
        FunctionManager.get().addFunction(new RemovePermissionFunction("removepermission"));
        FunctionManager.get().addFunction(new PlayerMsgFunction("pmsg"));
        FunctionManager.get().addFunction(new RandomFunction("random"));
        FunctionManager.get().addFunction(new SendFunction("send"));
        FunctionManager.get().addFunction(new ServerBroadcastFunction("broadcast"));
        FunctionManager.get().addFunction(new ParticleFunction("particles"));
        FunctionManager.get().addFunction(new SoundFunction("sound"));
        FunctionManager.get().addFunction(new SetNameFunction("setname"));
        FunctionManager.get().addFunction(new SetLoreFunction("setlore"));
        FunctionManager.get().addFunction(new SetTypeFunction("settype"));
        FunctionManager.get().addFunction(new SetDataFunction("setdata"));
        FunctionManager.get().addFunction(new SetAmountFunction("setamount"));
        FunctionManager.get().addFunction(new SetNBTFunction("setnbt"));
        FunctionManager.get().addFunction(new SetGlowFunction("setglow"));
        FunctionManager.get().addFunction(new CheckMoveableFunction("checkmoveable"));
        FunctionManager.get().addFunction(new SetMoveableFunction("setmoveable"));
        FunctionManager.get().addFunction(new SetEnchantsFunction("setenchants"));
        FunctionManager.get().addFunction(new SetCloseFunction("setclose"));
        FunctionManager.get().addFunction(new RemoveSlotFunction("removeslot"));
        FunctionManager.get().addFunction(new StatisticFunction("statistic"));
        FunctionManager.get().addFunction(new CheckLevelFunction("checklevel"));
        FunctionManager.get().addFunction(new LogFunction("log"));

        FunctionManager.get().addFunction(new CheckItemTypeInHandFunction("checkitemtypeinhand"));

        FunctionManager.get().addFunction(new SetGameRuleFunction("setgamerule"));
        FunctionManager.get().addFunction(new GetGameRuleFunction("getgamerule"));

        FunctionManager.get().addFunction(new CheckPlayerWorldFunction("checkplayerworld"));

        FunctionManager.get().addFunction(new PlayerMiniMsgFunction("minimsg"));
        FunctionManager.get().addFunction(new ServerMiniBroadcastFunction("minibroadcast"));

        FunctionManager.get().addFunction(new HasMetadataFunction("hasmetadata"));
        FunctionManager.get().addFunction(new HasMetadataFunction("getmetadata"));
        FunctionManager.get().addFunction(new SetMetadataFunction("setmetadata"));
        FunctionManager.get().addFunction(new IsGuiMetadataSet());
        FunctionManager.get().addFunction(new CopyBackMetadataFunction("copybackmetadata"));

        FunctionManager.get().addFunction(new IsBedrockPlayerFunction("isbedrockplayer"));
    }

    private void startPlayerCountTimer() {
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this.getPlugin(), () -> {
            for (String server : serverPlayerCount.keySet()) {
                PlayerWrapper<?> player = Iterables.getFirst(this.getServer().getOnlinePlayers(), null);
                if (player != null) {
                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    out.writeUTF("PlayerCount");
                    out.writeUTF(server);
                    String sendTo = "BungeeCord";
                    if (this.proxy == Proxy.REDIS_BUNGEE) {
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

    public DynamicGuiPlugin getPlugin() {
        return this.plugin;
    }

    public EventBus getEventBus() {
        return this.eventManager;
    }

    public FakeServer getServer() {
        return this.server;
    }

    public LoggerWrapper<?> getLogger() {
        return this.loggerWrapper;
    }

    public Integer getGlobalServerPlayerCount() {
        int globalPlayerCount = 0;
        for (int playerCount : this.serverPlayerCount.values()) {
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

    //Hack for checking for Java 8, temp work around for trident
	/*private EventBus getVersionEventBus()
	{
		String version = System.getProperty("java.version");
		if(version.startsWith("1.8") && !this.useReflectionEventBus)
		{
			return new JavassistEventBus();
		}
		
		this.loggerWrapper.info("Falling back to the reflection eventbus for better compatability");
		return new ReflectionEventBus();
	}*/

    private Proxy findProxyByString(String proxyStr) {
        if (proxyStr.equalsIgnoreCase("bungee") || proxyStr.equalsIgnoreCase("bungeecord")) {
            return Proxy.BUNGEECORD;
        } else if (proxyStr.equalsIgnoreCase("redis") || proxyStr.equalsIgnoreCase("redisbungee")) {
            return Proxy.REDIS_BUNGEE;
        }

        return Proxy.NONE;
    }

    public boolean sendToServer(PlayerWrapper<?> playerWrapper, String server) {
        if (this.server == null) {
            return false;
        } else if (this.proxy == Proxy.BUNGEECORD || this.proxy == Proxy.REDIS_BUNGEE) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(server);
            playerWrapper.sendPluginMessage(DynamicGui.get().getPlugin(), "BungeeCord", out.toByteArray());
            return true;
        }

        return false;
    }
}