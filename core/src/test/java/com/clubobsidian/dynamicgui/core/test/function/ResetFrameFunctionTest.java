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
import com.clubobsidian.dynamicgui.core.function.ResetFrameFunction;
import com.clubobsidian.dynamicgui.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResetFrameFunctionTest extends FactoryTest {

    @Test
    public void ownerNotSlotTest() throws Exception {
        Gui gui = this.getFactory().createGui("test");
        Function function = new ResetFrameFunction();
        function.setOwner(gui);
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void slotOwnerTest() throws Exception {
        Slot slot = this.getFactory().createSlot();
        Function function = new ResetFrameFunction();
        function.setOwner(slot);
        assertTrue(function.function(this.getFactory().createPlayer()));
    }
}