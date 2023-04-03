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

package com.clubobsidian.dynamicgui.core.test.config;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessageTest extends FactoryTest {

    @Test
    public void notNullTest() {
        this.getFactory().inject();
        assertNotNull(DynamicGui.get().getConfig().getMessage());
    }

    @Test
    public void noGuiTest() {
        this.getFactory().inject();
        String noGuiMessage = DynamicGui.get().getConfig().getMessage().getNoGui();
        assertNotNull(noGuiMessage);
        assertTrue(noGuiMessage.length() > 0);
    }
}