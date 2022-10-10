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
import com.clubobsidian.dynamicgui.core.function.CheckPlayerWorldFunction;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckPlayerWorldFunctionTest extends FactoryTest {

    @Test
    public void nullTest() throws Exception {
        Function function = new CheckPlayerWorldFunction();
        MockPlayerWrapper player = this.getFactory().createPlayer();
        assertFalse(function.function(player));
    }

    @Test
    public void worldEqualsTest() throws Exception {
        String worldName = "test";
        Function function = new CheckPlayerWorldFunction();
        function.setData(worldName);
        MockPlayerWrapper player = this.getFactory().createPlayer();
        player.setLocation(this.getFactory().createLocation(0, 0, 0, worldName));
        assertTrue(function.function(player));
    }
}
