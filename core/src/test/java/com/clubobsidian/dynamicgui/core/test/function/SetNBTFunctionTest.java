/*
 *    Copyright 2022 virustotalop and contributors.
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
import com.clubobsidian.dynamicgui.core.function.SetNBTFunction;
import com.clubobsidian.dynamicgui.mock.entity.player.MockPlayerWrapper;
import com.clubobsidian.dynamicgui.mock.gui.MockNonCloseableFunctionOwner;
import com.clubobsidian.dynamicgui.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetNBTFunctionTest extends FactoryTest {

    @Test
    public void nullTest() throws Exception {
        Function function = new SetNBTFunction();
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void notSlotTest() throws Exception {
        Function function = new SetNBTFunction();
        function.setData("asdf");
        function.setOwner(new MockNonCloseableFunctionOwner());
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void nbtTest() throws Exception {
        String nbt = "asdf";
        MockPlayerWrapper player = this.getFactory().createPlayer();
        Slot slot = this.getFactory().createSlot(player);
        Gui gui = slot.getOwner();
        InventoryWrapper<?> inventory = gui.getInventoryWrapper();
        Function function = new SetNBTFunction();
        function.setOwner(slot);
        function.setData(nbt);
        assertTrue(function.function(player));
        assertEquals(nbt, inventory.getItem(slot.getIndex()).getNBT());
    }
}