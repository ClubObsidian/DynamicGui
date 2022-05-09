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

package com.clubobsidian.dynamicgui.bukkit.registry.model;

import com.clubobsidian.dynamicgui.core.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.core.manager.inventory.ItemStackManager;
import com.clubobsidian.dynamicgui.core.registry.model.ModelProvider;
import com.clubobsidian.dynamicgui.core.util.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class OraxenModelProvider implements ModelProvider {

    private static final boolean ORAXEN_EXISTS = ReflectionUtil
            .classExists("io.th0rgal.oraxen.items.OraxenItems");
    private static final Method GET_ITEM_BY_ID = getItemById();
    private static final Method BUILD = getBuild();


    private static Method getItemById() {
        if (ORAXEN_EXISTS) {
            try {
                Class<?> items = Class.forName("io.th0rgal.oraxen.items.OraxenItems");
                Method getItem = items.getDeclaredMethod("getItemById", String.class);
                getItem.setAccessible(true);
                return getItem;
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static Method getBuild() {
        if (ORAXEN_EXISTS) {
            try {
                Class<?> itemBuilder = Class.forName("io.th0rgal.oraxen.items.ItemBuilder");
                Method build = itemBuilder.getDeclaredMethod("build");
                build.setAccessible(true);
                return build;
            } catch (ClassNotFoundException | NoSuchMethodException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String name() {
        return "oraxen";
    }

    @Override
    public boolean applyModel(ItemStackWrapper<?> itemStack, String data) {
        try {
            Object itemBuilder = GET_ITEM_BY_ID.invoke(null, data);
            if (itemBuilder == null) {
                return false;
            }
            ItemStackWrapper<?> built = ItemStackManager.get().createItemStackWrapper(BUILD.invoke(itemBuilder));
            if (built.hasCustomModel()) {
                return itemStack.setModel(built.getModelData());
            }
            return false;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return false;
        }
    }
}
