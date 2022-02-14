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

package com.clubobsidian.dynamicgui.core.test.function;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.function.impl.SetLoreFunction;
import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.dynamicgui.core.gui.Slot;
import com.clubobsidian.dynamicgui.core.test.mock.MockFactory;
import com.clubobsidian.dynamicgui.core.test.mock.gui.MockNonCloseableFunctionOwner;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetLoreFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void nullTest() {
        PlayerWrapper<?> player = this.factory.createPlayer();
        String line = "test";
        List<String> lore = new ArrayList<>();
        lore.add(line);
        Slot slot = this.factory.createSlot(player, lore);
        Function function = new SetLoreFunction();
        function.setOwner(slot);
        Gui gui = slot.getOwner();
        assertTrue(function.function(player));
        assertTrue(gui.getInventoryWrapper().getItem(slot.getIndex()).getLore().size() == 0);
    }

    @Test
    public void nonSlotTest() {
        Function function = new SetLoreFunction();
        function.setOwner(new MockNonCloseableFunctionOwner());
        assertFalse(function.function(this.factory.createPlayer()));
    }

    @Test
    public void oneLineTest() {
        PlayerWrapper<?> player = this.factory.createPlayer();
        Slot slot = this.factory.createSlot(player);
        Function function = new SetLoreFunction();
        function.setData("test");
        function.setOwner(slot);
        Gui gui = slot.getOwner();
        assertTrue(function.function(player));
        List<String> lore = gui.getInventoryWrapper().getItem(slot.getIndex()).getLore();
        assertTrue(lore.size() == 1);
        assertEquals("test", lore.get(0));
    }

    @Test
    public void multiLineTest() {
        PlayerWrapper<?> player = this.factory.createPlayer();
        Slot slot = this.factory.createSlot(player);
        Function function = new SetLoreFunction();
        function.setData("test1\ntest2");
        function.setOwner(slot);
        Gui gui = slot.getOwner();
        assertTrue(function.function(player));
        List<String> lore = gui.getInventoryWrapper().getItem(slot.getIndex()).getLore();
        assertTrue(lore.size() == 2);
        assertEquals("test1", lore.get(0));
        assertEquals("test2", lore.get(1));
    }
}