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

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.gui.GuiBuildType;
import com.clubobsidian.dynamicgui.api.gui.Slot;
import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.api.manager.inventory.InventoryManager;
import com.clubobsidian.dynamicgui.api.manager.replacer.ReplacerManager;
import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.api.world.LocationWrapper;
import com.clubobsidian.dynamicgui.core.util.ChatColor;
import org.apache.commons.lang3.SerializationUtils;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleGui implements Gui {

    /**
     *
     */
    private static final long serialVersionUID = -6294818826223305057L;
    private final List<Slot> slots;
    private final String name;
    private final String type;
    private final String title;
    private final int rows;
    private Boolean close;
    private final GuiBuildType guiBuildType;
    private final List<LocationWrapper<?>> locations;
    private final Map<String, List<Integer>> npcIds;
    private transient InventoryWrapper<?> inventoryWrapper;
    private final FunctionTree functions;
    private Gui back;
    private final Map<String, String> metadata;
    private final boolean isStatic;

    public SimpleGui(String name, String type, String title, int rows, Boolean close,
                     GuiBuildType guiBuildType, Map<String, List<Integer>> npcIds, List<Slot> slots,
                     List<LocationWrapper<?>> locations, FunctionTree functions, Map<String, String> metadata,
                     boolean isStatic) {
        this.name = name;
        this.type = type;
        this.title = ChatColor.translateAlternateColorCodes(title);
        this.rows = rows;
        this.slots = slots;
        this.close = close;
        this.guiBuildType = guiBuildType;
        this.npcIds = this.loadNpcIds(npcIds);
        this.locations = Collections.unmodifiableList(locations);
        this.inventoryWrapper = null;
        this.functions = functions;
        this.back = null;
        this.metadata = metadata;
        this.isStatic = isStatic;
    }

    private Map<String, List<Integer>> loadNpcIds(Map<String, List<Integer>> initialIds) {
        Map<String, List<Integer>> ids = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : initialIds.entrySet()) {
            ids.put(entry.getKey(), Collections.unmodifiableList(entry.getValue()));
        }
        return Collections.unmodifiableMap(ids);
    }

    public InventoryWrapper<?> buildInventory(PlayerWrapper<?> playerWrapper) {
        if (this.isStatic && this.inventoryWrapper != null) { //Don't rebuild if gui is static
            return this.inventoryWrapper;
        }
        String inventoryTitle = this.formatTitle(playerWrapper);
        Object nativeInventory = this.createInventory(inventoryTitle);
        InventoryWrapper<?> inventoryWrapper = InventoryManager.get().createInventoryWrapper(nativeInventory);
        this.populateInventory(playerWrapper, inventoryWrapper);
        this.inventoryWrapper = inventoryWrapper;
        return inventoryWrapper;
    }

    private String formatTitle(PlayerWrapper<?> playerWrapper) {
        String inventoryTitle = ReplacerManager.get().replace(this.title, playerWrapper);
        if (inventoryTitle.length() > 32) {
            inventoryTitle = inventoryTitle.substring(0, 31);
        }
        return inventoryTitle;
    }

    private Object createInventory(String inventoryTitle) {
        if (this.type == null || this.type.equals(InventoryType.CHEST.toString())) {
            return InventoryManager.get().createInventory(this.rows * 9, inventoryTitle);
        } else {
            return InventoryManager.get().createInventory(inventoryTitle, this.type);
        }
    }

    private void populateInventory(PlayerWrapper<?> playerWrapper, InventoryWrapper<?> inventoryWrapper) {
        try {
            Collection<Slot> slotsToRemove = new ArrayList<>();
            for (int i = 0; i < this.slots.size(); i++) {
                Slot slot = this.slots.get(i);
                if (slot != null) {
                    slot.setOwner(this);
                    ItemStackWrapper<?> item = slot.buildItemStack(playerWrapper);
                    if (item != null) {
                        if (this.guiBuildType == GuiBuildType.ADD) {
                            int itemIndex = inventoryWrapper.addItem(item);
                            if (itemIndex != -1) {
                                slot.setIndex(itemIndex);
                            }
                        } else {
                            inventoryWrapper.setItem(slot.getIndex(), item);
                        }
                    } else {
                        DynamicGui.get().getLogger().error(
                                "Removed slot '%d' from gui '%s' due to invalid configuration ",
                                i,
                                this.getName()
                        );
                        slotsToRemove.add(slot);
                    }
                }
            }
            for (Slot slot : slotsToRemove) {
                this.slots.remove(slot);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public int getRows() {
        return this.rows;
    }

    @Unmodifiable public List<Slot> getSlots() {
        return Collections.unmodifiableList(this.slots);
    }

    @Override
    public Boolean getClose() {
        return this.close;
    }

    @Override
    public void setClose(@Nullable Boolean close) {
        this.close = close;
    }

    public Map<String, List<Integer>> getNpcIds() {
        return this.npcIds;
    }

    public List<LocationWrapper<?>> getLocations() {
        return this.locations;
    }

    public GuiBuildType getBuildType() {
        return this.guiBuildType;
    }

    public InventoryWrapper<?> getInventoryWrapper() {
        return this.inventoryWrapper;
    }

    @Override
    public FunctionTree getFunctions() {
        return this.functions;
    }

    @Override
    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    public Gui getBack() {
        return this.back;
    }

    public void setBack(Gui back) {
        this.back = back;
    }

    public boolean isStatic() {
        return this.isStatic;
    }

    public Gui clone() {
        return SerializationUtils.clone(this);
    }
}