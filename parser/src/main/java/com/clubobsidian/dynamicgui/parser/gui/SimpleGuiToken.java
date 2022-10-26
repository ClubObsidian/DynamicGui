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

package com.clubobsidian.dynamicgui.parser.gui;

import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.api.gui.GuiMode;
import com.clubobsidian.dynamicgui.api.parser.gui.GuiToken;
import com.clubobsidian.dynamicgui.api.parser.macro.MacroParser;
import com.clubobsidian.dynamicgui.api.parser.macro.MacroToken;
import com.clubobsidian.dynamicgui.api.parser.slot.SlotToken;
import com.clubobsidian.dynamicgui.parser.function.tree.SimpleFunctionTree;
import com.clubobsidian.dynamicgui.parser.macro.SimpleMacroParser;
import com.clubobsidian.dynamicgui.parser.macro.SimpleMacroToken;
import com.clubobsidian.dynamicgui.parser.slot.SimpleSlotToken;
import com.clubobsidian.wrappy.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SimpleGuiToken implements GuiToken {

    /**
     *
     */
    private static final long serialVersionUID = -1815626830683338944L;

    private final String title;
    private final String type;
    private final int rows;
    private final GuiMode mode;
    private final Boolean closed; //This should be boxed
    private final List<String> alias;
    private final List<String> locations;
    private final Map<String, List<Integer>> npcs;
    private final Map<Integer, SlotToken> slots;
    private final MacroParser macroParser;
    private final FunctionTree functions;
    private final List<String> loadMacros;
    private final Map<String, String> metadata;
    private final boolean isStatic;

    public SimpleGuiToken(ConfigurationSection section) {
        this(section, new ArrayList<>());
    }

    public SimpleGuiToken(ConfigurationSection section, List<MacroToken> macroTokens) {
        List<MacroToken> copyMacroTokens = new ArrayList<>(macroTokens);

        ConfigurationSection macrosSection = section.getConfigurationSection("macros");
        copyMacroTokens.add(new SimpleMacroToken(macrosSection));

        this.macroParser = new SimpleMacroParser(copyMacroTokens);

        this.title = this.macroParser.parseStringMacros(section.getString("title"));
        this.type = this.parseType(section.getString("type"));
        this.rows = section.getInteger("rows");
        this.mode = this.parseMode(section.getString("mode"));
        this.closed = this.parseBoxedBoolean(section.getString("close"));
        this.alias = this.macroParser.parseListMacros(section.getStringList("alias"));
        this.locations = this.macroParser.parseListMacros(section.getStringList("locations"));
        this.npcs = this.loadNpcs(section);
        this.slots = this.loadSlots(section);


        ConfigurationSection guiFunctionsSection = section.getConfigurationSection("functions");
        this.functions = new SimpleFunctionTree(guiFunctionsSection, this.macroParser);

        this.loadMacros = section.getStringList("load-macros");

        ConfigurationSection metadataSection = section.getConfigurationSection("metadata");
        this.metadata = this.parseMetadata(metadataSection);
        this.isStatic = section.getBoolean("static");
    }

    private Boolean parseBoxedBoolean(String data) {
        if(data == null) {
            return null;
        }
        String parsed = this.macroParser.parseStringMacros(data);
        return Boolean.parseBoolean(parsed);
    }

    private GuiMode parseMode(String mode) {
        if (mode == null) {
            return GuiMode.SET;
        }

        return GuiMode.valueOf(mode.toUpperCase());
    }

    private Map<String, List<Integer>> loadNpcs(ConfigurationSection section) {
        Map<String, List<Integer>> npcs = new HashMap<>();
        ConfigurationSection npcSection = section.getConfigurationSection("npcs");
        for (String key : npcSection.getKeys()) {
            List<Integer> npcIds = npcSection.getIntegerList(key);
            npcs.put(key, npcIds);
        }
        return npcs;
    }

    private Map<String, String> parseMetadata(ConfigurationSection section) {
        Map<String, String> metadata = new HashMap<>();
        for (String key : section.getKeys()) {
            String parsedKey = this.macroParser.parseStringMacros(key);
            String value = section.getString(parsedKey);
            value = this.macroParser.parseStringMacros(value);
            metadata.put(parsedKey, value);
        }

        return metadata;
    }

    private Map<Integer, SlotToken> loadSlots(ConfigurationSection section) {
        Map<Integer, SlotToken> slots = new LinkedHashMap<>();
        int slotAmt = this.rows * 9;
        for (int i = 0; i < slotAmt; i++) {
            ConfigurationSection slotSection = section.getConfigurationSection(String.valueOf(i));
            if (!slotSection.isEmpty()) {
                SlotToken token = new SimpleSlotToken(i, slotSection, this.macroParser.getTokens());
                slots.put(i, token);
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
    public GuiMode getMode() {
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