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

package com.clubobsidian.dynamicgui.core.gui;

import com.clubobsidian.dynamicgui.api.enchantment.EnchantmentWrapper;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.gui.Slot;
import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.api.manager.inventory.ItemStackManager;
import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.core.manager.AnimationReplacerManager;
import com.clubobsidian.dynamicgui.api.manager.ModelManager;
import com.clubobsidian.dynamicgui.core.manager.ReplacerManager;
import com.clubobsidian.dynamicgui.api.model.ModelProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimpleSlot implements Slot {

    /**
     *
     */
    private static final long serialVersionUID = 2366997214615469494L;

    private int index;
    private final String icon;
    private final String name;
    private final String nbt;
    private final short data;
    private final boolean glow;
    private boolean movable;

    private final List<String> lore;
    private final List<EnchantmentWrapper> enchants;
    private final List<String> itemFlags;
    private final String modelProvider;
    private final String modelData;
    private Boolean close;
    private final int amount;
    private transient ItemStackWrapper<?> itemStack;
    private Gui owner;
    private final FunctionTree functions;
    private final int updateInterval;
    private int tick;
    private int frame;
    private final Map<String, String> metadata;
    private boolean update;

    public SimpleSlot(int index, int amount, String icon, String name, String nbt, short data, boolean glow,
                      boolean movable, Boolean close, List<String> lore,
                      List<EnchantmentWrapper> enchants, List<String> itemFlags,
                      String modelProvider, String modelData,
                      FunctionTree functions, int updateInterval, Map<String, String> metadata) {
        this.icon = icon;
        this.data = data;
        this.name = name;
        this.nbt = nbt;
        this.glow = glow;
        this.movable = movable;
        this.lore = lore;
        this.enchants = enchants;
        this.itemFlags = itemFlags;
        this.modelProvider = modelProvider;
        this.modelData = modelData;
        this.close = close;
        this.index = index;
        this.amount = amount;
        this.functions = functions;
        this.updateInterval = updateInterval;
        this.tick = 0;
        this.frame = 0;
        this.metadata = metadata;
        this.update = false;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getIcon() {
        return this.icon;
    }

    public int getAmount() {
        return this.amount;
    }

    public String getName() {
        return this.name;
    }

    public String getNBT() {
        return this.nbt;
    }

    public boolean getGlow() {
        return this.glow;
    }

    public boolean isMovable() {
        return this.movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public short getData() {
        return this.data;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public List<EnchantmentWrapper> getEnchants() {
        return this.enchants;
    }

    public List<String> getItemFlags() {
        return this.itemFlags;
    }

    @Override
    public Boolean getClose() {
        return this.close;
    }

    @Override
    public void setClose(Boolean close) {
        this.close = close;
    }

    @Override
    public FunctionTree getFunctions() {
        return this.functions;
    }

    public ItemStackWrapper<?> buildItemStack(PlayerWrapper<?> playerWrapper) {
        ItemStackWrapper<?> builderItem = this.itemStack;

        if (builderItem == null) {
            builderItem = ItemStackManager.get().createItemStackWrapper(this.icon, this.amount);
        } else {
            builderItem.setType(this.icon);
            builderItem.setAmount(this.amount);
        }

        if (!this.icon.equalsIgnoreCase(IGNORE_MATERIAL)) {
            if (this.data != 0) {
                builderItem.setDurability(this.data);
            }

            if (this.modelProvider != null && this.modelData != null) {
                ModelProvider provider = ModelManager.get().getProvider(this.modelProvider);
                if (provider != null) {
                    provider.applyModel(builderItem, this.modelData);
                }
            }

            if (this.name != null) {
                String newName = this.name;
                newName = ReplacerManager.get().replace(newName, playerWrapper);
                newName = AnimationReplacerManager.get().replace(this, playerWrapper, newName);
                builderItem.setName(newName);
            }

            if (this.lore != null) {
                List<String> newLore = new ArrayList<>();

                for (String newString : this.lore) {
                    String lore = ReplacerManager.get().replace(newString, playerWrapper);
                    lore = AnimationReplacerManager.get().replace(this, playerWrapper, lore);
                    if (lore.contains("\n")) {
                        String[] split = lore.split("\n");
                        for (String sp : split) {
                            newLore.add(sp);
                        }
                    } else {
                        newLore.add(lore);
                    }
                }

                builderItem.setLore(newLore);
            }

            if (this.enchants != null) {
                for (EnchantmentWrapper ench : this.enchants) {
                    builderItem.addEnchant(ench);
                }
            }

            builderItem.addItemFlags(this.itemFlags);

            if (this.glow) {
                builderItem.setGlowing(true);
            }

            if (this.nbt != null && !this.nbt.equals("")) {
                builderItem.setNBT(ReplacerManager.get().replace(this.nbt, playerWrapper));
            }
        }

        this.itemStack = builderItem;
        return builderItem;
    }

    public ItemStackWrapper<?> getItemStack() {
        return this.itemStack;
    }

    public void setOwner(Gui gui) {
        this.owner = gui;
    }

    public Gui getOwner() {
        return this.owner;
    }

    @Override
    public int getUpdateInterval() {
        return this.updateInterval;
    }

    @Override
    public int getCurrentTick() {
        return this.tick;
    }

    @Override
    public void resetTick() {
        this.tick = 0;
    }

    @Override
    public int tick() {
        this.tick += 1;

        if ((this.tick) % 20 == 0) {
            this.frame += 1;

            //Reset frame
            if (this.tick == Integer.MAX_VALUE) {
                this.tick = 0;
            }
            if (this.frame == Integer.MAX_VALUE) {
                this.frame = 0;
            }
        }

        return this.tick;
    }

    @Override
    public int getFrame() {
        return this.frame;
    }

    @Override
    public void resetFrame() {
        this.frame = 0;
    }

    @Override
    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    public boolean getUpdate() {
        return this.update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}