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
package com.clubobsidian.dynamicgui.parser.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.clubobsidian.dynamicgui.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.parser.macro.MacroParser;
import com.clubobsidian.dynamicgui.parser.macro.MacroToken;
import com.clubobsidian.dynamicgui.parser.slot.SlotToken;
import com.clubobsidian.wrappy.ConfigurationSection;

public class GuiToken implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1815626830683338944L;

    private final String title;
    private final String type;
    private final int rows;
    private final GuiMode mode;
    private final boolean closed;
    private final List<String> alias;
    private final List<String> locations;
    private final Map<String, List<Integer>> npcs;
    private final Map<Integer, SlotToken> slots;
    private final MacroParser macroParser;
    private final FunctionTree functions;
    private final List<String> loadMacros;
    private final Map<String, String> metadata;

    public GuiToken(ConfigurationSection section) {
        this(section, new ArrayList<>());
    }

    public GuiToken(ConfigurationSection section, List<MacroToken> macroTokens) {
        List<MacroToken> copyMacroTokens = new ArrayList<>(macroTokens);

        ConfigurationSection macrosSection = section.getConfigurationSection("macros");
        copyMacroTokens.add(new MacroToken(macrosSection));

        this.macroParser = new MacroParser(copyMacroTokens);

        this.title = this.macroParser.parseStringMacros(section.getString("title"));
        this.type = this.parseType(section.getString("type"));
        this.rows = section.getInteger("rows");
        this.mode = this.parseMode(section.getString("mode"));
        this.closed = section.getBoolean("close");
        this.alias = this.macroParser.parseListMacros(section.getStringList("alias"));
        this.locations = this.macroParser.parseListMacros(section.getStringList("locations"));
        this.npcs = this.loadNpcs(section);
        this.slots = this.loadSlots(section);


        ConfigurationSection guiFunctionsSection = section.getConfigurationSection("functions");
        this.functions = new FunctionTree(guiFunctionsSection, this.macroParser);

        this.loadMacros = section.getStringList("load-macros");

        ConfigurationSection metadataSection = section.getConfigurationSection("metadata");
        this.metadata = this.parseMetadata(metadataSection);
    }

    public String parseType(String type) {
        if (type == null) {
            return "CHEST";
        }
        return type.toUpperCase();
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
                SlotToken token = new SlotToken(i, slotSection, this.macroParser.getTokens());
                slots.put(i, token);
            }
        }
        return slots;
    }

    public String getTitle() {
        return this.title;
    }

    public String getType() {
        return this.type;
    }

    public int getRows() {
        return this.rows;
    }

    public GuiMode getMode() {
        return this.mode;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public List<String> getAlias() {
        return this.alias;
    }

    public List<String> getLocations() {
        return this.locations;
    }

    public Map<String, List<Integer>> getNpcs() {
        return this.npcs;
    }

    public Map<Integer, SlotToken> getSlots() {
        return this.slots;
    }

    public FunctionTree getFunctions() {
        return this.functions;
    }

    public MacroParser getMacroParser() {
        return this.macroParser;
    }

    public List<String> getLoadMacros() {
        return this.loadMacros;
    }

    public Map<String, String> getMetadata() {
        return this.metadata;
    }
}