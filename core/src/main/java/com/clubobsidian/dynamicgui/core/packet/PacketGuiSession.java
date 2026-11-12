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

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A packet inventory a player has open.
 * <p>
 * State ids keep increasing across a player's sessions, so a click whose state id is
 * lower than the first state id of the current session belongs to an earlier window.
 */
public class PacketGuiSession {

    /**
     * The window id used for packet inventories, it is outside of the
     * vanilla range of 1-100 so it never matches a real container.
     */
    public static final int WINDOW_ID = 101;

    private final UUID uuid;
    private final PacketInventoryWrapper inventoryWrapper;
    private final AtomicInteger stateId;
    private final int firstStateId;

    public PacketGuiSession(UUID uuid, PacketInventoryWrapper inventoryWrapper, int lastStateId) {
        this.uuid = uuid;
        this.inventoryWrapper = inventoryWrapper;
        this.stateId = new AtomicInteger(lastStateId);
        this.firstStateId = lastStateId + 1;
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

    public PacketInventoryWrapper getInventoryWrapper() {
        return this.inventoryWrapper;
    }

    public PacketInventory getInventory() {
        return this.inventoryWrapper.getInventory();
    }

    public int nextStateId() {
        return this.stateId.incrementAndGet();
    }

    public int getStateId() {
        return this.stateId.get();
    }

    /**
     * Whether a click was made in a window opened before this session
     *
     * @param clickStateId the state id the client sent with the click
     * @return if the click is from an earlier window
     */
    public boolean isStale(int clickStateId) {
        return clickStateId < this.firstStateId;
    }
}
