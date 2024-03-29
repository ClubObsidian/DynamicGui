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
import com.clubobsidian.dynamicgui.core.function.SetNameFunction;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.gui.MockNonCloseableFunctionOwner;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SetNameFunctionTest extends FactoryTest {

    @Test
    public void nullTest() throws Exception {
        Function function = new SetNameFunction();
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void notSlotTest() throws Exception {
        Function function = new SetNameFunction();
        function.setData("asdf");
        function.setOwner(new MockNonCloseableFunctionOwner());
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void nameTest() throws Exception {
        String name = "asdf";
        MockPlayerWrapper player = this.getFactory().createPlayer();
        Slot slot = this.getFactory().createSlot(player);
        Gui gui = slot.getOwner();
        InventoryWrapper<?> inventory = gui.getInventoryWrapper();
        Function function = new SetNameFunction();
        function.setOwner(slot);
        function.setData(name);
        assertTrue(function.function(player));
        assertEquals(name, inventory.getItem(slot.getIndex()).getName());
    }
}