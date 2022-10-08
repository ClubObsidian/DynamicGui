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

package com.clubobsidian.dynamicgui.core.test.manager;

import com.clubobsidian.dynamicgui.api.gui.Gui;
import com.clubobsidian.dynamicgui.api.manager.GuiManager;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuiManagerTest extends FactoryTest {

    private static final String TEST_GUI = "test";

    @Test
    public void testGetGuiNotNull() {
        Gui gui = GuiManager.get().getGui(TEST_GUI);
        assertNotNull(gui);
    }

    @Test
    public void testHasGui() {
        assertTrue(GuiManager.get().isGuiLoaded(TEST_GUI));
    }

    @Test
    public void testGetGuis() {
        List<Gui> guis = GuiManager.get().getGuis();
        assertNotNull(guis);
        assertTrue(guis.size() > 1);
    }
}