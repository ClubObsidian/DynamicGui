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

package com.clubobsidian.dynamicgui.parser.slot;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.api.parser.macro.MacroParser;
import com.clubobsidian.dynamicgui.api.parser.macro.MacroToken;
import com.clubobsidian.dynamicgui.api.parser.slot.SlotToken;
import com.clubobsidian.dynamicgui.parser.function.tree.SimpleFunctionTree;
import com.clubobsidian.dynamicgui.parser.macro.SimpleMacroParser;
import com.clubobsidian.dynamicgui.parser.macro.SimpleMacroToken;
import com.clubobsidian.wrappy.ConfigurationSection;

import java.util.*;

public class SimpleSlotToken implements SlotToken {

    /**
     *
     */
    private static final long serialVersionUID = -1898426889177654844L;

    private final int index;
    private final int amount;
    private final String icon;
    private final String name;
    private final String nbt;
    private final boolean glow;
    private final boolean movable;
    private final Boolean closed; //This should be boxed
    private final byte data;
    private final List<String> lore;
    private final List<String> enchants;
    private final List<String> itemFlags;
    private final Map<String, String> dataComponents;
    private final String modelProvider;
    private final String modelData;
    private final int updateInterval;
    private final MacroParser macroParser;
    private final FunctionTree functionTree;
    private final Map<String, String> metadata;

    public SimpleSlotToken(int index, ConfigurationSection section) {
        this(index, section, new ArrayList<>());
    }

    public SimpleSlotToken(int index, ConfigurationSection section, List<MacroToken> macroTokens) {
        List<MacroToken> copyMacroTokens = new ArrayList<>(macroTokens);
        ConfigurationSection macrosSection = section.getConfigurationSection("macros");
        copyMacroTokens.add(new SimpleMacroToken(macrosSection));
        this.macroParser = new SimpleMacroParser(copyMacroTokens);
        this.macroParser.parseSectionMacros(section);
        this.index = index;
        this.amount = this.parseAmount(section.getInteger("amount"));
        this.icon = this.macroParser.parseStringMacros(section.getString("icon"));
        this.name = this.macroParser.parseStringMacros(section.getString("name"));
        this.nbt = this.macroParser.parseStringMacros(section.getString("nbt"));
        this.glow = this.parseBoolean(section.getString("glow"));
        this.movable = this.parseBoolean(section.getString("movable"));
        this.closed = this.parseBoxedBoolean(section.getString("close"));
        this.data = this.parseByte(section.getString("data"));
        this.lore = this.macroParser.parseListMacros(section.getStringList("lore"));
        this.enchants = this.macroParser.parseListMacros(section.getStringList("enchants"));
        this.itemFlags = this.macroParser.parseListMacros(section.getStringList("item-flags"));
        this.dataComponents = this.parseStringMap(
                section.getMap("data-components", String.class, String.class)
        );
        this.modelProvider = this.macroParser.parseStringMacros(section.getString("model.provider"));
        this.modelData = this.macroParser.parseStringMacros(section.getString("model.data"));
        this.updateInterval = this.parseUpdateInterval(section.getString("update-interval"));
        ConfigurationSection functionsSection = section.getConfigurationSection("functions");
        this.functionTree = new SimpleFunctionTree(functionsSection, this.macroParser);
        ConfigurationSection metadataSection = section.getConfigurationSection("metadata");
        this.metadata = this.parseMetadata(metadataSection);
    }

    private Map<String, String> parseStringMap(Map<String, String> map) {
        if (map == null) {
            return Collections.emptyMap();
        }
        Map<String, String> parsedMap = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = this.macroParser.parseStringMacros(entry.getKey());
            String value = this.macroParser.parseStringMacros(entry.getValue());
            parsedMap.put(key, value);
        }
        return parsedMap;
    }

    private int parseAmount(int amount) {
        if (amount == 0) {
            return 1;
        }
        return amount;
    }

    private byte parseByte(String data) {
        String stringData = this.macroParser.parseStringMacros(data);
        try {
            return Byte.parseByte(stringData);
        } catch (Exception ex) {
            return 0;
        }
    }

    private boolean parseBoolean(String data) {
        if (data == null) {
            return false;
        }
        String parsed = this.macroParser.parseStringMacros(data);
        return Boolean.parseBoolean(parsed);
    }

    private Boolean parseBoxedBoolean(String data) {
        if (data == null) {
            return null;
        }
        String parsed = this.macroParser.parseStringMacros(data);
        return Boolean.parseBoolean(parsed);
    }

    private int parseInteger(String data) {
        if (data == null) {
            return 0;
        }

        try {
            String parsed = this.macroParser.parseStringMacros(data);
            return Integer.parseInt(parsed);
        } catch (Exception ex) {
            return 0;
        }
    }

    private int parseUpdateInterval(String data) {
        int updateInterval = this.parseInteger(data);
        return Math.max(updateInterval, 0);
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

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public String getIcon() {
        return this.icon;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getNbt() {
        return this.nbt;
    }

    @Override
    public boolean getGlow() {
        return this.glow;
    }

    @Override
    public boolean isMovable() {
        return this.movable;
    }

    @Override
    public Boolean isClosed() {
        return this.closed;
    }

    @Override
    public byte getData() {
        return this.data;
    }

    @Override
    public List<String> getLore() {
        return this.lore;
    }

    @Override
    public int getUpdateInterval() {
        return this.updateInterval;
    }

    @Override
    public List<String> getEnchants() {
        return this.enchants;
    }

    @Override
    public List<String> getItemFlags() {
        return this.itemFlags;
    }

    @Override
    public Map<String, String> getDataComponents() {
        return this.dataComponents;
    }

    @Override
    public String getModelProvider() {
        return this.modelProvider;
    }

    @Override
    public String getModelData() {
        return this.modelData;
    }

    @Override
    public FunctionTree getFunctionTree() {
        return this.functionTree;
    }

    @Override
    public MacroParser getMacroParser() {
        return this.macroParser;
    }

    @Override
    public Map<String, String> getMetadata() {
        return this.metadata;
    }
}