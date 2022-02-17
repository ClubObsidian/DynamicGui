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
import com.clubobsidian.dynamicgui.core.function.impl.SetNameFunction;
import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.dynamicgui.core.gui.Slot;
import com.clubobsidian.dynamicgui.core.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.MockFactory;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.gui.MockNonCloseableFunctionOwner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetNameFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void nullTest() {
        Function function = new SetNameFunction();
        assertFalse(function.function(this.factory.createPlayer()));
    }

    @Test
    public void notSlotTest() {
        Function function = new SetNameFunction();
        function.setData("asdf");
        function.setOwner(new MockNonCloseableFunctionOwner());
        assertFalse(function.function(this.factory.createPlayer()));
    }

    @Test
    public void nameTest() {
        String name = "asdf";
        MockPlayerWrapper player = this.factory.createPlayer();
        Slot slot = this.factory.createSlot(player);
        Gui gui = slot.getOwner();
        InventoryWrapper<?> inventory = gui.getInventoryWrapper();
        Function function = new SetNameFunction();
        function.setOwner(slot);
        function.setData(name);
        assertTrue(function.function(player));
        assertEquals(name, inventory.getItem(slot.getIndex()).getName());
    }
}