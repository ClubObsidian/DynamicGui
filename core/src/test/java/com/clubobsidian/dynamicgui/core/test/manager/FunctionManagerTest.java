package com.clubobsidian.dynamicgui.core.test.manager;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.dynamicgui.core.gui.Slot;
import com.clubobsidian.dynamicgui.core.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.FunctionManager;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.core.test.mock.MockFactory;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayer;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
import com.clubobsidian.dynamicgui.parser.function.FunctionType;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FunctionManagerTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void testPassing() throws ExecutionException, InterruptedException {
        this.factory.inject();
        MockPlayerWrapper playerWrapper = this.factory.createPlayer();
        Gui gui = GuiManager.get().getGui("test");
        assertTrue(gui.getSlots().size() >= 1);
        Slot slot = gui.getSlots().get(0);
        assertTrue(FunctionManager.get().tryFunctions(slot, FunctionType.CLICK, playerWrapper).get());
        assertTrue(playerWrapper.getIncomingChat().size() == 1);
        assertEquals("test", playerWrapper.getIncomingChat().get(0));
    }

    @Test
    public void testFailing() throws ExecutionException, InterruptedException {
        this.factory.inject();
        MockPlayerWrapper playerWrapper = this.factory.createPlayer();
        Gui gui = GuiManager.get().getGui("test");
        assertTrue(gui.getSlots().size() >= 1);
        Slot slot = gui.getSlots().get(1);
        assertFalse(FunctionManager.get().tryFunctions(slot, FunctionType.CLICK, playerWrapper).get());
        assertTrue(playerWrapper.getIncomingChat().size() == 1);
        assertEquals("fail", playerWrapper.getIncomingChat().get(0));
    }
}