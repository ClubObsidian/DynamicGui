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

package com.clubobsidian.dynamicgui.core.packet;

import com.clubobsidian.dynamicgui.api.inventory.InventoryLimits;
import com.clubobsidian.dynamicgui.api.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The protocol's menu types, the id is what is sent in the open window packet.
 */
public enum PacketMenuType {

    GENERIC_9X1(0, "minecraft:generic_9x1"),
    GENERIC_9X2(1, "minecraft:generic_9x2"),
    GENERIC_9X3(2, "minecraft:generic_9x3"),
    GENERIC_9X4(3, "minecraft:generic_9x4"),
    GENERIC_9X5(4, "minecraft:generic_9x5"),
    GENERIC_9X6(5, "minecraft:generic_9x6"),
    GENERIC_3X3(6, "minecraft:generic_3x3"),
    CRAFTER_3X3(7, "minecraft:crafter_3x3"),
    ANVIL(8, "minecraft:anvil"),
    BEACON(9, "minecraft:beacon"),
    BLAST_FURNACE(10, "minecraft:blast_furnace"),
    BREWING_STAND(11, "minecraft:brewing_stand"),
    CRAFTING(12, "minecraft:crafting"),
    ENCHANTMENT(13, "minecraft:enchantment"),
    FURNACE(14, "minecraft:furnace"),
    GRINDSTONE(15, "minecraft:grindstone"),
    HOPPER(16, "minecraft:hopper"),
    LECTERN(17, "minecraft:lectern"),
    LOOM(18, "minecraft:loom"),
    MERCHANT(19, "minecraft:merchant"),
    SHULKER_BOX(20, "minecraft:shulker_box"),
    SMITHING(21, "minecraft:smithing"),
    SMOKER(22, "minecraft:smoker"),
    CARTOGRAPHY_TABLE(23, "minecraft:cartography_table"),
    STONECUTTER(24, "minecraft:stonecutter");

    private static final PacketMenuType[] CHESTS = {
            GENERIC_9X1,
            GENERIC_9X2,
            GENERIC_9X3,
            GENERIC_9X4,
            GENERIC_9X5,
            GENERIC_9X6
    };

    private final int id;
    private final String key;

    PacketMenuType(int id, String key) {
        this.id = id;
        this.key = key;
    }

    public int getId() {
        return this.id;
    }

    public String getKey() {
        return this.key;
    }

    /**
     * Gets the menu type a gui opens as, chests with more than
     * {@link InventoryLimits#MAX_CHEST_ROWS} rows open as a 6 row chest
     * and extend into the player's inventory.
     *
     * @param type the inventory type
     * @param rows the amount of rows
     * @return the menu type or null if the type can not be opened as a window
     */
    @Nullable
    public static PacketMenuType of(@NotNull InventoryType type, int rows) {
        return switch (type) {
            case CHEST -> {
                int chestRows = Math.min(rows, InventoryLimits.MAX_CHEST_ROWS);
                yield chestRows < 1 ? null : CHESTS[chestRows - 1];
            }
            case ANVIL -> ANVIL;
            case BEACON -> BEACON;
            case BREWING -> BREWING_STAND;
            case DISPENSER, DROPPER -> GENERIC_3X3;
            case ENCHANTING -> ENCHANTMENT;
            case ENDER_CHEST -> GENERIC_9X3;
            case FURNACE -> FURNACE;
            case HOPPER -> HOPPER;
            case MERCHANT -> MERCHANT;
            case SHULKER_BOX -> SHULKER_BOX;
            case SMOKER -> SMOKER;
            case WORKBENCH -> CRAFTING;
            case PLAYER, CRAFTING -> null;
        };
    }
}
