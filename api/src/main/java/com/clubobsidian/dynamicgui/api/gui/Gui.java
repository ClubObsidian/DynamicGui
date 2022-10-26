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

import com.clubobsidian.dynamicgui.api.component.CloseableComponent;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.factory.FunctionTreeFactory;
import com.clubobsidian.dynamicgui.api.factory.GuiFactory;
import com.clubobsidian.dynamicgui.api.function.FunctionOwner;
import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.api.manager.gui.GuiManager;
import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.api.world.LocationWrapper;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public interface Gui extends Serializable, FunctionOwner, MetadataHolder, CloseableComponent {

    /**
     * Builds the inventory for the gui
     *
     * @param playerWrapper player wrapper to build the gui for
     * @return the built inventory wrapper
     */
    InventoryWrapper<?> buildInventory(PlayerWrapper<?> playerWrapper);

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
     * How to the gui was built I.E: add or set mode
     * In "add" mode the slots are added in fifo order into the
     * inventory ignoring the actual index number of the slot.
     * Slots added in "set" mode are added to the inventory
     * and set in their respective slot index.
     *
     * @return the gui build mode
     */
    GuiMode getGuiMode();

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

        private transient String name;
        private transient String type;
        private transient String title;
        private transient int rows;
        private transient Boolean close;
        private transient GuiMode guiMode;
        private transient final Map<String, List<Integer>> npcIds = new HashMap<>();
        private transient final List<Slot> slots = new ArrayList<>();
        private transient final List<LocationWrapper<?>> locations = new ArrayList<>();
        private transient FunctionTree functionTree = TREE_FACTORY.create();
        private transient final Map<String, String> metadata = new HashMap<>();
        private transient Gui backGui;
        private transient boolean isStatic = false;

        public Builder setType(String type) {
            this.type = type.toUpperCase();
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setRows(int rows) {
            this.rows = rows;
            return this;
        }

        public Builder setClose(Boolean close) {
            this.close = close;
            return this;
        }

        public Builder setGuiMode(String mode) {
            this.setGuiMode(GuiMode.valueOf(mode));
            return this;
        }

        public Builder setGuiMode(GuiMode guiMode) {
            this.guiMode = guiMode;
            return this;
        }

        public Builder addNpcId(String plugin, Integer id) {
            List<Integer> npcs = this.npcIds.get(plugin);
            if (npcs == null) {
                npcs = new ArrayList<>();
                this.npcIds.put(plugin, npcs);
            }

            npcs.add(id);
            return this;
        }

        public Builder addNpcId(String plugin, Integer[] npcIds) {
            for (Integer id : npcIds) {
                this.addNpcId(plugin, id);
            }
            return this;
        }

        public Builder addNpcId(String plugin, List<Integer> npcIds) {
            for (Integer id : npcIds) {
                this.addNpcId(plugin, id);
            }
            return this;
        }

        public Builder addSlot(Slot slot) {
            this.slots.add(slot);
            return this;
        }

        public Builder addLocation(LocationWrapper<?> loc) {
            this.locations.add(loc);
            return this;
        }

        public Builder setFunctionTree(FunctionTree functionTree) {
            this.functionTree = functionTree;
            return this;
        }

        public Builder setBack(Gui backGui) {
            this.backGui = backGui;
            return this;
        }

        public Builder setBack(String backGuiName) {
            this.backGui = GuiManager.get().getGui(backGuiName);
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

        public Builder setStatic(boolean isStatic) {
            this.isStatic = isStatic;
            return this;
        }

        public Gui build() {
            Gui gui = GUI_FACTORY.create(this.name, this.type, this.title, this.rows,
                    this.close, this.guiMode, this.npcIds, this.slots, this.locations,
                    this.functionTree, this.metadata, this.isStatic);
            if (this.backGui != null) {
                gui.setBack(this.backGui);
            }
            return gui;
        }
    }
}