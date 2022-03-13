package com.clubobsidian.dynamicgui.core.test.manager;

import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.dynamicgui.core.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.core.test.mock.MockFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuiManagerTest {

    private static final String TEST_GUI = "test";

    private final MockFactory factory = new MockFactory();

    @BeforeEach
    public void setup() {
        this.factory.inject();
    }

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
        List<Gui> guis = GuiManager.get().getGuis();;
        assertNotNull(guis);
        assertTrue(guis.size() == 1);
    }
}