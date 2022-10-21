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

package com.clubobsidian.dynamicgui.api.gui;

import com.clubobsidian.dynamicgui.api.component.AnimationHolder;
import com.clubobsidian.dynamicgui.api.component.CloseableComponent;
import com.clubobsidian.dynamicgui.api.enchantment.EnchantmentWrapper;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.factory.FunctionTreeFactory;
import com.clubobsidian.dynamicgui.api.factory.SlotFactory;
import com.clubobsidian.dynamicgui.api.function.FunctionOwner;
import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionTree;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface Slot extends Serializable, FunctionOwner, AnimationHolder, MetadataHolder, CloseableComponent {

    String IGNORE_MATERIAL = "AIR";
    String TEST_MATERIAL = "STONE";

    int getIndex();

    void setIndex(int index);

    String getIcon();

    int getAmount();

    String getName();

    String getNBT();

    boolean getGlow();

    boolean isMovable();

    void setMovable(boolean movable);

    short getData();

    List<String> getLore();

    List<EnchantmentWrapper> getEnchants();

    List<String> getItemFlags();

    Boolean getClose();

    void setClose(@Nullable Boolean close);

    FunctionTree getFunctions();

    ItemStackWrapper<?> buildItemStack(PlayerWrapper<?> playerWrapper);

    ItemStackWrapper<?> getItemStack();

    void setOwner(Gui gui);

    Gui getOwner();

    @Override
    int getUpdateInterval();

    int getCurrentTick();

    void resetTick();

    int tick();

    int getFrame();

    void resetFrame();

    Map<String, String> getMetadata();

    boolean getUpdate();

    void setUpdate(boolean update);

    class Builder {

        @Inject
        private static SlotFactory SLOT_FACTORY;
        @Inject
        private static FunctionTreeFactory TREE_FACTORY;

        private transient String icon;
        private transient String name;
        private transient String nbt;
        private transient short data;
        private transient boolean glow;
        private transient boolean movable;
        private transient Boolean close;
        private transient final List<String> lore = new ArrayList<>();
        private transient final List<EnchantmentWrapper> enchants = new ArrayList<>();
        private transient final List<String> itemFlags = new ArrayList<>();
        private transient String modelProvider;
        private transient String modelData;
        private transient int index;
        private transient int amount = 1;
        private transient int updateInterval = 0;
        private transient FunctionTree functionTree = TREE_FACTORY.create();
        private transient final Map<String, String> metadata = new HashMap<>();


        public Builder setIcon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setNBT(String nbt) {
            this.nbt = nbt;
            return this;
        }

        public Builder setData(int data) {
            this.setData((short) data);
            return this;
        }

        public Builder setData(short data) {
            this.data = data;
            return this;
        }

        public Builder setGlow(boolean glow) {
            this.glow = glow;
            return this;
        }

        public Builder setModelProvider(String modelProvider) {
            this.modelProvider = modelProvider;
            return this;
        }

        public Builder setModelData(String modelData) {
            this.modelData = modelData;
            return this;
        }

        public Builder setMovable(boolean movable) {
            this.movable = movable;
            return this;
        }

        public Builder setClose(Boolean close) {
            this.close = close;
            return this;
        }

        public Builder setIndex(int index) {
            this.index = index;
            return this;
        }

        public Builder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder setUpdateInterval(int interval) {
            this.updateInterval = interval;
            return this;
        }

        public Builder addLore(String lore) {
            this.lore.add(lore);
            return this;
        }

        public Builder addLore(String... lore) {
            for (String l : lore) {
                this.addLore(l);
            }
            return this;
        }

        public Builder addLore(Collection<String> lore) {
            for (String l : lore) {
                this.addLore(l);
            }
            return this;
        }

        public Builder addEnchant(EnchantmentWrapper enchant) {
            this.enchants.add(enchant);
            return this;
        }

        public Builder addEnchant(EnchantmentWrapper... enchant) {
            for (EnchantmentWrapper ench : enchant) {
                this.addEnchant(ench);
            }
            return this;
        }

        public Builder addEnchant(List<EnchantmentWrapper> enchant) {
            for (EnchantmentWrapper ench : enchant) {
                this.addEnchant(ench);
            }

            return this;
        }

        public Builder addItemFlag(String... itemFlags) {
            this.addItemFlag(Arrays.asList(itemFlags));
            return this;
        }

        public Builder addItemFlag(Collection<String> itemFlags) {
            this.itemFlags.addAll(itemFlags);
            return this;
        }

        public Builder setFunctionTree(FunctionTree functionTree) {
            this.functionTree = functionTree;
            return this;
        }

        public Builder addMetadata(String key, String value) {
            this.metadata.put(key, value);
            return this;
        }

        public Builder addMetadata(Map<String, String> metadata) {
            Iterator<Map.Entry<String, String>> it = metadata.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> next = it.next();
                String key = next.getKey();
                String value = next.getValue();
                this.addMetadata(key, value);
            }

            return this;
        }

        public Builder fromItemStackWrapper(ItemStackWrapper<?> itemStackWrapper) {
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
            return SLOT_FACTORY.create(this.index, this.amount, this.icon, this.name,
                    this.nbt, this.data, this.glow, this.movable,
                    this.close, this.lore, this.enchants, this.itemFlags,
                    this.modelProvider, this.modelData, this.functionTree,
                    this.updateInterval, this.metadata);
        }
    }
}