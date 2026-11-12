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

import com.clubobsidian.dynamicgui.api.inventory.InventoryLimits;
import com.clubobsidian.dynamicgui.api.inventory.InventoryType;
import com.clubobsidian.dynamicgui.core.packet.PacketMenuType;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PacketMenuTypeTest {

    @Test
    public void testIdsMatchProtocol() {
        assertEquals(0, PacketMenuType.GENERIC_9X1.getId());
        assertEquals(5, PacketMenuType.GENERIC_9X6.getId());
        assertEquals(6, PacketMenuType.GENERIC_3X3.getId());
        assertEquals(8, PacketMenuType.ANVIL.getId());
        assertEquals(9, PacketMenuType.BEACON.getId());
        assertEquals(12, PacketMenuType.CRAFTING.getId());
        assertEquals(16, PacketMenuType.HOPPER.getId());
        assertEquals(24, PacketMenuType.STONECUTTER.getId());
    }

    @Test
    public void testIdsAreUniqueAndContiguous() {
        Set<Integer> ids = new HashSet<>();
        for (PacketMenuType type : PacketMenuType.values()) {
            assertTrue(ids.add(type.getId()), type.toString());
        }
        for (int id = 0; id < PacketMenuType.values().length; id++) {
            assertTrue(ids.contains(id));
        }
        assertEquals(25, ids.size());
    }

    @Test
    public void testChestMenus() {
        assertEquals(PacketMenuType.GENERIC_9X1, PacketMenuType.of(InventoryType.CHEST, 1));
        assertEquals(PacketMenuType.GENERIC_9X3, PacketMenuType.of(InventoryType.CHEST, 3));
        assertEquals(PacketMenuType.GENERIC_9X6, PacketMenuType.of(InventoryType.CHEST, 6));
        assertEquals(PacketMenuType.GENERIC_9X6, PacketMenuType.of(InventoryType.CHEST, 10));
        assertNull(PacketMenuType.of(InventoryType.CHEST, 0));
    }

    @Test
    public void testTypeMenus() {
        assertEquals(PacketMenuType.GENERIC_3X3, PacketMenuType.of(InventoryType.DISPENSER, 0));
        assertEquals(PacketMenuType.GENERIC_3X3, PacketMenuType.of(InventoryType.DROPPER, 0));
        assertEquals(PacketMenuType.GENERIC_9X3, PacketMenuType.of(InventoryType.ENDER_CHEST, 0));
        assertEquals(PacketMenuType.CRAFTING, PacketMenuType.of(InventoryType.WORKBENCH, 0));
        assertEquals(PacketMenuType.HOPPER, PacketMenuType.of(InventoryType.HOPPER, 4));
        assertNull(PacketMenuType.of(InventoryType.PLAYER, 0));
        assertNull(PacketMenuType.of(InventoryType.CRAFTING, 0));
    }

    @Test
    public void testEveryPacketTypeHasMenu() {
        for (InventoryType type : InventoryType.values()) {
            PacketMenuType menuType = PacketMenuType.of(type, InventoryLimits.MAX_CHEST_ROWS);
            assertEquals(InventoryLimits.supportsPacket(type), menuType != null, type.toString());
        }
    }
}
