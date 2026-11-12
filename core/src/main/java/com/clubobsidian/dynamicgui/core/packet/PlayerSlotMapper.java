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

/**
 * Maps the player's inventory slots onto an open window's slots.
 * <p>
 * A window is laid out as the window's own slots, then the player's storage
 * (inventory slots 9-35) and then the hotbar (inventory slots 0-8).
 */
public final class PlayerSlotMapper {

    public static final int NO_SLOT = -1;

    private static final int HOTBAR_SIZE = 9;
    private static final int STORAGE_START = 9;
    private static final int STORAGE_END = 35;
    private static final int STORAGE_SIZE = STORAGE_END - STORAGE_START + 1;
    private static final int PLAYER_WINDOW_HOTBAR_START = 36;
    private static final int PLAYER_WINDOW_HOTBAR_END = 44;

    private PlayerSlotMapper() {
    }

    /**
     * Maps a player inventory index to a window slot
     *
     * @param windowSlots    the amount of slots the window has before the player's inventory
     * @param inventoryIndex the player inventory index, 0-8 hotbar and 9-35 storage
     * @return the window slot or {@link #NO_SLOT} if the index is not shown in windows
     */
    public static int toWindowSlot(int windowSlots, int inventoryIndex) {
        if (inventoryIndex >= 0 && inventoryIndex < HOTBAR_SIZE) {
            return windowSlots + STORAGE_SIZE + inventoryIndex;
        } else if (inventoryIndex >= STORAGE_START && inventoryIndex <= STORAGE_END) {
            return windowSlots + inventoryIndex - STORAGE_START;
        }
        return NO_SLOT;
    }

    /**
     * Maps a slot of the player's own inventory window (window 0) to a player inventory index
     *
     * @param playerWindowSlot the slot in the player's inventory window
     * @return the player inventory index or {@link #NO_SLOT} for crafting, armor and offhand slots
     */
    public static int fromPlayerWindowSlot(int playerWindowSlot) {
        if (playerWindowSlot >= STORAGE_START && playerWindowSlot <= STORAGE_END) {
            return playerWindowSlot;
        } else if (playerWindowSlot >= PLAYER_WINDOW_HOTBAR_START && playerWindowSlot <= PLAYER_WINDOW_HOTBAR_END) {
            return playerWindowSlot - PLAYER_WINDOW_HOTBAR_START;
        }
        return NO_SLOT;
    }
}
