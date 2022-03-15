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

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.function.impl.SetMovableFunction;
import com.clubobsidian.dynamicgui.core.gui.Slot;
import com.clubobsidian.dynamicgui.core.test.mock.gui.MockNonCloseableFunctionOwner;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetMovableFunctionTest extends FactoryTest {

    @Test
    public void nullTest() {
        Function function = new SetMovableFunction();
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void notSlotTest() {
        Function function = new SetMovableFunction();
        function.setData("a");
        function.setOwner(new MockNonCloseableFunctionOwner());
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void setMovableFalseTest() {
        PlayerWrapper<?> player = this.getFactory().createPlayer();
        Function function = new SetMovableFunction();
        function.setData("false");
        Slot slot = this.getFactory().createSlot(player);
        function.setOwner(slot);
        assertTrue(function.function(this.getFactory().createPlayer()));
        assertFalse(slot.isMovable());
    }

    @Test
    public void setMovableTrueTest() {
        PlayerWrapper<?> player = this.getFactory().createPlayer();
        Function function = new SetMovableFunction();
        function.setData("true");
        Slot slot = this.getFactory().createSlot(player);
        function.setOwner(slot);
        assertTrue(function.function(this.getFactory().createPlayer()));
        assertTrue(slot.isMovable());
    }
}