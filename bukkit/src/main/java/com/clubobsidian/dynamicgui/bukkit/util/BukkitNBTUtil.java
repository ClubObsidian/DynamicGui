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
    private static final Class<?> COMPOUND_CLASS = ReflectionUtil.getClassIfExists(
            "net.minecraft.nbt.NBTTagCompound",
            "net.minecraft.server." + VERSION + ".NBTTagCompound"
    );
    private static final Class<?> PARSER_CLASS = ReflectionUtil.getClassIfExists(
            "net.minecraft.nbt.MojangsonParser",
            "net.minecraft.server." + VERSION + ".MojangsonParser"
    );

    private static Method parse;
    private static Method asNMSCopy;
    private static Method setTag;
    private static Method asBukkitCopy;
    private static Method getTag;

    public static Object parse(String nbtStr) {
        if (parse == null) {
            try {
                parse = ReflectionUtil.getStaticMethod(PARSER_CLASS, COMPOUND_CLASS);
                parse.setAccessible(true);
            } catch (NullPointerException | SecurityException e) {
                e.printStackTrace();
            }
        }
        try {
            return parse.invoke(null, nbtStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTag(ItemStack itemStack) {
        String version = VersionUtil.getVersion();
        try {
            if (asNMSCopy == null) {
                String craftItemStackClassName = "org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack";
                Class<?> craftItemStackClass;
                craftItemStackClass = Class.forName(craftItemStackClassName);
                asNMSCopy = craftItemStackClass.getDeclaredMethod("asNMSCopy", ItemStack.class);
                asNMSCopy.setAccessible(true);
            }

            if (getTag == null) {
                getTag = NMS_ITEM_STACK_CLASS.getDeclaredMethod("getTag");
                getTag.setAccessible(true);
            }

            if (asBukkitCopy == null) {
                String craftItemStackClassName = "org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack";
                Class<?> craftItemStackClass = Class.forName(craftItemStackClassName);
                asBukkitCopy = craftItemStackClass.getDeclaredMethod("asBukkitCopy", NMS_ITEM_STACK_CLASS);
                asBukkitCopy.setAccessible(true);
            }

            Object nmsItemStack = asNMSCopy.invoke(null, itemStack);
            Object tag = getTag.invoke(nmsItemStack);
            if (tag == null) {
                return null;
            }

            return tag.toString();
        } catch (ClassNotFoundException | NoSuchMethodException |
                SecurityException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException |
                NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ItemStack setTag(ItemStack itemStack, String nbt) {
        String version = VersionUtil.getVersion();
        try {
            if (asNMSCopy == null) {
                String craftItemStackClassName = "org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack";
                Class<?> craftItemStackClass = Class.forName(craftItemStackClassName);
                asNMSCopy = craftItemStackClass.getDeclaredMethod("asNMSCopy", ItemStack.class);
                asNMSCopy.setAccessible(true);
            }

            if (setTag == null) {
                getSetTagMethod();
            }

            if (asBukkitCopy == null) {
                String craftItemStackClassName = "org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack";
                Class<?> craftItemStackClass = Class.forName(craftItemStackClassName);
                asBukkitCopy = craftItemStackClass.getDeclaredMethod("asBukkitCopy", NMS_ITEM_STACK_CLASS);
                asBukkitCopy.setAccessible(true);
            }

            Object nmsItemStack = asNMSCopy.invoke(null, itemStack);
            Object nbtCompound = BukkitNBTUtil.parse(nbt);
            Object invokedSetTag = setTag.invoke(nmsItemStack, nbtCompound);
            nmsItemStack = setTag.getReturnType().equals(void.class) ? nmsItemStack : invokedSetTag;
            ItemStack bukkitItemStack = (ItemStack) asBukkitCopy.invoke(null, nmsItemStack);
            return bukkitItemStack;
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void getSetTagMethod() {
        try {
            setTag = ReflectionUtil.getMethod(NMS_ITEM_STACK_CLASS, "setTag", "setTagClone");
            setTag.setAccessible(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private BukkitNBTUtil() {
    }
}