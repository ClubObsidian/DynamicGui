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
import com.clubobsidian.dynamicgui.core.function.CheckMovableFunction;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckMovableFunctionTest extends FactoryTest {

    @Test
    public void nullTest() throws Exception {
        Function function = new CheckMovableFunction();
        MockPlayerWrapper wrapper = this.getFactory().createPlayer();
        assertFalse(function.function(wrapper));
    }

    @Test
    public void ownerNotSlotTest() throws Exception {
        Function function = new CheckMovableFunction();
        function.setData("true");
        function.setOwner(this.getFactory().createGui("test"));
        MockPlayerWrapper wrapper = this.getFactory().createPlayer();
        assertFalse(function.function(wrapper));
    }

    @Test
    public void isMovableTest() throws Exception {
        Function function = new CheckMovableFunction();
        function.setData("true");
        function.setOwner(this.getFactory().createSlot("STONE", true));
        MockPlayerWrapper wrapper = this.getFactory().createPlayer();
        assertTrue(function.function(wrapper));
    }
}