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
package com.clubobsidian.dynamicgui.builder;

import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.ModeEnum;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.world.LocationWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GuiBuilder {

    private String name;
    private String type;
    private String title;
    private int rows;
    private Boolean close;
    private ModeEnum modeEnum;
    private final Map<String, List<Integer>> npcIds;
    private final List<Slot> slots;
    private final List<LocationWrapper<?>> locs;
    private FunctionTree functionTree;
    private final Map<String, String> metadata;
    private Gui backGui;

    public GuiBuilder() {
        this.npcIds = new HashMap<>();
        this.slots = new ArrayList<>();
        this.locs = new ArrayList<>();
        this.functionTree = new FunctionTree();
        this.metadata = new HashMap<>();
    }

    public GuiBuilder setType(String type) {
        this.type = type.toUpperCase();
        return this;
    }

    public GuiBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public GuiBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public GuiBuilder setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public GuiBuilder setClose(Boolean close) {
        this.close = close;
        return this;
    }

    public GuiBuilder setModeEnum(String mode) {
        this.setModeEnum(ModeEnum.valueOf(mode));
        return this;
    }

    public GuiBuilder setModeEnum(ModeEnum modeEnum) {
        this.modeEnum = modeEnum;
        return this;
    }

    public GuiBuilder addNpcId(String plugin, Integer id) {
        List<Integer> npcs = this.npcIds.get(plugin);
        if(npcs == null) {
            npcs = new ArrayList<>();
            this.npcIds.put(plugin, npcs);
        }

        npcs.add(id);
        return this;
    }

    public GuiBuilder addNpcId(String plugin, Integer[] npcIds) {
        for(Integer id : npcIds) {
            this.addNpcId(plugin, id);
        }
        return this;
    }

    public GuiBuilder addNpcId(String plugin, List<Integer> npcIds) {
        for(Integer id : npcIds) {
            this.addNpcId(plugin, id);
        }
        return this;
    }

    public GuiBuilder addSlot(Slot slot) {
        this.slots.add(slot);
        return this;
    }

    public GuiBuilder addLocation(LocationWrapper<?> loc) {
        this.locs.add(loc);
        return this;
    }

    public GuiBuilder setFunctionTree(FunctionTree functionTree) {
        this.functionTree = functionTree;
        return this;
    }

    public GuiBuilder setBack(Gui backGui) {
        this.backGui = backGui;
        return this;
    }

    public GuiBuilder setBack(String backGuiName) {
        this.backGui = GuiManager.get().getGuiByName(backGuiName);
        return this;
    }

    public GuiBuilder addMetadata(String key, String value) {
        this.metadata.put(key, value);
        return this;
    }

    public GuiBuilder addMetadata(Map<String, String> metadata) {
        Iterator<Entry<String, String>> it = metadata.entrySet().iterator();
        while(it.hasNext()) {
            Entry<String, String> next = it.next();
            String key = next.getKey();
            String value = next.getValue();
            this.addMetadata(key, value);
        }

        return this;
    }

    public Gui build() {
        Gui gui = new Gui(this.name, this.type, this.title, this.rows, this.close, this.modeEnum, this.npcIds, this.slots, this.locs, this.functionTree, this.metadata);
        if(this.backGui != null) {
            gui.setBack(this.backGui);
        }

        return gui;
    }
}