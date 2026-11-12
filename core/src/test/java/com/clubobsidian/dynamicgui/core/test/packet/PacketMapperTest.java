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

import com.clubobsidian.dynamicgui.core.event.inventory.Click;
import com.clubobsidian.dynamicgui.core.packet.PacketClickMapper;
import com.clubobsidian.dynamicgui.core.packet.PlayerSlotMapper;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow.WindowClickType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PacketMapperTest {

    @Test
    public void testClickMapping() {
        assertEquals(Click.LEFT, PacketClickMapper.toClick(WindowClickType.PICKUP, 0));
        assertEquals(Click.RIGHT, PacketClickMapper.toClick(WindowClickType.PICKUP, 1));
        assertEquals(Click.SHIFT_LEFT, PacketClickMapper.toClick(WindowClickType.QUICK_MOVE, 0));
        assertEquals(Click.SHIFT_RIGHT, PacketClickMapper.toClick(WindowClickType.QUICK_MOVE, 1));
        assertEquals(Click.MIDDLE, PacketClickMapper.toClick(WindowClickType.CLONE, 2));
        assertNull(PacketClickMapper.toClick(WindowClickType.SWAP, 0));
        assertNull(PacketClickMapper.toClick(WindowClickType.THROW, 0));
        assertNull(PacketClickMapper.toClick(WindowClickType.QUICK_CRAFT, 0));
        assertNull(PacketClickMapper.toClick(WindowClickType.PICKUP_ALL, 0));
        assertNull(PacketClickMapper.toClick(null, 0));
    }

    @Test
    public void testPlayerInventoryToWindowSlot() {
        assertEquals(54, PlayerSlotMapper.toWindowSlot(54, 9)); //First storage slot
        assertEquals(80, PlayerSlotMapper.toWindowSlot(54, 35)); //Last storage slot
        assertEquals(81, PlayerSlotMapper.toWindowSlot(54, 0)); //First hotbar slot
        assertEquals(89, PlayerSlotMapper.toWindowSlot(54, 8)); //Last hotbar slot
        assertEquals(40, PlayerSlotMapper.toWindowSlot(5, 8)); //Hopper hotbar
        assertEquals(PlayerSlotMapper.NO_SLOT, PlayerSlotMapper.toWindowSlot(54, 40)); //Offhand
    }

    @Test
    public void testPlayerWindowSlotToInventory() {
        assertEquals(9, PlayerSlotMapper.fromPlayerWindowSlot(9));
        assertEquals(35, PlayerSlotMapper.fromPlayerWindowSlot(35));
        assertEquals(0, PlayerSlotMapper.fromPlayerWindowSlot(36));
        assertEquals(8, PlayerSlotMapper.fromPlayerWindowSlot(44));
        assertEquals(PlayerSlotMapper.NO_SLOT, PlayerSlotMapper.fromPlayerWindowSlot(5)); //Armor
        assertEquals(PlayerSlotMapper.NO_SLOT, PlayerSlotMapper.fromPlayerWindowSlot(45)); //Offhand
    }
}
