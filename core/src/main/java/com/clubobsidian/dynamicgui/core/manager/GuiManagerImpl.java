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

package com.clubobsidian.dynamicgui.core.manager;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.command.CommandRegistrar;
import com.clubobsidian.dynamicgui.api.enchantment.EnchantmentWrapper;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.factory.GuiFactory;
import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.gui.GuiBuildType;
import com.clubobsidian.dynamicgui.api.gui.Slot;
import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.api.logger.LoggerWrapper;
import com.clubobsidian.dynamicgui.api.manager.FunctionManager;
import com.clubobsidian.dynamicgui.api.manager.gui.GuiManager;
import com.clubobsidian.dynamicgui.api.manager.material.MaterialManager;
import com.clubobsidian.dynamicgui.api.manager.world.LocationManager;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.api.parser.gui.GuiToken;
import com.clubobsidian.dynamicgui.api.parser.macro.MacroToken;
import com.clubobsidian.dynamicgui.api.parser.slot.SlotToken;
import com.clubobsidian.dynamicgui.api.platform.Platform;
import com.clubobsidian.dynamicgui.api.platform.PlatformType;
import com.clubobsidian.dynamicgui.api.plugin.DynamicGuiPlugin;
import com.clubobsidian.dynamicgui.api.world.LocationWrapper;
import com.clubobsidian.dynamicgui.core.event.inventory.GuiLoadEvent;
import com.clubobsidian.dynamicgui.core.event.inventory.GuiPreloadEvent;
import com.clubobsidian.dynamicgui.core.event.inventory.GuiSwitchEvent;
import com.clubobsidian.dynamicgui.core.gui.SimpleSlot;
import com.clubobsidian.dynamicgui.core.util.ChatColor;
import com.clubobsidian.dynamicgui.core.util.HashUtil;
import com.clubobsidian.dynamicgui.core.util.ThreadUtil;
import com.clubobsidian.dynamicgui.parser.gui.SimpleGuiToken;
import com.clubobsidian.dynamicgui.parser.macro.SimpleMacroToken;
import com.clubobsidian.trident.EventBus;
import com.clubobsidian.wrappy.Configuration;
import com.clubobsidian.wrappy.ConfigurationSection;
import org.apache.commons.io.FileUtils;

import javax.inject.Inject;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiManagerImpl extends GuiManager {

    private static Gui getOrCloneGui(Gui gui) {
        return gui.isStatic() ? gui : gui.clone();
    }

    private Map<String, Gui> guis;
    private final Map<UUID, Gui> playerGuis = new HashMap<>();
    private Map<String, Gui> cachedGuis;
    private Map<String, GuiToken> cachedTokens;
    private Map<String, List<MacroToken>> globalMacros;
    private Map<String, List<MacroToken>> cachedGlobalMacros;
    private Map<String, byte[]> guiHashes;
    private Map<String, byte[]> globalMacrosTimestamps;
    private final Set<String> modifiedMacros = new HashSet<>();
    private final CommandRegistrar commandRegistrar;
    private final Platform platform;
    private final GuiFactory factory;
    private final EventBus eventBus;
    private boolean initialized = false;

    @Inject
    private GuiManagerImpl(CommandRegistrar commandRegistrar, Platform platform,
                           GuiFactory factory, EventBus eventBus) {
        this.guis = new HashMap<>();
        this.cachedGuis = new HashMap<>();
        this.cachedTokens = new HashMap<>();
        this.globalMacros = new LinkedHashMap<>();
        this.cachedGlobalMacros = new HashMap<>();
        this.guiHashes = new HashMap<>();
        this.globalMacrosTimestamps = new HashMap<>();
        this.commandRegistrar = commandRegistrar;
        this.platform = platform;
        this.factory = factory;
        this.eventBus = eventBus;
    }

    @Override
    public boolean initialize() {
        if (!this.initialized) {
            this.loadGlobalMacros();
            this.loadGuis();
            this.initialized = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean isGuiLoaded(String name) {
        return this.guis.containsKey(name);
    }

    @Override
    public Gui getGui(String name) {
        Gui gui = this.guis.get(name);
        if (gui != null) {
            return getOrCloneGui(gui);
        }
        return null;
    }

    @Override
    public void reloadGuis(boolean force) {
        DynamicGui.get().getLogger().info("Reloading guis!");
        this.commandRegistrar.unregisterGuiAliases();
        this.cachedGuis = this.guis;
        this.guis = new HashMap<>();
        this.cachedGlobalMacros = this.globalMacros;
        this.globalMacros = new HashMap<>();
        if (force) {
            this.cachedTokens = new HashMap<>();
            this.cachedGuis = new HashMap<>();
            this.guiHashes = new HashMap<>();
            this.globalMacrosTimestamps = new HashMap<>();
            this.cachedGlobalMacros = new HashMap<>();
        }

        this.loadGlobalMacros();
        this.loadGuis();
        this.platform.syncCommands();
    }

    @Override
    public List<Gui> getGuis() {
        return List.copyOf(this.guis.values());
    }

    @Override
    protected Map<UUID, Gui> getPlayerGuisInternal() {
        return this.playerGuis;
    }

    @Override
    public CompletableFuture<Boolean> openGui(PlayerWrapper<?> playerWrapper,
                                              Gui gui,
                                              Gui back,
                                              Map<String, String> metadata) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        future.exceptionally((ex) -> {
            ex.printStackTrace();
            return null;
        });
        ThreadUtil.run(() -> {
            if (gui == null) {
                playerWrapper.sendMessage(DynamicGui.get().getConfig().getMessage().getNoGui());
                future.complete(false);
                return;
            }

            Gui clonedGui = getOrCloneGui(gui);
            if (back != null) {
                clonedGui.setBack(back.clone());
            }
            clonedGui.getMetadata().putAll(metadata);

            GuiPreloadEvent preloadEvent = new GuiPreloadEvent(clonedGui, playerWrapper);
            this.eventBus.callEvent(preloadEvent);

            //Run gui load functions
            CompletableFuture<Boolean> result = FunctionManager.get().tryFunctions(clonedGui, FunctionType.LOAD, playerWrapper);
            result.exceptionally((ex) -> {
                ex.printStackTrace();
                return null;
            });
            result.thenAccept((ran) -> ThreadUtil.run(() -> {
                GuiLoadEvent event = new GuiLoadEvent(clonedGui, playerWrapper);
                if (!ran) {
                    event.setCancelled(true);
                }
                this.eventBus.callEvent(event);

                if (ran) {
                    Gui currentGui = this.getPlayerGui(playerWrapper);
                    if (back != null && back.equals(currentGui)) {
                        this.eventBus.callEvent(new GuiSwitchEvent(back, clonedGui, playerWrapper));
                    }
                    InventoryWrapper<?> inventoryWrapper = clonedGui.buildInventory(playerWrapper);
                    if (inventoryWrapper == null) {
                        future.complete(false);
                        return;
                    }

                    //Run slot load functions
                    CompletableFuture<Boolean> slotFuture = new CompletableFuture<>();
                    List<Slot> slots = clonedGui.getSlots();
                    int slotSize = slots.size();
                    AtomicInteger slotCount = new AtomicInteger(0);
                    for (int i = 0; i < slotSize; i++) {
                        if (slotFuture.isDone()) {
                            return;
                        }
                        Slot slot = slots.get(i);
                        FunctionManager.get()
                                .tryFunctions(slot, FunctionType.LOAD, playerWrapper)
                                .whenComplete((slotResult, ex) -> {
                                    if (slotFuture.isDone()) {
                                        return;
                                    }
                                    if (ex != null) {
                                        ex.printStackTrace();
                                        slotFuture.complete(false);
                                    } else {
                                        int count = slotCount.incrementAndGet();
                                        if (slotSize == count) {
                                            slotFuture.complete(true);
                                        }
                                    }
                                });
                    }
                    if (slots.isEmpty()) {
                        slotFuture.complete(true);
                    }
                    slotFuture.whenComplete((completed, ex) -> {
                        if (ex != null) {
                            ex.printStackTrace();
                            future.complete(false);
                        } else {
                            ThreadUtil.run(() -> {
                                Platform platform = DynamicGui.get().getPlatform();
                                if (platform.getType() == PlatformType.SPONGE) {
                                    platform.getScheduler().runSyncDelayedTask(() -> {
                                        playerWrapper.openInventory(inventoryWrapper);
                                    }, 1L);
                                } else {
                                    playerWrapper.openInventory(inventoryWrapper);
                                }
                                this.playerGuis.put(playerWrapper.getUniqueId(), clonedGui);
                                platform.getScheduler().runSyncDelayedTask(() -> {
                                    playerWrapper.updateInventory();
                                }, 2L);
                                future.complete(true);
                            }, false);
                        }
                    });
                } else {
                    future.complete(false);
                }
            }, false));
        }, false);
        return future;
    }

    private void loadGlobalMacroFromFile(File file) {
        String macroName = file.getName().substring(0, file.getName().lastIndexOf("."));
        byte[] fileHash = HashUtil.getMD5(file);
        byte[] cachedHash = this.globalMacrosTimestamps.get(macroName);
        if (cachedHash == null || !Arrays.equals(fileHash, cachedHash)) {
            List<MacroToken> tokens = new ArrayList<>();
            Configuration config = Configuration.load(file);
            for (Object key : config.getKeys()) {
                ConfigurationSection section = config.getConfigurationSection(key);
                MacroToken token = new SimpleMacroToken(section);
                tokens.add(token);
            }
            this.modifiedMacros.add(macroName);
            this.globalMacrosTimestamps.put(macroName, fileHash);
            this.globalMacros.put(macroName, tokens);
        } else {
            List<MacroToken> cachedTokens = this.cachedGlobalMacros.get(macroName);
            this.globalMacros.put(macroName, cachedTokens);
        }
    }

    private void loadGlobalMacros() {
        File macroFolder = DynamicGui.get().getPlugin().getMacroFolder();

        Collection<File> macroFiles = FileUtils.listFiles(macroFolder, new String[]{"yml", "json", "conf", "xml"}, true);

        for (File file : macroFiles) {
            this.loadGlobalMacroFromFile(file);
        }

        Iterator<Entry<String, List<MacroToken>>> it = this.cachedGlobalMacros.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, List<MacroToken>> next = it.next();
            String macroName = next.getKey();
            if (!this.globalMacros.containsKey(macroName)) {
                it.remove();
                this.globalMacrosTimestamps.remove(macroName);
            }
        }
    }

    private void loadGuis() {
        this.loadFileGuis();
        this.loadRemoteGuis();
        this.modifiedMacros.clear();
        this.cleanupGuis();
    }

    private void loadGuiFromFile(Configuration yaml, File file) {
        DynamicGui dynamicGui = DynamicGui.get();
        try {
            String guiName = file.getName().substring(0, file.getName().lastIndexOf("."));
            Gui cachedGui = this.cachedGuis.get(guiName);
            byte[] cachedHash = this.guiHashes.get(guiName);
            GuiToken token = this.cachedTokens.get(guiName);
            byte[] guiHash = HashUtil.getMD5(file);
            if (cachedGui != null
                    && token != null
                    && cachedHash != null
                    && Arrays.equals(cachedHash, guiHash)
                    && !hasUpdatedMacro(token)) {
                for (String alias : token.getAlias()) {
                    this.commandRegistrar.registerGuiAliasCommand(guiName, alias, token.getCommandArguments());
                }

                this.guis.put(guiName, cachedGui);
                dynamicGui.getLogger().info("Cached gui '%s' has been loaded!", guiName);
            } else {
                this.guiHashes.put(guiName, guiHash);
                this.loadGuiFromConfiguration(guiName, yaml);
            }
        } catch (NullPointerException ex) {
            dynamicGui.getLogger().info("Error loading in file '%s'", file.getName());
            ex.printStackTrace();
        }
    }

    private void loadFileGuis() {
        DynamicGui dynamicGui = DynamicGui.get();
        DynamicGuiPlugin plugin = dynamicGui.getPlugin();
        File guiFolder = plugin.getGuiFolder();

        Collection<File> ar = FileUtils.listFiles(guiFolder, new String[]{"yml", "json", "conf", "xml"}, true);

        if (ar.size() != 0) {
            for (File file : ar) {
                Configuration config = Configuration.load(file);
                this.loadGuiFromFile(config, file);
            }
        } else {
            DynamicGui.get().getLogger().error("No guis found, please add guis or issues may occur!");
        }
    }

    private boolean hasUpdatedMacro(GuiToken token) {
        List<String> macros = token.getLoadMacros();
        for (String macro : macros) {
            if (this.modifiedMacros.contains(macro)) {
                return true;
            }
        }

        return false;
    }

    private void loadRemoteGui(String guiName, String strUrl) {
        try {
            URL url = new URL(strUrl);
            File file = new File(DynamicGui.get().getPlugin().getGuiFolder(), guiName);
            Configuration yaml = Configuration.load(url, file);
            this.loadGuiFromFile(yaml, file);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            DynamicGui.get().getLogger().error(
                    "An error occured when loading from the url '%s' " +
                            "please ensure you have the correct url.",
                    strUrl
            );
        }
    }

    private void loadRemoteGuis() {
        File configFile = new File(DynamicGui.get().getPlugin().getDataFolder(), "config.yml");

        Configuration config = Configuration.load(configFile);
        if (config.get("remote-guis") != null) {
            ConfigurationSection remote = config.getConfigurationSection("remote-guis");
            for (Object key : remote.getKeys()) {
                ConfigurationSection guiSection = remote.getConfigurationSection(key);
                String strURL = guiSection.getString("url");
                String guiName = guiSection.getString("file-name");
                this.loadRemoteGui(guiName, strURL);
            }
        }
    }

    private void cleanupGuis() {
        Iterator<Entry<String, Gui>> it = this.cachedGuis.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Gui> next = it.next();
            String guiName = next.getKey();
            if (!this.guis.containsKey(guiName)) {
                it.remove();
                this.guiHashes.remove(guiName);
                this.cachedTokens.remove(guiName);
            }
        }
    }

    private void loadGuiFromConfiguration(String guiName, Configuration config) {
        LoggerWrapper<?> logger = DynamicGui.get().getLogger();

        GuiToken guiToken = new SimpleGuiToken(config);
        List<MacroToken> guiTokens = new ArrayList<>();
        List<String> loadMacros = guiToken.getLoadMacros();

        if (loadMacros.size() > 0) {
            for (String macro : loadMacros) {
                List<MacroToken> macroTokens = this.globalMacros.get(macro);
                if (macroTokens != null) {
                    for (MacroToken t : macroTokens) {
                        guiTokens.add(t);
                    }
                } else {
                    logger.error("Invalid global macro specified %s in gui '%s''", macro, guiName);
                }
            }

            guiToken = new SimpleGuiToken(config, guiTokens);
        }

        this.cachedTokens.put(guiName, guiToken);
        List<Slot> slots = this.createSlots(guiToken);
        final Gui gui = this.createGui(guiToken, guiName, slots, DynamicGui.get().getPlugin());

        this.guis.put(guiName, gui);
        logger.info("gui '%s' has been loaded!", gui.getName());
    }

    private List<Slot> createSlots(GuiToken guiToken) {
        List<Slot> slots = new ArrayList<>();
        Iterator<Entry<Integer, SlotToken>> it = guiToken.getSlots().entrySet().iterator();
        while (it.hasNext()) {
            Entry<Integer, SlotToken> next = it.next();
            int index = next.getKey();
            SlotToken slotToken = next.getValue();

            String icon = slotToken.getIcon();
            String name = slotToken.getName();

            if (name != null) {
                name = ChatColor.translateAlternateColorCodes(name);
            }

            String nbt = slotToken.getNbt();

            List<String> lore = new ArrayList<>();
            for (String ls : slotToken.getLore()) {
                lore.add(ChatColor.translateAlternateColorCodes(ls));
            }

            List<EnchantmentWrapper> enchants = new ArrayList<>();

            for (String ench : slotToken.getEnchants()) {
                String[] args = ench.split(",");
                enchants.add(new EnchantmentWrapper(args[0], Integer.parseInt(args[1])));
            }

            List<String> itemFlags = slotToken.getItemFlags();
            Map<String, String> dataComponents = slotToken.getDataComponents();

            int amount = slotToken.getAmount();

            Boolean close = slotToken.isClosed();

            short data = slotToken.getData();

            boolean glow = slotToken.getGlow();
            boolean movable = slotToken.isMovable();

            String modelProvider = slotToken.getModelProvider();
            String modelData = slotToken.getModelData();

            int updateInterval = slotToken.getUpdateInterval();

            Map<String, String> metadata = slotToken.getMetadata();

            slots.add(new SimpleSlot(index, amount, icon, name, nbt, data, glow, movable,
                    close, lore, enchants, itemFlags, dataComponents, modelProvider, modelData,
                    slotToken.getFunctionTree(), updateInterval, metadata));
        }

        return slots;
    }

    private Gui createGui(final GuiToken guiToken, final String guiName, final List<Slot> slots, final DynamicGuiPlugin plugin) {
        String type = guiToken.getType();
        String title = guiToken.getTitle();
        int rows = guiToken.getRows();
        List<String> aliases = guiToken.getAlias();

        for (String alias : aliases) {
            this.commandRegistrar.registerGuiAliasCommand(guiName, alias, guiToken.getCommandArguments());
        }

        Boolean close = guiToken.isClosed();

        List<LocationWrapper<?>> locations = new ArrayList<>();
        for (String location : guiToken.getLocations()) {
            locations.add(LocationManager.get().toLocationWrapper(location));
        }

        GuiBuildType guiBuildType = GuiBuildType.valueOf(guiToken.getMode().toString());

        Map<String, List<Integer>> npcIds = guiToken.getNpcs();
        Map<String, String> metadata = guiToken.getMetadata();

        boolean isStatic = guiToken.isStatic();

        return this.factory.create(guiName, type, title, rows, close, guiBuildType,
                npcIds, slots, locations, guiToken.getFunctions(), metadata,
                isStatic);
    }
}