package com.clubobsidian.dynamicgui.test.mock;

import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.InventoryType;
import com.clubobsidian.dynamicgui.gui.ModeEnum;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.parser.function.tree.FunctionTree;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;

public class MockFactory {

    public <T> T mock(Class<T> mockClazz) {
        return Mockito.mock(mockClazz, Mockito.withSettings()
                .useConstructor()
                .defaultAnswer(Mockito.CALLS_REAL_METHODS));
    }

    public <T> T mock(Class<T> mockClazz, Object... args) {
        return Mockito.mock(mockClazz, Mockito.withSettings().useConstructor(args)
                .defaultAnswer(Mockito.CALLS_REAL_METHODS));
    }

    public MockPlayerWrapper createPlayer() {
        return this.mock(MockPlayerWrapper.class);
    }

    public MockItemStackWrapper createItemStack(String type) {
        MockItemStack itemStack = this.mock(MockItemStack.class, type);
        return this.mock(MockItemStackWrapper.class, itemStack);
    }

    public Gui createGui(String title) {
        return new Gui(title,
                InventoryType.CHEST.toString(),
                title,
                6,
                true,
                ModeEnum.SET,
                new HashMap<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new FunctionTree(),
                new HashMap<>());
    }

    public Slot createSlot(String type) {
        return this.createSlot(type, false);
    }

    public Slot createSlot(String type, boolean movable) {
        return new Slot(0,
                1,
                type,
                "test",
                null,
                (short) 0,
                false,
                movable,
                false,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                null,
                null,
                new FunctionTree(),
                0,
                new HashMap<>());
    }
}