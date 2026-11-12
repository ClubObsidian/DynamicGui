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

package com.clubobsidian.dynamicgui.api.manager.packet;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.inventory.InventoryType;
import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Handles everything that is sent to players as packets,
 * this includes packet guis which only exist on the client.
 */
public abstract class PacketManager {

    /**
     * Starts the packet manager, packet listeners are registered here
     */
    public abstract void start();

    /**
     * Stops the packet manager, open packet guis are closed and packet listeners are unregistered
     */
    public abstract void stop();

    /**
     * Creates a client side only inventory
     *
     * @param title the title of the inventory
     * @param type  the inventory type
     * @param rows  for chests the amount of rows, for other types the
     *              amount of player inventory rows the inventory extends into
     * @return the packet inventory
     */
    public abstract InventoryWrapper<?> createPacketInventory(@NotNull Component title,
                                                              @NotNull InventoryType type,
                                                              int rows);

    /**
     * Gets the packet inventory a player currently has open
     *
     * @param uuid the uuid of the player
     * @return the open packet inventory or null if none is open
     */
    @Nullable
    public abstract InventoryWrapper<?> getOpenInventory(@NotNull UUID uuid);

    /**
     * Sends a slot update for the native inventory the player currently has open
     *
     * @param playerWrapper    the player to send the update to
     * @param slot             the slot to update
     * @param itemStackWrapper the item to send
     */
    public abstract void sendSlot(@NotNull PlayerWrapper<?> playerWrapper,
                                  int slot,
                                  @NotNull ItemStackWrapper<?> itemStackWrapper);
}
