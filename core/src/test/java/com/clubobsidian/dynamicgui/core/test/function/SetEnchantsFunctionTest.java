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

package com.clubobsidian.dynamicgui.core.test.function;

import com.clubobsidian.dynamicgui.core.enchantment.EnchantmentWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.function.impl.SetEnchantsFunction;
import com.clubobsidian.dynamicgui.core.gui.Gui;
import com.clubobsidian.dynamicgui.core.gui.Slot;
import com.clubobsidian.dynamicgui.core.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.entity.player.MockPlayerWrapper;
import com.clubobsidian.dynamicgui.core.test.mock.gui.MockNonCloseableFunctionOwner;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetEnchantsFunctionTest extends FactoryTest {

    @Test
    public void nullTest() throws Exception {
        Function function = new SetEnchantsFunction();
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void noOwnerTest() throws Exception {
        Function function = new SetEnchantsFunction();
        function.setData(EnchantmentWrapper.TEST_ENCHANT_2 + ",1");
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void ownerNotSlotTest() throws Exception {
        Function function = new SetEnchantsFunction();
        function.setData(EnchantmentWrapper.TEST_ENCHANT_2 + ",1");
        function.setOwner(new MockNonCloseableFunctionOwner());
        assertFalse(function.function(this.getFactory().createPlayer()));
    }

    @Test
    public void testOneEnchant() throws Exception {
        int index = 0;
        this.getFactory().inject();
        MockPlayerWrapper player = this.getFactory().createPlayer();
        List<EnchantmentWrapper> enchants = new ArrayList<>();
        enchants.add(new EnchantmentWrapper(EnchantmentWrapper.TEST_ENCHANT_1, 1));
        Slot slot = this.getFactory().createSlot(index, Slot.TEST_MATERIAL, enchants, false);
        slot.buildItemStack(player);
        List<Slot> slots = new ArrayList<>();
        slots.add(slot);
        Gui gui = this.getFactory().createGui("test", slots);
        InventoryWrapper<?> inventory = gui.buildInventory(player);
        slot.setOwner(gui);
        Function function = new SetEnchantsFunction();
        function.setOwner(slot);
        function.setData(EnchantmentWrapper.TEST_ENCHANT_2 + ",1");
        assertTrue(function.function(this.getFactory().createPlayer()));
        assertTrue(inventory.getItem(index).getEnchants().size() == 1);
        assertEquals(EnchantmentWrapper.TEST_ENCHANT_2, inventory.getItem(index).getEnchants().get(0).getEnchant());
    }

    @Test
    public void testMultipleEnchants() throws Exception {
        int index = 0;
        this.getFactory().inject();
        MockPlayerWrapper player = this.getFactory().createPlayer();
        List<EnchantmentWrapper> enchants = new ArrayList<>();
        enchants.add(new EnchantmentWrapper(EnchantmentWrapper.TEST_ENCHANT_1, 1));
        Slot slot = this.getFactory().createSlot(index, Slot.TEST_MATERIAL, enchants, false);
        slot.buildItemStack(player);
        List<Slot> slots = new ArrayList<>();
        slots.add(slot);
        Gui gui = this.getFactory().createGui("test", slots);
        InventoryWrapper<?> inventory = gui.buildInventory(player);
        slot.setOwner(gui);
        Function function = new SetEnchantsFunction();
        function.setOwner(slot);
        function.setData(EnchantmentWrapper.TEST_ENCHANT_2 + ",1;" + EnchantmentWrapper.TEST_ENCHANT_3 + ",1");
        assertTrue(function.function(this.getFactory().createPlayer()));
        assertTrue(inventory.getItem(index).getEnchants().size() == 2);
        assertEquals(EnchantmentWrapper.TEST_ENCHANT_2, inventory.getItem(index).getEnchants().get(0).getEnchant());
        assertEquals(EnchantmentWrapper.TEST_ENCHANT_3, inventory.getItem(index).getEnchants().get(1).getEnchant());
    }
}