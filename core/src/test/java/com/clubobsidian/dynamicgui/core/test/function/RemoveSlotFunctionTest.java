/*
 *    Copyright 2021 Club Obsidian and contributors.
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

package com.clubobsidian.dynamicgui.core.test.function;

import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.function.impl.RemoveSlotFunction;
import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.dynamicgui.core.gui.Slot;
import com.clubobsidian.dynamicgui.core.test.mock.MockFactory;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RemoveSlotFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void randomDataTest() {
        Function function = new RemoveSlotFunction();
        function.setData(UUID.randomUUID().toString());
        assertFalse(function.function(this.factory.createPlayer()));
    }

    @Test
    public void noDataTest() {
        this.factory.inject();
        MockPlayerWrapper player = this.factory.createPlayer();
        Slot slot = this.factory.createSlot(0, Slot.TEST_MATERIAL, false);
        slot.buildItemStack(player);
        List<Slot> slots = new ArrayList<>();
        Gui gui = this.factory.createGui("test", slots);
        gui.buildInventory(player);
        slot.setOwner(gui);
        Function function = new RemoveSlotFunction();
        function.setOwner(slot);
        assertTrue(function.function(player));
    }

    @Test
    public void thisDataTest() {
        this.factory.inject();
        MockPlayerWrapper player = this.factory.createPlayer();
        Slot slot = this.factory.createSlot(0, Slot.TEST_MATERIAL, false);
        slot.buildItemStack(player);
        List<Slot> slots = new ArrayList<>();
        Gui gui = this.factory.createGui("test", slots);
        gui.buildInventory(player);
        slot.setOwner(gui);
        Function function = new RemoveSlotFunction();
        function.setData("this");
        function.setOwner(slot);
        assertTrue(function.function(player));
    }
}