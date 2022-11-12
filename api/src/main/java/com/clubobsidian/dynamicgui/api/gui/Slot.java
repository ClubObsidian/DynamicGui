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
import org.jetbrains.annotations.Unmodifiable;

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

    /**
     * The index that the slot exists at after a gui is built.
     * Before a gui is built this value does not accurately
     * have the correct index.
     *
     * @return the slot index
     */
    int getIndex();

    /**
     * This typically should only be used for setting
     * the index for where a slot exists when the gui
     * is built.
     *
     * @param index the index to set for the slot
     */
    void setIndex(int index);

    /**
     * Gets the icon or material that the slot uses to build
     * the initial item. If the item was modified
     * this may not reflect the current material for
     * the item stack. If you need the current
     * material use {@link ItemStackWrapper#getType()}
     *
     * @return the icon or material for the slot
     */
    String getIcon();

    /**
     * Gets the amount that the slot uses to build
     * the initial item. If the item was modified
     * this may not reflect the current amount for
     * the item stack. If you need the current
     * amount use {@link ItemStackWrapper#getAmount()}
     *
     * @return the amount for the slot
     */
    int getAmount();

    /**
     * Gets the custom name that the slot uses to build
     * the initial item. If the item was modified
     * this may not reflect the current name for
     * the item stack. If you need the current
     * name use {@link ItemStackWrapper#getName()}
     *
     * @return the name of the slot
     */
    String getName();

    /**
     * Gets the nbt that the slot uses to build
     * the initial item. If the item was modified
     * this may not reflect the current nbt for
     * the item stack. If you need the current
     * name use {@link ItemStackWrapper#getNBT()}
     *
     * @return the slot nbt
     */
    String getNBT();

    /**
     * Gets whether the item is glowing as in
     * whether it has an enchant that is added
     * to the item that is hidden.
     *
     * @return whether the item is glowing
     */
    boolean getGlow();

    /**
     * Gets whether this slot is movable, as in that
     * the item within the slot can be moved around the
     * gui, removed etc.
     *
     * @return whether the slot is movable
     */
    boolean isMovable();

    /**
     * Sets whether this slot is movable, as in that
     * the item within the slot can be moved around the
     * gui, removed etc.
     *
     * @param movable whether the slot should be movable
     */
    void setMovable(boolean movable);

    /**
     * Gets the damage value that the slot uses to build
     * the initial item. If the item was modified
     * this may not reflect the current data for
     * the item stack. If you need the current
     * durability use {@link ItemStackWrapper#getDurability()}
     *
     * @return the slot durability
     */
    short getData();

    /**
     * Gets the lore that the slot uses to build
     * the initial item. If the item was modified
     * this may not reflect the current lore for
     * the item stack. If you need the current
     * lore use {@link ItemStackWrapper#getLore()}
     *
     * @return the slot lore
     */
    @Unmodifiable List<String> getLore();

    /**
     * Gets the enchants that the slot uses to build
     * the initial item. If the item was modified
     * this may not reflect the current enchants for
     * the item stack. If you need the current
     * durability use {@link ItemStackWrapper#getEnchants()}
     *
     * @return the slot enchants
     */
    @Unmodifiable List<EnchantmentWrapper> getEnchants();

    /**
     * Gets the item flags that the slot uses to build
     * the initial item. If the item was modified
     * this may not reflect the current item flags for
     * the item stack. If you need the current
     * item flags use {@link ItemStackWrapper#getItemFlags()}
     *
     * @return the slot item flags
     */
    @Unmodifiable List<String> getItemFlags();

    /**
     * Gets whether the slot should close
     *
     * @return boxed boolean if the slot closes when clicked
     */
    @Nullable Boolean getClose();

    /**
     * Sets whether the slot should close
     *
     * @param close boxed boolean if the component should close
     */
    void setClose(@Nullable Boolean close);

    /**
     * Builds an item stack wrapper for the slot
     *
     * @param playerWrapper the player to build the slot for
     * @return the built item stack wrapper
     */
    ItemStackWrapper<?> buildItemStack(PlayerWrapper<?> playerWrapper);

    /**
     * The item stack wrapper for the slot
     *
     * @return the slot item stack wrapper
     */
    @Nullable ItemStackWrapper<?> getItemStack();

    /**
     * Sets the owner of the slot
     *
     * @param gui gui to set as owner
     */
    void setOwner(Gui gui);

    /**
     * Gets the owner of the gui. The owner of the gui
     * is the gui that the slots are owned by or belong to.
     *
     * @return gui owner
     */
    Gui getOwner();

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


        /**
         * Sets the icon for the slot
         *
         * @param icon to set
         * @return this builder
         */
        public Builder setIcon(String icon) {
            this.icon = icon;
            return this;
        }

        /**
         * Sets the name for the slot
         *
         * @param name to set
         * @return this builder
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the nbt for the slot
         *
         * @param nbt to set
         * @return this builder
         */
        public Builder setNBT(String nbt) {
            this.nbt = nbt;
            return this;
        }

        /**
         * Sets the data for the slot
         *
         * @param data to set
         * @return this builder
         */
        public Builder setData(int data) {
            this.setData((short) data);
            return this;
        }

        /**
         * Sets the data for the slot
         *
         * @param data to set
         * @return this builder
         */
        public Builder setData(short data) {
            this.data = data;
            return this;
        }

        /**
         * Sets the glow for the slot
         *
         * @param glow to set
         * @return this builder
         */
        public Builder setGlow(boolean glow) {
            this.glow = glow;
            return this;
        }

        /**
         * Sets the model provider for the slot
         *
         * @param modelProvider to set
         * @return this builder
         */
        public Builder setModelProvider(String modelProvider) {
            this.modelProvider = modelProvider;
            return this;
        }

        /**
         * Sets the model data for the slot
         *
         * @param modelData to set
         * @return this builder
         */
        public Builder setModelData(String modelData) {
            this.modelData = modelData;
            return this;
        }

        /**
         * Sets whether the slot should be movable
         *
         * @param movable to set
         * @return this builder
         */
        public Builder setMovable(boolean movable) {
            this.movable = movable;
            return this;
        }

        /**
         * Set whether the slot should close
         *
         * @param close to set
         * @return this builder
         */
        public Builder setClose(@Nullable Boolean close) {
            this.close = close;
            return this;
        }

        /**
         * Set the index for the slot
         *
         * @param index to set
         * @return this builder
         */
        public Builder setIndex(int index) {
            this.index = index;
            return this;
        }

        /**
         * Set the amount for the slot
         *
         * @param amount to set
         * @return this builder
         */
        public Builder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        /**
         * Set update interval for the slot
         *
         * @param interval to set
         * @return this builder
         */
        public Builder setUpdateInterval(int interval) {
            this.updateInterval = interval;
            return this;
        }

        /**
         * Add lore to the slot
         *
         * @param lore to add
         * @return this builder
         */
        public Builder addLore(String lore) {
            this.lore.add(lore);
            return this;
        }

        /**
         * Add lore to the slot
         *
         * @param lore to add
         * @return this builder
         */
        public Builder addLore(String... lore) {
            for (String l : lore) {
                this.addLore(l);
            }
            return this;
        }

        /**
         * Add lore to the slot
         *
         * @param lore to add
         * @return this builder
         */
        public Builder addLore(Collection<String> lore) {
            for (String l : lore) {
                this.addLore(l);
            }
            return this;
        }

        /**
         * Add an enchantment to the slot
         *
         * @param enchant to add
         * @return this builder
         */
        public Builder addEnchant(EnchantmentWrapper enchant) {
            this.enchants.add(enchant);
            return this;
        }

        /**
         * Add an enchantment to the slot
         *
         * @param enchant to add
         * @return this builder
         */
        public Builder addEnchant(EnchantmentWrapper... enchant) {
            for (EnchantmentWrapper ench : enchant) {
                this.addEnchant(ench);
            }
            return this;
        }

        /**
         * Add an enchantment to the slot
         *
         * @param enchant to add
         * @return this builder
         */
        public Builder addEnchant(List<EnchantmentWrapper> enchant) {
            for (EnchantmentWrapper ench : enchant) {
                this.addEnchant(ench);
            }

            return this;
        }

        /**
         * Add item flags to the slot
         *
         * @param itemFlags to add
         * @return this builder
         */
        public Builder addItemFlag(String... itemFlags) {
            this.addItemFlag(Arrays.asList(itemFlags));
            return this;
        }

        /**
         * Add item flags to the slot
         *
         * @param itemFlags to add
         * @return this builder
         */
        public Builder addItemFlag(Collection<String> itemFlags) {
            this.itemFlags.addAll(itemFlags);
            return this;
        }

        /**
         * Sets the function tree
         *
         * @param functionTree to set
         * @return this builder
         */
        public Builder setFunctionTree(FunctionTree functionTree) {
            this.functionTree = functionTree;
            return this;
        }

        /**
         * Adds metadata to the slot
         *
         * @param key to add
         * @param value to add
         * @return this builder
         */
        public Builder addMetadata(String key, String value) {
            this.metadata.put(key, value);
            return this;
        }

        /**
         * Adds metadata to the slot
         *
         * @param metadata map of metadata to add
         * @return this builder
         */
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

        /**
         * Create builder from item stack wrapper
         *
         * @param itemStackWrapper to copy from
         * @return this builder
         */
        public Builder fromItemStackWrapper(ItemStackWrapper<?> itemStackWrapper) {
            this.setIcon(itemStackWrapper.getType());
            this.setName(itemStackWrapper.getName());
            this.setNBT(itemStackWrapper.getNBT());
            this.setData(itemStackWrapper.getDurability());
            this.setAmount(itemStackWrapper.getAmount());
            this.addLore(itemStackWrapper.getLore());
            this.addItemFlag(itemStackWrapper.getItemFlags());

            for (EnchantmentWrapper enchant : itemStackWrapper.getEnchants()) {
                this.addEnchant(enchant);
            }

            return this;
        }

        /**
         * Builds slot with built parameters
         *
         * @return built slot
         */
        public Slot build() {
            return SLOT_FACTORY.create(this.index, this.amount, this.icon, this.name,
                    this.nbt, this.data, this.glow, this.movable,
                    this.close, this.lore, this.enchants, this.itemFlags,
                    this.modelProvider, this.modelData, this.functionTree,
                    this.updateInterval, this.metadata);
        }
    }
}