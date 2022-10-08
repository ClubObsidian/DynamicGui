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

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public interface Gui extends Serializable, FunctionOwner, MetadataHolder, CloseableComponent {


    InventoryWrapper<?> buildInventory(PlayerWrapper<?> playerWrapper);

    String getName();

    String getType();

    String getTitle();

    int getRows();

    List<Slot> getSlots();

    Boolean getClose();

    void setClose(Boolean close);

    Map<String, List<Integer>> getNpcIds();

    List<LocationWrapper<?>> getLocations();

    ModeEnum getModeEnum();

    InventoryWrapper<?> getInventoryWrapper();

    FunctionTree getFunctions();

    Map<String, String> getMetadata();


    Gui getBack();

    void setBack(Gui back);

    boolean isStatic();

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
        private transient ModeEnum modeEnum;
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

        public Builder setModeEnum(String mode) {
            this.setModeEnum(ModeEnum.valueOf(mode));
            return this;
        }

        public Builder setModeEnum(ModeEnum modeEnum) {
            this.modeEnum = modeEnum;
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
                    this.close, this.modeEnum, this.npcIds, this.slots, this.locations,
                    this.functionTree, this.metadata, this.isStatic);
            if (this.backGui != null) {
                gui.setBack(this.backGui);
            }
            return gui;
        }
    }
}