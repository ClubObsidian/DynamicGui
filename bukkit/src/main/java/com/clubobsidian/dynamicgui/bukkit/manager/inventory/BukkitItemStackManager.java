/*
 *    Copyright 2018-2023 virustotalop
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

package com.clubobsidian.dynamicgui.bukkit.manager.inventory;

import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.api.manager.inventory.ItemStackManager;
import com.clubobsidian.dynamicgui.bukkit.inventory.BukkitItemStackWrapper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class BukkitItemStackManager extends ItemStackManager {

    @Override
    @Nullable
    public Object createItemStack(String type, int quantity) {
        Material material = Material.getMaterial(type);
        return material == null ? null : new ItemStack(material, quantity);
    }

    @Override
    public ItemStackWrapper<?> createItemStackWrapper(Object itemStack) {
        return itemStack == null ? null : new BukkitItemStackWrapper<>((ItemStack) itemStack);
    }
}