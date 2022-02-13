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
import com.clubobsidian.dynamicgui.core.function.impl.SetAmountFunction;
import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.dynamicgui.core.gui.Slot;
import com.clubobsidian.dynamicgui.core.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.MockFactory;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayer;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetAmountFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void nullTest() {
        Function function = new SetAmountFunction();
        assertFalse(function.function(this.factory.createPlayer()));
    }

    @Test
    public void nonSlotOwnerTest() {
        Function function = new SetAmountFunction();
        function.setOwner(this.factory.createGui("test"));
        function.setData("1");
        assertFalse(function.function(this.factory.createPlayer()));
    }

    @Test
    public void dataTest() {
        this.factory.inject();
        MockPlayerWrapper player = this.factory.createPlayer();
        int newAmount = 2;
        Slot slot = this.factory.createSlot();
        slot.buildItemStack(player);
        List<Slot> slots = new ArrayList<>();
        slots.add(slot);
        Gui gui = this.factory.createGui("test", slots);
        InventoryWrapper<?> inventory = gui.buildInventory(player);
        slot.setOwner(gui);
        Function function = new SetAmountFunction();
        function.setOwner(slot);
        function.setData("" + newAmount);
        assertTrue(function.function(player));
        assertEquals(newAmount, inventory.getItem(slot.getIndex()).getAmount());
    }
}