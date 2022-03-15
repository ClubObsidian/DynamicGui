package com.clubobsidian.dynamicgui.core.test.manager;

import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.dynamicgui.core.gui.Slot;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.FunctionManager;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.core.test.test.ScheduledTest;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
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