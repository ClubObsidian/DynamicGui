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

package com.clubobsidian.dynamicgui.registry.model.plugin;

import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.manager.inventory.ItemStackManager;
import com.clubobsidian.dynamicgui.registry.model.ModelProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ItemsAdderModelProvider implements ModelProvider {

    private static final boolean ITEMS_ADDER_EXISTS = exists();
    private static final Method GET_INSTANCE = instance();
    private static final Method GET_ITEM_STACK = itemStack();

    private static boolean exists() {
        try {
            Class.forName("dev.lone.itemsadder.api.CustomStack");
            return true;
        } catch(ClassNotFoundException e) {
            return false;
        }
    }

    private static Method instance() {
        if(ITEMS_ADDER_EXISTS) {
            try {
                Class<?> customStack = Class.forName("dev.lone.itemsadder.api.CustomStack");
                Method getInstance = customStack.getDeclaredMethod("getInstance", String.class);
                getInstance.setAccessible(true);
                return getInstance;
            } catch(ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static Method itemStack() {
        if(ITEMS_ADDER_EXISTS) {
            try {
                Class<?> customStack= Class.forName("dev.lone.itemsadder.api.CustomStack");
                Method getItemStack = customStack.getDeclaredMethod("getItemStack");
                getItemStack.setAccessible(true);
                return getItemStack;
            } catch(ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String name() {
        return "itemsadder";
    }

    @Override
    public boolean applyModel(ItemStackWrapper<?> itemStack, String data) {
        if(ITEMS_ADDER_EXISTS) {
            try {
                Object customStack = GET_INSTANCE.invoke(null, data);
                if(customStack != null) {
                    ItemStackWrapper<?> wrappedCustom = ItemStackManager
                            .get()
                            .createItemStackWrapper(GET_ITEM_STACK.invoke(customStack));
                    if(wrappedCustom.hasCustomModel()) {
                        return itemStack.setModel(wrappedCustom.getModelData());
                    }
                }
            } catch(IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
