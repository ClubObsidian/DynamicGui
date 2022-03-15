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
import com.clubobsidian.dynamicgui.core.function.impl.SetTypeFunction;
import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.dynamicgui.core.gui.Slot;
import com.clubobsidian.dynamicgui.core.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.gui.MockNonCloseableFunctionOwner;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetTypeFunctionTest extends FactoryTest {

    @Test
    public void nullTest() {
        Function function = new SetTypeFunction();
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void notSlotTest() {
        Function function = new SetTypeFunction();
        function.setData(Slot.TEST_MATERIAL);
        function.setOwner(new MockNonCloseableFunctionOwner());
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void typeTest() {
        String type = Slot.TEST_MATERIAL;
        MockPlayerWrapper player = this.getFactory().createPlayer();
        Slot slot = this.getFactory().createSlot(player);
        Gui gui = slot.getOwner();
        InventoryWrapper<?> inventory = gui.getInventoryWrapper();
        Function function = new SetTypeFunction();
        function.setOwner(slot);
        function.setData(type);
        assertTrue(function.function(player));
        assertEquals(type, inventory.getItem(slot.getIndex()).getType());
    }
}