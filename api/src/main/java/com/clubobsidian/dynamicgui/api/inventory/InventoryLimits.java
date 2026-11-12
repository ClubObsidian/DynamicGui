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

/**
 * Size limits for gui inventories.
 * <p>
 * Chest guis are sized by their rows. Non-packet chests are limited to
 * {@link #MAX_CHEST_ROWS} rows, packet chests can have up to {@link #MAX_PACKET_ROWS}
 * rows where every row past {@link #MAX_CHEST_ROWS} extends into the player's inventory:
 * 0-53 chest, 54-80 player storage, 81-89 hotbar.
 * <p>
 * Other types have a fixed amount of slots. Non-packet guis ignore rows, for packet
 * guis rows (0-{@link #PLAYER_INVENTORY_ROWS}) are the amount of player inventory rows
 * the gui extends into. A packet hopper with 4 rows has the slots 0-4 hopper,
 * 5-31 player storage and 32-40 hotbar.
 */
public final class InventoryLimits {

    public static final int ROW_SIZE = 9;
    public static final int MAX_CHEST_ROWS = 6;
    public static final int MAX_CHEST_SIZE = MAX_CHEST_ROWS * ROW_SIZE;
    public static final int PLAYER_INVENTORY_ROWS = 4;
    public static final int MAX_PACKET_ROWS = MAX_CHEST_ROWS + PLAYER_INVENTORY_ROWS;

    private InventoryLimits() {
    }

    /**
     * Whether a type can be opened as a packet gui, the player inventory
     * and its crafting grid are not windows the server can open.
     *
     * @param type the inventory type
     * @return if the type can be a packet gui
     */
    public static boolean supportsPacket(InventoryType type) {
        return type != InventoryType.PLAYER && type != InventoryType.CRAFTING;
    }

    /**
     * Gets the min amount of rows a gui can have
     *
     * @param type   the inventory type
     * @param packet whether the gui is a packet gui
     * @return the min amount of rows
     */
    public static int minRows(InventoryType type, boolean packet) {
        return type == InventoryType.CHEST && packet ? 1 : 0;
    }

    /**
     * Gets the max amount of rows a gui can have
     *
     * @param type   the inventory type
     * @param packet whether the gui is a packet gui
     * @return the max amount of rows
     */
    public static int maxRows(InventoryType type, boolean packet) {
        if (type == InventoryType.CHEST) {
            return packet ? MAX_PACKET_ROWS : MAX_CHEST_ROWS;
        }
        return packet ? PLAYER_INVENTORY_ROWS : Integer.MAX_VALUE;
    }

    /**
     * Checks whether the amount of rows is valid for a gui
     *
     * @param type   the inventory type
     * @param rows   the amount of rows
     * @param packet whether the gui is a packet gui
     * @return if the rows are within the limits
     */
    public static boolean isValidRows(InventoryType type, int rows, boolean packet) {
        return rows >= minRows(type, packet) && rows <= maxRows(type, packet);
    }

    /**
     * Gets the amount of slots the type's own window has, for chests above
     * {@link #MAX_CHEST_ROWS} rows this is the chest part of the window.
     *
     * @param type the inventory type
     * @param rows the amount of rows
     * @return the amount of slots of the window before the player's inventory
     */
    public static int windowSlots(InventoryType type, int rows) {
        if (type == InventoryType.CHEST) {
            return Math.min(rows, MAX_CHEST_ROWS) * ROW_SIZE;
        }
        return type.getSlots();
    }

    /**
     * Gets the amount of slots a gui has
     *
     * @param type   the inventory type
     * @param rows   the amount of rows
     * @param packet whether the gui is a packet gui
     * @return the amount of slots
     */
    public static int size(InventoryType type, int rows, boolean packet) {
        if (type == InventoryType.CHEST) {
            return rows * ROW_SIZE;
        }
        if (packet) {
            return type.getSlots() + rows * ROW_SIZE;
        }
        return type.getSlots();
    }
}
