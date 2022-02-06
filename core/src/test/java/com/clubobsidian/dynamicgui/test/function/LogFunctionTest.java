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

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.function.impl.LogFunction;
import com.clubobsidian.dynamicgui.test.mock.MockFactory;
import com.clubobsidian.dynamicgui.test.mock.logger.MockLogger;
import com.clubobsidian.dynamicgui.test.mock.logger.MockLoggerWrapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LogFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void nullTest() {
        Function function = new LogFunction();
        PlayerWrapper<?> player = this.factory.createPlayer();
        assertFalse(function.function(player));
    }

    @Test
    public void withDataTest() {
        String data = "test";
        Function function = new LogFunction();
        function.setData(data);
        MockLoggerWrapper wrapper = this.factory.inject().getLogger();
        MockLogger logger = wrapper.getLogger();
        PlayerWrapper<?> player = this.factory.createPlayer();
        assertTrue(function.function(player));
        assertTrue(logger.getInfoMessages().size() == 1);
        assertEquals(data, logger.getInfoMessages().get(0));
    }
}