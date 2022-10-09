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

package com.clubobsidian.dynamicgui.core.factory;

import com.clubobsidian.dynamicgui.api.enchantment.EnchantmentWrapper;
import com.clubobsidian.dynamicgui.api.factory.SlotFactory;
import com.clubobsidian.dynamicgui.api.gui.Slot;
import com.clubobsidian.dynamicgui.api.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.core.gui.SimpleSlot;

import java.util.List;
import java.util.Map;

public class SlotFactoryImpl implements SlotFactory {

    @Override
    public Slot create(int index, int amount, String icon, String name,
                       String nbt, short data, boolean glow,
                       boolean movable, Boolean close, List<String> lore,
                       List<EnchantmentWrapper> enchants, List<String> itemFlags, String modelProvider,
                       String modelData, FunctionTree functionTree, int updateInterval,
                       Map<String, String> metadata) {
        return new SimpleSlot(index, amount, icon, name,
                nbt, data, glow, movable,
                close, lore, enchants, itemFlags,
                modelProvider, modelData, functionTree,
                updateInterval, metadata);
    }
}