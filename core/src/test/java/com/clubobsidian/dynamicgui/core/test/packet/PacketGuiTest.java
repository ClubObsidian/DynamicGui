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
package com.clubobsidian.dynamicgui.core.test.packet;

import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.gui.Slot;
import com.clubobsidian.dynamicgui.api.inventory.InventoryType;
import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import com.clubobsidian.dynamicgui.mock.logger.MockLogger;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PacketGuiTest extends FactoryTest {

    private InventoryWrapper<?> build(InventoryType type, int rows, boolean packet, List<Slot> slots) {
        MockPlayerWrapper player = this.getFactory().createPlayer();
        Gui gui = this.getFactory().createGui("test", type, rows, packet, slots);
        return gui.buildInventory(player);
    }

    @Test
    public void testOversizedChestIsNotBuilt() {
        MockLogger logger = this.getFactory().getLogger().getLogger();
        logger.clear();
        assertNull(this.build(InventoryType.CHEST, 7, false, new ArrayList<>()));
        assertEquals(1, logger.getErrorMessages().size());
    }

    @Test
    public void testOversizedPacketChestIsNotBuilt() {
        assertNull(this.build(InventoryType.CHEST, 11, true, new ArrayList<>()));
    }

    @Test
    public void testOversizedPacketHopperIsNotBuilt() {
        assertNull(this.build(InventoryType.HOPPER, 5, true, new ArrayList<>()));
    }

    @Test
    public void testPacketPlayerIsNotBuilt() {
        assertNull(this.build(InventoryType.PLAYER, 0, true, new ArrayList<>()));
    }

    @Test
    public void testPacketChestSize() {
        List<Slot> slots = new ArrayList<>();
        slots.add(this.getFactory().createSlot(89, "STONE", new ArrayList<>(), false));
        InventoryWrapper<?> inventory = this.build(InventoryType.CHEST, 10, true, slots);
        assertNotNull(inventory);
        assertEquals(90, inventory.getSize());
        assertEquals("STONE", inventory.getItem(89).getType());
    }

    @Test
    public void testPacketHopperSize() {
        List<Slot> slots = new ArrayList<>();
        slots.add(this.getFactory().createSlot(40, "STONE", new ArrayList<>(), false));
        InventoryWrapper<?> inventory = this.build(InventoryType.HOPPER, 4, true, slots);
        assertNotNull(inventory);
        assertEquals(41, inventory.getSize());
        assertEquals("STONE", inventory.getItem(40).getType());
    }
}
