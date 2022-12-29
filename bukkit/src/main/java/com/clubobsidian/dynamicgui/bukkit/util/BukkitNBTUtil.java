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

package com.clubobsidian.dynamicgui.bukkit.util;

import com.clubobsidian.dynamicgui.core.util.ReflectionUtil;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class BukkitNBTUtil {

    private static final String VERSION = VersionUtil.getVersion();
    private static final Class<?> NMS_ITEM_STACK_CLASS = ReflectionUtil.getClassIfExists(
            "net.minecraft.world.item.ItemStack",
            "net.minecraft.server." + VERSION + ".ItemStack"
    );
    private static final Class<?> CRAFT_ITEM_STACK_CLASS = ReflectionUtil.getClassIfExists(
            "org.bukkit.craftbukkit." + VERSION + ".inventory.CraftItemStack"
    );
    private static final Class<?> COMPOUND_CLASS = ReflectionUtil.getClassIfExists(
            "net.minecraft.nbt.NBTTagCompound",
            "net.minecraft.server." + VERSION + ".NBTTagCompound",
            "net.minecraft.nbt.CompoundTag"
    );
    private static final Class<?> PARSER_CLASS = ReflectionUtil.getClassIfExists(
            "net.minecraft.nbt.MojangsonParser",
            "net.minecraft.server." + VERSION + ".MojangsonParser",
            "net.minecraft.nbt.TagParser"
    );
    private static final Method AS_BUKKIT_COPY = ReflectionUtil.getStaticMethod(
            CRAFT_ITEM_STACK_CLASS,
            CRAFT_ITEM_STACK_CLASS,
            NMS_ITEM_STACK_CLASS
    );
    private static final Method AS_NMS_COPY = ReflectionUtil.getStaticMethod(
            CRAFT_ITEM_STACK_CLASS,
            NMS_ITEM_STACK_CLASS,
            ItemStack.class
    );
    private static final Method GET_COMPOUND_TAG = ReflectionUtil.getMethodByReturnType(
            NMS_ITEM_STACK_CLASS,
            COMPOUND_CLASS,
            new Class<?>[0]
    );


    private static final Method PARSE = ReflectionUtil.getStaticMethod(PARSER_CLASS, COMPOUND_CLASS);
    private static final Method SET_TAG = ReflectionUtil.getMethod(NMS_ITEM_STACK_CLASS, "setTag", "setTagClone");


    public static Object parse(String nbtStr) {
        try {
            return PARSE.invoke(null, nbtStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTag(ItemStack itemStack) {
        try {
            Object nmsItemStack = AS_NMS_COPY.invoke(null, itemStack);
            Object tag = GET_COMPOUND_TAG.invoke(nmsItemStack);
            if (tag == null) {
                return null;
            }
            return tag.toString();
        } catch (SecurityException | IllegalAccessException |
                 IllegalArgumentException | InvocationTargetException |
                 NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ItemStack setTag(ItemStack itemStack, String nbt) {
        try {
            Object nmsItemStack = AS_NMS_COPY.invoke(null, itemStack);
            Object nbtCompound = BukkitNBTUtil.parse(nbt);
            Object invokedSetTag = SET_TAG.invoke(nmsItemStack, nbtCompound);
            nmsItemStack = SET_TAG.getReturnType().equals(void.class) ? nmsItemStack : invokedSetTag;
            ItemStack bukkitItemStack = (ItemStack) AS_BUKKIT_COPY.invoke(null, nmsItemStack);
            return bukkitItemStack;
        } catch (SecurityException | IllegalAccessException |
                 IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BukkitNBTUtil() {
    }
}