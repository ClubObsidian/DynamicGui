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

import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.dynamicgui.core.gui.Slot;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.FunctionManager;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.test.ScheduledTest;
import com.clubobsidian.dynamicgui.parser.function.FunctionType;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FunctionManagerTest extends ScheduledTest {

    @Test
    public void testPassing() throws ExecutionException, InterruptedException {
        MockPlayerWrapper playerWrapper = this.getFactory().createPlayer();
        Gui gui = GuiManager.get().getGui("test");
        Slot slot = gui.getSlots().get(0);
        assertTrue(FunctionManager.get().tryFunctions(slot, FunctionType.CLICK, playerWrapper).get());
        assertTrue(playerWrapper.getIncomingChat().size() == 1);
        assertEquals("test", playerWrapper.getIncomingChat().get(0));
    }

    @Test
    public void testFailing() throws ExecutionException, InterruptedException {
        MockPlayerWrapper playerWrapper = this.getFactory().createPlayer();
        Gui gui = GuiManager.get().getGui("test");
        Slot slot = gui.getSlots().get(1);
        assertFalse(FunctionManager.get().tryFunctions(slot, FunctionType.CLICK, playerWrapper).get());
        assertTrue(playerWrapper.getIncomingChat().size() == 1);
        assertEquals("fail", playerWrapper.getIncomingChat().get(0));
    }

    @Test
    public void testAsync() throws ExecutionException, InterruptedException {
        MockPlayerWrapper playerWrapper = this.getFactory().createPlayer();
        Gui gui = GuiManager.get().getGui("test");
        Slot slot = gui.getSlots().get(2);
        long time = System.currentTimeMillis();
        assertTrue(FunctionManager.get().tryFunctions(slot, FunctionType.CLICK, playerWrapper).get());
        assertTrue(playerWrapper.getIncomingChat().size() == 1);
        assertEquals("async", playerWrapper.getIncomingChat().get(0));
        long delta = System.currentTimeMillis() - time;
        assertTrue(delta >= 500);
    }

    @Test
    public void testMainThread() throws ExecutionException, InterruptedException {
        MockPlayerWrapper playerWrapper = this.getFactory().createPlayer();
        Gui gui = GuiManager.get().getGui("test");
        Slot slot = gui.getSlots().get(3);
        assertTrue(FunctionManager.get().tryFunctions(slot, FunctionType.CLICK, playerWrapper).get());
    }

    @Test
    public void testAsyncThread() throws ExecutionException, InterruptedException {
        MockPlayerWrapper playerWrapper = this.getFactory().createPlayer();
        Gui gui = GuiManager.get().getGui("test");
        Slot slot = gui.getSlots().get(4);
        assertTrue(FunctionManager.get().tryFunctions(slot, FunctionType.CLICK, playerWrapper).get());
    }
}