package com.clubobsidian.dynamicgui.core.test.function;

import com.clubobsidian.dynamicgui.core.function.CheckOpenInventorySlotsFunction;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckOpenInventorySlotsFunctionTest extends FactoryTest {

    @Test
    public void nullTest() throws Exception {
        CheckOpenInventorySlotsFunction function = new CheckOpenInventorySlotsFunction();
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void notDigitTest() throws Exception {
        CheckOpenInventorySlotsFunction function = new CheckOpenInventorySlotsFunction();
        function.setData("a");
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void hasOpenSlots() throws Exception {
        CheckOpenInventorySlotsFunction function = new CheckOpenInventorySlotsFunction();
        function.setData("1");
        MockPlayerWrapper player = this.getFactory().createPlayer();
        player.setOpenInventorySlots(1);
        assertTrue(function.function(player));
    }

    @Test
    public void doesNotHaveOpenSlots() throws Exception {
        CheckOpenInventorySlotsFunction function = new CheckOpenInventorySlotsFunction();
        function.setData("1");
        MockPlayerWrapper player = this.getFactory().createPlayer();
        player.setOpenInventorySlots(0);
        assertFalse(function.function(player));
    }
}
