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
package com.clubobsidian.dynamicgui.core.test.mock.manager;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.inventory.InventoryLimits;
import com.clubobsidian.dynamicgui.api.inventory.InventoryType;
import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.api.manager.packet.PacketManager;
import com.clubobsidian.dynamicgui.core.test.mock.inventory.MockInventory;
import com.clubobsidian.dynamicgui.core.test.mock.inventory.MockInventoryWrapper;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class MockPacketManager extends PacketManager {

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public InventoryWrapper<?> createPacketInventory(@NotNull Component title, @NotNull InventoryType type, int rows) {
        return new MockInventoryWrapper(new MockInventory(InventoryLimits.size(type, rows, true)));
    }

    @Override
    public @Nullable InventoryWrapper<?> getOpenInventory(@NotNull UUID uuid) {
        return null;
    }

    @Override
    public void sendSlot(@NotNull PlayerWrapper<?> playerWrapper, int slot, @NotNull ItemStackWrapper<?> itemStackWrapper) {
    }
}
