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
import com.clubobsidian.dynamicgui.core.function.impl.LogFunction;
import com.clubobsidian.dynamicgui.core.test.mock.logger.MockLogger;
import com.clubobsidian.dynamicgui.core.test.mock.logger.MockLoggerWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogFunctionTest extends FactoryTest {

    @Test
    public void nullTest() {
        Function function = new LogFunction();
        PlayerWrapper<?> player = this.getFactory().createPlayer();
        assertFalse(function.function(player));
    }

    @Test
    public void withDataTest() {
        String data = "test";
        Function function = new LogFunction();
        function.setData(data);
        MockLoggerWrapper wrapper = this.getFactory().inject().getLogger();
        MockLogger logger = wrapper.getLogger();
        PlayerWrapper<?> player = this.getFactory().createPlayer();
        assertTrue(function.function(player));
        assertTrue(logger.getInfoMessages().size() == 1);
        assertEquals(data, logger.getInfoMessages().get(0));
    }
}