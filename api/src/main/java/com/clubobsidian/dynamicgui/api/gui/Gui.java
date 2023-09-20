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

package com.clubobsidian.dynamicgui.api.gui;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.component.CloseableComponent;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.factory.FunctionTreeFactory;
import com.clubobsidian.dynamicgui.api.factory.GuiFactory;
import com.clubobsidian.dynamicgui.api.function.FunctionOwner;
import com.clubobsidian.dynamicgui.api.inventory.InventoryType;
import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.api.manager.gui.GuiManager;
import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.api.world.LocationWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;


public interface Gui extends Serializable, FunctionOwner, MetadataHolder, CloseableComponent {

    /**
     * Builds the inventory for the gui
     *
     * @param playerWrapper player wrapper to build the gui for
     * @return the built inventory wrapper
     */
    InventoryWrapper<?> buildInventory(@NotNull PlayerWrapper<?> playerWrapper);

    /**
     * The name of the gui without file extension, for example given a
     * gui named "test.yml" the name of the gui will be "test"
     *
     * @return the name of the gui
     */
    String getName();

    /**
     * The type of inventory that the built inventory represents, I.E CHEST etc.
     * Null is considered a chest
     *
     * @return the type of inventory for the underlying inventory
     */
    @Nullable String getType();

    /**
     * The title of the inventory for the gui, this may not represent what the
     * player currently sees.
     *
     * @return the initial title of the inventory
     */
    String getTitle();

    /**
     * The amount of rows that the gui has, this is
     * gui type dependent but should give you an idea of how
     * many slots can be populated.
     *
     * @return the number of rows
     */
    int getRows();

    /**
     * A list of slots for the gui
     *
     * @return list of slots
     */
    @Unmodifiable List<Slot> getSlots();

    /**
     * Map of npc registry names to a list of ids
     *
     * @return a map of npc registry names to a list of ids
     */
    @Unmodifiable Map<String, List<Integer>> getNpcIds();

    /**
     * The block locations that the gui can be opened with by interacting
     * with those locations
     *
     * @return a list of location wrappers
     */
    @Unmodifiable List<LocationWrapper<?>> getLocations();

    /**
     * How to the gui was built I.E: add or set.
     * In "add" mode the slots are added in fifo order into the
     * inventory ignoring the actual index number of the slot.
     * Slots added in "set" mode are added to the inventory
     * and set in their respective slot index.
     *
     * @return the gui build type
     */
    GuiBuildType getBuildType();

    /**
     * The built inventory for the gui. This starts as initially null
     * and on the first and on first build of the gui this gets set.
     *
     * @return the inventory wrapper for the gui
     */
    @Nullable InventoryWrapper<?> getInventoryWrapper();

    /**
     * The gui that came before this gui or a gui that was
     * set to be the previous gui.
     *
     * @return the previous gui
     */
    Gui getBack();

    /**
     * Sets the previous gui for this gui
     *
     * @param back the previous gui to set
     */
    void setBack(Gui back);

    /**
     * Returns if the gui is static. A static gui works
     * different from a normal gui, a normal gui gets
     * built per player and is built multiple times when
     * animated. Static guis instead should have no player
     * specific replacers and also only get built once per
     * iteration for animations.
     *
     * @return whether the gui is static
     */
    boolean isStatic();

    /**
     * Deep clones the gui
     *
     * @return the gui cloned
     */
    Gui clone();

    class Builder {

        @Inject
        private static GuiFactory GUI_FACTORY;
        @Inject
        private static FunctionTreeFactory TREE_FACTORY;

        private transient String name = UUID.randomUUID().toString();
        private transient String type = InventoryType.CHEST.toString();
        private transient String title;
        private transient int rows = 6;
        private transient Boolean close = true;
        private transient GuiBuildType guiBuildType = GuiBuildType.SET;
        private transient final Map<String, List<Integer>> npcIds = new HashMap<>();
        private transient final List<Slot> slots = new ArrayList<>();
        private transient final List<LocationWrapper<?>> locations = new ArrayList<>();
        private transient FunctionTree functionTree = TREE_FACTORY.create();
        private transient final Map<String, String> metadata = new HashMap<>();
        private transient Gui backGui;
        private transient boolean isStatic = false;
        private transient Boolean legacyIndexing;

        /**
         * Sets the inventory type
         *
         * @param type to set
         * @return this builder
         */
        public Builder setType(String type) {
            this.type = type.toUpperCase();
            return this;
        }

        /**
         * Sets the gui's name
         *
         * @param name to set
         * @return this builder
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the inventory's title
         *
         * @param title to set
         * @return this builder
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets the amount of rows an inventory should have
         *
         * @param rows to set
         * @return this builder
         */
        public Builder setRows(int rows) {
            this.rows = rows;
            return this;
        }

        /**
         * Set whether the gui should close
         *
         * @param close whether to close the gui
         * @return this builder
         */
        public Builder setClose(@Nullable Boolean close) {
            this.close = close;
            return this;
        }

        /**
         * Sets the gui build type
         *
         * @param type the type to set
         * @return this builder
         */
        public Builder setGuiBuildType(String type) {
            this.setGuiBuildType(GuiBuildType.valueOf(type));
            return this;
        }

        /**
         * Sets the gui build type
         *
         * @param type to set
         * @return this builder
         */
        public Builder setGuiBuildType(GuiBuildType type) {
            this.guiBuildType = type;
            return this;
        }

        /**
         * Adds a npc id that the gui should bind to
         *
         * @param plugin the plugin the npc belongs to
         * @param id     the npc id
         * @return this builder
         */
        public Builder addNpcId(String plugin, int id) {
            List<Integer> npcs = this.npcIds.get(plugin);
            if (npcs == null) {
                npcs = new ArrayList<>();
                this.npcIds.put(plugin, npcs);
            }

            npcs.add(id);
            return this;
        }

        /**
         * Adds npc ids that the gui should bind to
         *
         * @param plugin the plugin the npc belongs to
         * @param ids    the npc ids to add
         * @return this builder
         */
        public Builder addNpcId(String plugin, int[] ids) {
            for (int id : ids) {
                this.addNpcId(plugin, id);
            }
            return this;
        }

        /**
         * Adds npc ids that the gui should bind to
         *
         * @param plugin the plugin the npc belongs to
         * @param ids    the npc ids to add
         * @return this builder
         */
        public Builder addNpcId(String plugin, List<Integer> ids) {
            for (int id : ids) {
                this.addNpcId(plugin, id);
            }
            return this;
        }

        /**
         * Adds a slot to the gui
         *
         * @param slot to add
         * @return this builder
         */
        public Builder addSlot(Slot slot) {
            this.slots.add(slot);
            return this;
        }

        /**
         * Adds a location the gui should be able to be opened from
         *
         * @param loc to add
         * @return this builder
         */
        public Builder addLocation(LocationWrapper<?> loc) {
            this.locations.add(loc);
            return this;
        }

        /**
         * Set the gui's function tree
         *
         * @param functionTree to set
         * @return this builder
         */
        public Builder setFunctionTree(FunctionTree functionTree) {
            this.functionTree = functionTree;
            return this;
        }

        /**
         * Sets the back gui
         *
         * @param backGui to set
         * @return this builder
         */
        public Builder setBack(Gui backGui) {
            this.backGui = backGui;
            return this;
        }

        /**
         * Sets the back gui
         *
         * @param backGuiName to set
         * @return this builder
         */
        public Builder setBack(String backGuiName) {
            this.backGui = GuiManager.get().getGui(backGuiName);
            return this;
        }

        /**
         * Adds a metadata key value to the gui
         *
         * @param key   to add
         * @param value to add
         * @return this builder
         */
        public Builder addMetadata(String key, String value) {
            this.metadata.put(key, value);
            return this;
        }

        /**
         * Adds a map of metadata key values
         *
         * @param metadata to add
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
         * Set whether the gui should be static
         *
         * @param isStatic to set
         * @return this builder
         */
        public Builder setStatic(boolean isStatic) {
            this.isStatic = isStatic;
            return this;
        }

        /**
         * Sets whether the gui should use legacy indexing,
         * indexing that starts at 0 instead of 1
         *
         * @param legacyIndexing to set
         * @return this builder
         */
        public Builder setLegacyIndexing(boolean legacyIndexing) {
            this.legacyIndexing = legacyIndexing;
            return this;
        }

        private boolean hasZeroSlot() {
            for (Slot slot : this.slots) {
                if (slot.getIndex() == 0) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Build the gui based off the builder parameters
         *
         * @return the built Gui
         */
        public Gui build() {
            boolean legacyIndexing = hasZeroSlot() ? true :
                    this.legacyIndexing != null ? this.legacyIndexing :
                    DynamicGui.get().getConfig().getLegacyIndexing();
            Gui gui = GUI_FACTORY.create(this.name, this.type, this.title, this.rows,
                    this.close, this.guiBuildType, this.npcIds, this.slots, this.locations,
                    this.functionTree, this.metadata, this.isStatic, legacyIndexing);
            if (this.backGui != null) {
                gui.setBack(this.backGui);
            }
            return gui;
        }
    }
}