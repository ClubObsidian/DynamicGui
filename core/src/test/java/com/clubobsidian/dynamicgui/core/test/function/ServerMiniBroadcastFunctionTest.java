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
import com.clubobsidian.dynamicgui.core.function.ServerMiniBroadcastFunction;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.MiniMessageManager;
import com.clubobsidian.dynamicgui.core.test.mock.plugin.MockPlatform;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerMiniBroadcastFunctionTest extends FactoryTest {

    @Test
    public void nullTest() throws Exception {
        Function function = new ServerMiniBroadcastFunction();
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void dataTest() throws Exception {
        MockPlatform platform = this.getFactory().inject().getPlatform();
        String data = "test";
        String json = MiniMessageManager.get().toJson(data);
        Function function = new ServerMiniBroadcastFunction();
        function.setData(data);
        assertTrue(function.function(this.getFactory().createPlayer()));
        List<String> messages = platform.getBroadcastMessages();
        assertTrue(messages.size() == 1);
        assertEquals(json, messages.get(0));
    }
}