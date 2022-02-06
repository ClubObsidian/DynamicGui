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

package com.clubobsidian.dynamicgui.test.function;

import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.function.impl.ConsoleCmdFunction;
import com.clubobsidian.dynamicgui.test.mock.MockFactory;
import com.clubobsidian.dynamicgui.test.mock.plugin.MockPlatform;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConsoleCmdFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void nullTest() {
        Function function = new ConsoleCmdFunction();
        assertFalse(function.function(this.factory.createPlayer()));
    }

    @Test
    public void withDataTest() {
        String data = "test";
        MockPlatform platform = this.factory.inject().getPlatform();
        Function function = new ConsoleCmdFunction();
        function.setData(data);
        assertTrue(function.function(this.factory.createPlayer()));
        assertEquals(data, platform.getDispatchedServerCommands().get(0));
    }

}
