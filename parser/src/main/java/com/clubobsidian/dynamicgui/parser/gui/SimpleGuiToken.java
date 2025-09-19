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

package com.clubobsidian.dynamicgui.parser.gui;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.command.cloud.CloudArgument;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.gui.GuiBuildType;
import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.api.parser.gui.GuiToken;
import com.clubobsidian.dynamicgui.api.parser.macro.MacroParser;
import com.clubobsidian.dynamicgui.api.parser.macro.MacroToken;
import com.clubobsidian.dynamicgui.api.parser.slot.SlotToken;
import com.clubobsidian.dynamicgui.parser.function.tree.SimpleFunctionTree;
import com.clubobsidian.dynamicgui.parser.index.SlotExpander;
import com.clubobsidian.dynamicgui.parser.macro.SimpleMacroParser;
import com.clubobsidian.dynamicgui.parser.macro.SimpleMacroToken;
import com.clubobsidian.dynamicgui.parser.slot.SimpleSlotToken;
import com.clubobsidian.wrappy.ConfigurationSection;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.component.CommandComponent;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.suggestion.BlockingSuggestionProvider;

import java.util.*;
import java.util.stream.Collectors;

public class SimpleGuiToken implements GuiToken {

    /**
     *
     */
    private static final long serialVersionUID = -1815626830683338944L;

    private final String title;
    private final String type;
    private final int rows;
    private final GuiBuildType mode;
    private final Boolean closed; //This should be boxed
    private final List<String> alias;
    private final List<CommandComponent> commandArguments;
    private final List<String> locations;
    private final Map<String, List<Integer>> npcs;
    private final Map<Integer, SlotToken> slots;
    private final MacroParser macroParser;
    private final FunctionTree functions;
    private final List<String> loadMacros;
    private final Map<String, String> metadata;
    private final boolean isStatic;
    public final ConfigurationSection section;

    public SimpleGuiToken(ConfigurationSection section) {
        this(section, new ArrayList<>());
    }

    public SimpleGuiToken(ConfigurationSection section, List<MacroToken> macroTokens) {
        List<MacroToken> copyMacroTokens = new ArrayList<>(macroTokens);

        ConfigurationSection macrosSection = section.getConfigurationSection("macros");
        copyMacroTokens.add(new SimpleMacroToken(macrosSection));

        this.macroParser = new SimpleMacroParser(copyMacroTokens);
        this.macroParser.parseSectionMacros(section);

        this.title = this.macroParser.parseStringMacros(section.getString("title"));
        this.type = this.parseType(section.getString("type"));
        this.rows = section.getInteger("rows");
        this.mode = this.parseMode(section.getString("mode"));
        this.closed = this.parseBoxedBoolean(section.getString("close"));
        this.alias = this.macroParser.parseListMacros(section.getStringList("alias"));
        this.commandArguments = this.loadCommandArguments(section.getConfigurationSection("command-args"));
        this.locations = this.macroParser.parseListMacros(section.getStringList("locations"));
        this.npcs = this.loadNpcs(section);
        this.slots = this.loadSlots(section);


        ConfigurationSection guiFunctionsSection = section.getConfigurationSection("functions");
        this.functions = new SimpleFunctionTree(guiFunctionsSection, this.macroParser);

        this.loadMacros = section.getStringList("load-macros");

        ConfigurationSection metadataSection = section.getConfigurationSection("metadata");
        this.metadata = this.parseMetadata(metadataSection);
        this.isStatic = section.getBoolean("static");
        this.section = section;
    }

    @SuppressWarnings({"rawtypes"})
    private List<CommandComponent> loadCommandArguments(ConfigurationSection section) {
        List<CommandComponent> args = new ArrayList<>();
        for (Object key : section.getKeys()) {
            String keyName = String.valueOf(key);
            ConfigurationSection keySec = section.getConfigurationSection(key);
            String type = keySec.getString("type");
            boolean optional = keySec.getBoolean("optional");
            boolean greedy = keySec.getBoolean("greedy");
            Optional<CloudArgument> opt = CloudArgument.fromType(type);
            if (opt.isPresent()) {
                CloudArgument arg = opt.get();
                CommandComponent.Builder commandBuilder = arg.argument(keyName, optional, greedy);
                ConfigurationSection tabComplete = keySec.getConfigurationSection("tab-complete");
                String completeType = tabComplete.getString("type");
                if (completeType == null) {
                    DynamicGui.get().getLogger().error("No tab complete type found for arg %s", keyName);
                } else {
                    if (completeType.equals("player")) {
                        commandBuilder = commandBuilder.suggestionProvider(new BlockingSuggestionProvider.Strings() {
                            @Override
                            public @NonNull Iterable<@NonNull String> stringSuggestions(@NonNull CommandContext commandContext,
                                                                                        @NonNull CommandInput input) {
                                return DynamicGui.get().getPlatform()
                                        .getOnlinePlayers()
                                        .stream()
                                        .map(PlayerWrapper::getName).collect(Collectors.toList());
                            }
                        });
                    }
                }
                args.add(commandBuilder.build());
            } else {
                DynamicGui.get().getLogger().error("Invalid argument type %s", type);
            }
        }
        return args;
    }

    private Boolean parseBoxedBoolean(String data) {
        if (data == null) {
            return null;
        }
        String parsed = this.macroParser.parseStringMacros(data);
        return Boolean.parseBoolean(parsed);
    }

    private GuiBuildType parseMode(String mode) {
        if (mode == null) {
            return GuiBuildType.SET;
        }

        return GuiBuildType.valueOf(mode.toUpperCase());
    }

    private Map<String, List<Integer>> loadNpcs(ConfigurationSection section) {
        Map<String, List<Integer>> npcs = new HashMap<>();
        ConfigurationSection npcSection = section.getConfigurationSection("npcs");
        for (Object key : npcSection.getKeys()) {
            if (key instanceof String) {
                String keyStr = (String) key;
                List<Integer> npcIds = npcSection.getIntegerList(key);
                npcs.put(keyStr, npcIds);
            } else {
                DynamicGui.get().getLogger().error("Npc plugin names do not support non-string names: " + key);
            }
        }
        return npcs;
    }

    private Map<String, String> parseMetadata(ConfigurationSection section) {
        Map<String, String> metadata = new HashMap<>();
        for (Object key : section.getKeys()) {
            if (key instanceof String) {
                String keyStr = (String) key;
                String parsedKey = this.macroParser.parseStringMacros(keyStr);
                String value = section.getString(parsedKey);
                value = this.macroParser.parseStringMacros(value);
                metadata.put(parsedKey, value);
            } else {
                DynamicGui.get().getLogger().error("Metadata does not support non-string keys: " + key);
            }
        }

        return metadata;
    }

    private Map<Integer, SlotToken> loadSlots(ConfigurationSection section) {
        Map<Integer, SlotToken> slots = new LinkedHashMap<>();
        for (Object key : section.getKeys()) {
            if (!SlotExpander.isSlot(key)) {
                continue;
            }
            int slotAmt = this.rows * 9;
            ConfigurationSection slotSection = section.getConfigurationSection(key);
            List<Integer> expandedSlots = SlotExpander.expand(key);
            for (Integer index : expandedSlots) {
                if (index >= slotAmt) {
                    DynamicGui.get()
                            .getLogger()
                            .error("Slot '%d' is >= the index for the max allowed slots of '%d'",
                                    index, slotAmt
                            );
                    continue;
                }
                SlotToken token = new SimpleSlotToken(index, slotSection, this.macroParser.getTokens());
                slots.put(index, token);
            }
        }
        return slots;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public GuiBuildType getMode() {
        return this.mode;
    }

    @Override
    public Boolean isClosed() {
        return this.closed;
    }

    @Override
    public List<String> getAlias() {
        return this.alias;
    }

    @Override
    public Collection<CommandComponent> getCommandArguments() {
        return this.commandArguments;
    }

    @Override
    public List<String> getLocations() {
        return this.locations;
    }

    @Override
    public Map<String, List<Integer>> getNpcs() {
        return this.npcs;
    }

    @Override
    public Map<Integer, SlotToken> getSlots() {
        return this.slots;
    }

    @Override
    public FunctionTree getFunctions() {
        return this.functions;
    }

    @Override
    public MacroParser getMacroParser() {
        return this.macroParser;
    }

    @Override
    public List<String> getLoadMacros() {
        return this.loadMacros;
    }

    @Override
    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    @Override
    public boolean isStatic() {
        return this.isStatic;
    }
}