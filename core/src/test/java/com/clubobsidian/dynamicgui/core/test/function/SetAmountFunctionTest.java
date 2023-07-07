/*
 *    Copyright 2018-2023 virustotalop
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

import com.clubobsidian.dynamicgui.api.function.Function;
import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.gui.Slot;
import com.clubobsidian.dynamicgui.api.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.core.function.SetAmountFunction;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SetAmountFunctionTest extends FactoryTest {

    @Test
    public void nullTest() throws Exception {
        Function function = new SetAmountFunction();
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void nonSlotOwnerTest() throws Exception {
        Function function = new SetAmountFunction();
        function.setOwner(this.getFactory().createGui("test"));
        function.setData("1");
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void invalidDataTest() throws Exception {
        this.getFactory().inject();
        MockPlayerWrapper player = this.getFactory().createPlayer();
        Slot slot = this.getFactory().createSlot();
        slot.buildItemStack(player);
        List<Slot> slots = new ArrayList<>();
        slots.add(slot);
        Gui gui = this.getFactory().createGui("test", slots);
        gui.buildInventory(player);
        slot.setOwner(gui);
        Function function = new SetAmountFunction();
        function.setOwner(slot);
        function.setData("a");
        assertFalse(function.function(player));
    }

    @Test
    public void dataTest() throws Exception {
        this.getFactory().inject();
        MockPlayerWrapper player = this.getFactory().createPlayer();
        int newAmount = 2;
        Slot slot = this.getFactory().createSlot();
        slot.buildItemStack(player);
        List<Slot> slots = new ArrayList<>();
        slots.add(slot);
        Gui gui = this.getFactory().createGui("test", slots);
        InventoryWrapper<?> inventory = gui.buildInventory(player);
        slot.setOwner(gui);
        Function function = new SetAmountFunction();
        function.setOwner(slot);
        function.setData("" + newAmount);
        assertTrue(function.function(player));
        assertEquals(newAmount, inventory.getItem(slot.getIndex()).getAmount());
    }
}