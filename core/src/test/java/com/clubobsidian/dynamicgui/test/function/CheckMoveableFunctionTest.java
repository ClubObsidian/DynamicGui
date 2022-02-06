package com.clubobsidian.dynamicgui.test.function;

import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.function.impl.CheckMoveableFunction;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.test.mock.MockFactory;
import com.clubobsidian.dynamicgui.test.mock.MockPlayer;
import com.clubobsidian.dynamicgui.test.mock.MockPlayerWrapper;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CheckMoveableFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void nullTest() {
        Function function = new CheckMoveableFunction();
        MockPlayerWrapper wrapper = this.factory.createPlayer();
        assertFalse(function.function(wrapper));
    }

    @Test
    public void ownerNotSlotTest() {
        Function function = new CheckMoveableFunction();
        function.setData("true");
        function.setOwner(this.factory.createGui("test"));
        MockPlayerWrapper wrapper = this.factory.createPlayer();
        assertFalse(function.function(wrapper));
    }

    @Test
    public void isMovableTest() {
        Function function = new CheckMoveableFunction();
        function.setData("true");
        function.setOwner(this.factory.createSlot("STONE", true));
        MockPlayerWrapper wrapper = this.factory.createPlayer();
        assertTrue(function.function(wrapper));
    }
}