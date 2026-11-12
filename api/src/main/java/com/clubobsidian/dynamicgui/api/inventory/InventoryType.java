/*
 *    Copyright 2018-2025 virustotalop
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

package com.clubobsidian.dynamicgui.api.inventory;

import org.jetbrains.annotations.Nullable;

public enum InventoryType {

    ANVIL(3),
    BEACON(1),
    BREWING(5),
    //CARTOGRAPHY,
    CHEST(27),
    CRAFTING(5),
    DISPENSER(9),
    DROPPER(9),
    ENCHANTING(2),
    ENDER_CHEST(27),
    FURNACE(3),
    //GRINDSTONE,
    HOPPER(5),
    //LECTERN,
    //LOOM,
    MERCHANT(3),
    PLAYER(41),
    SHULKER_BOX(27),
    //SMITHING,
    SMOKER(3),
    //STONECUTTER,
    WORKBENCH(10);

    private final int slots;

    InventoryType(int slots) {
        this.slots = slots;
    }

    /**
     * Gets the amount of slots the type's own window has. Chest guis are
     * sized by their rows so for {@link #CHEST} this is the default size.
     *
     * @return the amount of slots
     */
    public int getSlots() {
        return this.slots;
    }

    /**
     * Gets an inventory type from a string ignoring case
     *
     * @param type the type to get
     * @return the inventory type or null if there is no such type
     */
    @Nullable
    public static InventoryType fromString(@Nullable String type) {
        if (type == null) {
            return null;
        }
        for (InventoryType inventoryType : values()) {
            if (inventoryType.name().equalsIgnoreCase(type)) {
                return inventoryType;
            }
        }
        return null;
    }
}
