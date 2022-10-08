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
import com.clubobsidian.dynamicgui.api.gui.Slot;
import com.clubobsidian.dynamicgui.api.manager.GuiManager;
import com.clubobsidian.dynamicgui.core.manager.FunctionManager;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.test.ScheduledTest;
import com.clubobsidian.dynamicgui.api.parser.function.FunctionType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FunctionManagerTest extends ScheduledTest {

    @Test
    public void testHasAsyncFunctionRunningPassing() throws InterruptedException, ExecutionException {
        MockPlayerWrapper playerWrapper = this.getFactory().createPlayer();
        Gui gui = GuiManager.get().getGui("test");
        Slot slot = gui.getSlots().get(2);
        CompletableFuture<Boolean> future = FunctionManager
                .get()
                .tryFunctions(slot, FunctionType.CLICK, playerWrapper);
        Thread.sleep(500);
        boolean has = FunctionManager.get().hasAsyncFunctionRunning(playerWrapper);
        future.get();
        assertTrue(has);
    }

    @Test
    public void testHasAsyncFunctionRunningFailing() throws ExecutionException, InterruptedException {
        MockPlayerWrapper playerWrapper = this.getFactory().createPlayer();
        Gui gui = GuiManager.get().getGui("test");
        Slot slot = gui.getSlots().get(2);
        FunctionManager.get().tryFunctions(slot, FunctionType.CLICK, playerWrapper).get();
        assertFalse(FunctionManager.get().hasAsyncFunctionRunning(playerWrapper));
    }

    @Test
    public void testHasAsyncFunctionRunningWithFunctionNamePassing() throws InterruptedException, ExecutionException {
        MockPlayerWrapper playerWrapper = this.getFactory().createPlayer();
        Gui gui = GuiManager.get().getGui("test");
        Slot slot = gui.getSlots().get(2);
        CompletableFuture<Boolean> future = FunctionManager
                .get()
                .tryFunctions(slot, FunctionType.CLICK, playerWrapper);
        Thread.sleep(500);
        boolean has = FunctionManager.get().hasAsyncFunctionRunning(playerWrapper, "delay");
        future.get();
        assertTrue(has);
    }

    @Test
    public void testHasAsyncFunctionRunningWithFunctionNameFailing() throws ExecutionException, InterruptedException {
        MockPlayerWrapper playerWrapper = this.getFactory().createPlayer();
        Gui gui = GuiManager.get().getGui("test");
        Slot slot = gui.getSlots().get(2);
        FunctionManager.get().tryFunctions(slot, FunctionType.CLICK, playerWrapper).get();
        assertFalse(FunctionManager.get().hasAsyncFunctionRunning(playerWrapper, "delay"));
    }

    @Test
    public void testTryFunctionsPassing() throws ExecutionException, InterruptedException {
        MockPlayerWrapper playerWrapper = this.getFactory().createPlayer();
        Gui gui = GuiManager.get().getGui("test");
        Slot slot = gui.getSlots().get(0);
        assertTrue(FunctionManager.get().tryFunctions(slot, FunctionType.CLICK, playerWrapper).get());
        assertTrue(playerWrapper.getIncomingChat().size() == 1);
        assertEquals("test", playerWrapper.getIncomingChat().get(0));
    }

    @Test
    public void testTryFunctionsFailing() throws ExecutionException, InterruptedException {
        MockPlayerWrapper playerWrapper = this.getFactory().createPlayer();
        Gui gui = GuiManager.get().getGui("test");
        Slot slot = gui.getSlots().get(1);
        assertFalse(FunctionManager.get().tryFunctions(slot, FunctionType.CLICK, playerWrapper).get());
        assertTrue(playerWrapper.getIncomingChat().size() == 1);
        assertEquals("fail", playerWrapper.getIncomingChat().get(0));
    }

    @Test
    public void testTryFunctionsAsync() throws ExecutionException, InterruptedException {
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
    public void testTryFunctionsMainThread() throws ExecutionException, InterruptedException {
        MockPlayerWrapper playerWrapper = this.getFactory().createPlayer();
        Gui gui = GuiManager.get().getGui("test");
        Slot slot = gui.getSlots().get(3);
        assertTrue(FunctionManager.get().tryFunctions(slot, FunctionType.CLICK, playerWrapper).get());
    }

    @Test
    public void testTryFunctionsAsyncThread() throws ExecutionException, InterruptedException {
        MockPlayerWrapper playerWrapper = this.getFactory().createPlayer();
        Gui gui = GuiManager.get().getGui("test");
        Slot slot = gui.getSlots().get(4);
        assertTrue(FunctionManager.get().tryFunctions(slot, FunctionType.CLICK, playerWrapper).get());
    }

    @Test
    public void testLoadWithClick() throws ExecutionException, InterruptedException {
        MockPlayerWrapper playerWrapper = this.getFactory().createPlayer();
        playerWrapper.addPermission("test");
        Gui gui = GuiManager.get().getGui("multi-function-test");
        Slot slot = gui.getSlots().get(0);
        boolean failed = FunctionManager.get().tryFunctions(slot, FunctionType.LOAD, playerWrapper).get();
        assertFalse(failed);
        List<String> chat = playerWrapper.getIncomingChat();
        assertEquals(1, chat.size());
        assertEquals("condition failed", chat.get(0));
    }

    @Test
    public void testMultipleLoadFail() throws ExecutionException, InterruptedException {
        MockPlayerWrapper playerWrapper = this.getFactory().createPlayer();
        playerWrapper.addPermission("test");
        Gui gui = GuiManager.get().getGui("multi-function-test");
        Slot slot = gui.getSlots().get(1);
        boolean failed = FunctionManager.get().tryFunctions(slot, FunctionType.LOAD, playerWrapper).get();
        assertFalse(failed);
        List<String> chat = playerWrapper.getIncomingChat();
        assertEquals(1, chat.size());
        assertEquals("test", chat.get(0));
    }

    @Test
    public void testFailNoFailFunction() throws ExecutionException, InterruptedException {
        MockPlayerWrapper playerWrapper = this.getFactory().createPlayer();
        Gui gui = GuiManager.get().getGui("no-fail-function");
        Slot slot = gui.getSlots().get(0);
        boolean failed = FunctionManager.get().tryFunctions(slot, FunctionType.LOAD, playerWrapper).get();
        assertFalse(failed);
    }

    @Test
    public void testNoFunctionType() throws ExecutionException, InterruptedException {
        MockPlayerWrapper playerWrapper = this.getFactory().createPlayer();
        playerWrapper.addPermission("test");
        Gui gui = GuiManager.get().getGui("test-no-function-type");
        Slot slot = gui.getSlots().get(0);
        boolean result = FunctionManager.get().tryFunctions(slot, FunctionType.LOAD, playerWrapper).get();
        assertTrue(result);
    }

    @Test
    public void testFailNoFailType() throws ExecutionException, InterruptedException {
        MockPlayerWrapper playerWrapper = this.getFactory().createPlayer();
        Gui gui = GuiManager.get().getGui("test-fail-no-fail-type");
        Slot slot = gui.getSlots().get(0);
        boolean result = FunctionManager.get().tryFunctions(slot, FunctionType.LOAD, playerWrapper).get();
        assertFalse(result);
    }
}