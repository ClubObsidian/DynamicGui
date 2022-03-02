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

package com.clubobsidian.dynamicgui.core.test.config;

import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.core.test.mock.MockFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessageTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void notNullTest() {
        this.factory.inject();
        assertNotNull(DynamicGui.get().getMessage());
    }

    @Test
    public void noGuiTest() {
        this.factory.inject();
        String noGuiMessage = DynamicGui.get().getMessage().getNoGui();
        assertNotNull(noGuiMessage);
        assertTrue(noGuiMessage.length() > 0);
    }
}