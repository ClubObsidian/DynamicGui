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

package com.clubobsidian.dynamicgui.bukkit.registry.model;

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.api.manager.inventory.ItemStackManager;
import com.clubobsidian.dynamicgui.api.model.ModelProvider;
import com.clubobsidian.dynamicgui.core.util.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NexoModelProvider implements ModelProvider {

    private static final boolean NEXO_EXISTS = ReflectionUtil.classExists("com.nexomc.nexo.api.NexoItems");
    private static final Method GET_ITEM_BUILDER = itemBuilder();
    private static final Method GET_ITEM_STACK = itemStack();

    private static Method itemBuilder() {
        if (NEXO_EXISTS) {
            try {
                Class<?> customStack = Class.forName("com.nexomc.nexo.api.NexoItems");
                Method getInstance = customStack.getDeclaredMethod("itemFromId", String.class);
                getInstance.setAccessible(true);
                return getInstance;
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static Method itemStack() {
        if (NEXO_EXISTS) {
            try {
                Class<?> customStack = Class.forName("com.nexomc.nexo.items.ItemBuilder");
                Method build = customStack.getDeclaredMethod("build");
                build.setAccessible(true);
                return build;
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String name() {
        return "nexo";
    }

    @Override
    public boolean applyModel(ItemStackWrapper<?> itemStack, String data) {
        if (NEXO_EXISTS) {
            try {
                Object itemBuilder = GET_ITEM_BUILDER.invoke(null, data);
                if (itemBuilder != null) {
                    ItemStackWrapper<?> wrappedCustom = ItemStackManager
                            .get()
                            .createItemStackWrapper(GET_ITEM_STACK.invoke(itemBuilder));
                    if (wrappedCustom.hasCustomModel()) {
                        return itemStack.setModel(wrappedCustom.getModelData());
                    } else {
                        DynamicGui.get().getLogger().error("Cannot find model data for %s", data);
                    }
                } else {
                    DynamicGui.get().getLogger().error("No model data found '%s' for provider '%s'",
                            data,
                            this.name()
                    );
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            DynamicGui.get().getLogger().error("Failed applying ItemsAdder model due to an outdated API");
        }
        return false;
    }
}
