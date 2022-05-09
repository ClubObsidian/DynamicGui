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

package com.clubobsidian.dynamicgui.core.builder;

import com.clubobsidian.dynamicgui.core.enchantment.EnchantmentWrapper;
import com.clubobsidian.dynamicgui.core.gui.Slot;
import com.clubobsidian.dynamicgui.core.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.parser.function.tree.FunctionTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SlotBuilder {

    private String icon;
    private String name;
    private String nbt;
    private short data;
    private boolean glow;
    private boolean movable;
    private Boolean close;
    private List<String> lore;
    private final List<EnchantmentWrapper> enchants = new ArrayList<>();
    private final List<String> itemFlags = new ArrayList<>();
    private String modelProvider;
    private String modelData;
    private int index;
    private int amount = 1;
    private int updateInterval = 0;
    private FunctionTree functionTree = new FunctionTree();
    private final Map<String, String> metadata = new HashMap<>();


    public SlotBuilder setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public SlotBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public SlotBuilder setNBT(String nbt) {
        this.nbt = nbt;
        return this;
    }

    public SlotBuilder setData(int data) {
        this.setData((short) data);
        return this;
    }

    public SlotBuilder setData(short data) {
        this.data = data;
        return this;
    }

    public SlotBuilder setGlow(boolean glow) {
        this.glow = glow;
        return this;
    }

    public SlotBuilder setModelProvider(String modelProvider) {
        this.modelProvider = modelProvider;
        return this;
    }

    public SlotBuilder setModelData(String modelData) {
        this.modelData = modelData;
        return this;
    }

    public SlotBuilder setMovable(boolean movable) {
        this.movable = movable;
        return this;
    }

    public SlotBuilder setClose(Boolean close) {
        this.close = close;
        return this;
    }

    public SlotBuilder setIndex(int index) {
        this.index = index;
        return this;
    }

    public SlotBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public SlotBuilder setUpdateInterval(int interval) {
        this.updateInterval = interval;
        return this;
    }

    public SlotBuilder addLore(String lore) {
        if (this.lore == null) {
            this.lore = new ArrayList<String>();
            this.lore.add(lore);
        } else {
            this.lore.add(lore);
        }
        return this;
    }

    public SlotBuilder addLore(String... lore) {
        for (String l : lore) {
            this.addLore(l);
        }

        return this;
    }

    public SlotBuilder addLore(List<String> lore) {
        for (String l : lore) {
            this.addLore(l);
        }

        return this;
    }

    public SlotBuilder addEnchant(EnchantmentWrapper enchant) {
        this.enchants.add(enchant);
        return this;
    }

    public SlotBuilder addEnchant(EnchantmentWrapper... enchant) {
        for (EnchantmentWrapper ench : enchant) {
            this.addEnchant(ench);
        }

        return this;
    }

    public SlotBuilder addEnchant(List<EnchantmentWrapper> enchant) {
        for (EnchantmentWrapper ench : enchant) {
            this.addEnchant(ench);
        }

        return this;
    }

    public SlotBuilder addItemFlag(String... itemFlags) {
        this.addItemFlag(Arrays.asList(itemFlags));
        return this;
    }

    public SlotBuilder addItemFlag(Collection<String> itemFlags) {
        this.itemFlags.addAll(itemFlags);
        return this;
    }

    public SlotBuilder setFunctionTree(FunctionTree functionTree) {
        this.functionTree = functionTree;
        return this;
    }

    public SlotBuilder addMetadata(String key, String value) {
        this.metadata.put(key, value);
        return this;
    }

    public SlotBuilder addMetadata(Map<String, String> metadata) {
        Iterator<Entry<String, String>> it = metadata.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, String> next = it.next();
            String key = next.getKey();
            String value = next.getValue();
            this.addMetadata(key, value);
        }

        return this;
    }

    public SlotBuilder fromItemStackWrapper(ItemStackWrapper<?> itemStackWrapper) {
        this.setIcon(itemStackWrapper.getType());
        this.setName(itemStackWrapper.getName());
        this.setNBT(itemStackWrapper.getNBT());
        this.setData(itemStackWrapper.getDurability());
        this.setAmount(itemStackWrapper.getAmount());
        this.addLore(itemStackWrapper.getLore());

        for (EnchantmentWrapper enchant : itemStackWrapper.getEnchants()) {
            this.addEnchant(enchant);
        }

        return this;
    }

    public Slot build() {
        return new Slot(this.index, this.amount, this.icon, this.name,
                this.nbt, this.data, this.glow, this.movable,
                this.close, this.lore, this.enchants, this.itemFlags,
                this.modelProvider, this.modelData, this.functionTree,
                this.updateInterval, this.metadata);
    }
}