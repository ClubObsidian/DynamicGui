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
package com.clubobsidian.dynamicgui.util.bukkit;

import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class BukkitNBTUtil {

    private static final String ITEM_STACK_CLASS_NAME = getItemStackClass();
    private static final String PARSER_CLASS_NAME = getParserClass();
    private static final String COMPOUND_CLASS_NAME = getCompoundClass();

    private static Method parse;
    private static Method asNMSCopy;
    private static Method setTag;
    private static Method asBukkitCopy;
    private static Method getTag;

    private BukkitNBTUtil() {
    }

    public static Object parse(String nbtStr) {
        if(parse == null) {
            try {
                Class<?> mojangParser = Class.forName(PARSER_CLASS_NAME);
                parse = mojangParser.getDeclaredMethod("parse", String.class);
                parse.setAccessible(true);
            } catch(ClassNotFoundException | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }
        try {
            return parse.invoke(null, nbtStr);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTag(ItemStack itemStack) {
        String version = VersionUtil.getVersion();
        try {
            if(asNMSCopy == null) {
                String craftItemStackClassName = "org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack";
                Class<?> craftItemStackClass;
                craftItemStackClass = Class.forName(craftItemStackClassName);
                asNMSCopy = craftItemStackClass.getDeclaredMethod("asNMSCopy", ItemStack.class);
                asNMSCopy.setAccessible(true);
            }

            if(getTag == null) {
                Class<?> nmsItemStackClass = Class.forName(ITEM_STACK_CLASS_NAME);
                getTag = nmsItemStackClass.getDeclaredMethod("getTag");
                getTag.setAccessible(true);
            }

            if(asBukkitCopy == null) {
                Class<?> nmsItemStackClass = Class.forName(ITEM_STACK_CLASS_NAME);
                String craftItemStackClassName = "org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack";
                Class<?> craftItemStackClass = Class.forName(craftItemStackClassName);
                asBukkitCopy = craftItemStackClass.getDeclaredMethod("asBukkitCopy", nmsItemStackClass);
                asBukkitCopy.setAccessible(true);
            }

            Object nmsItemStack = asNMSCopy.invoke(null, itemStack);
            Object tag = getTag.invoke(nmsItemStack);
            if(tag == null) {
                return null;
            }

            return tag.toString();
        } catch(ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ItemStack setTag(ItemStack itemStack, String nbt) {
        String version = VersionUtil.getVersion();
        try {
            if(asNMSCopy == null) {
                String craftItemStackClassName = "org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack";
                Class<?> craftItemStackClass = Class.forName(craftItemStackClassName);
                asNMSCopy = craftItemStackClass.getDeclaredMethod("asNMSCopy", ItemStack.class);
                asNMSCopy.setAccessible(true);
            }

            if(setTag == null) {
                Class<?> nmsItemStackClass = Class.forName(ITEM_STACK_CLASS_NAME);
                Class<?> nbtTagCompoundClass = Class.forName(COMPOUND_CLASS_NAME);
                setTag = nmsItemStackClass.getDeclaredMethod("setTag", nbtTagCompoundClass);
                setTag.setAccessible(true);
            }

            if(asBukkitCopy == null) {
                Class<?> nmsItemStackClass = Class.forName(ITEM_STACK_CLASS_NAME);
                String craftItemStackClassName = "org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack";
                Class<?> craftItemStackClass = Class.forName(craftItemStackClassName);
                asBukkitCopy = craftItemStackClass.getDeclaredMethod("asBukkitCopy", nmsItemStackClass);
                asBukkitCopy.setAccessible(true);
            }

            Object nmsItemStack = asNMSCopy.invoke(null, itemStack);
            Object nbtCompound = BukkitNBTUtil.parse(nbt);
            setTag.invoke(nmsItemStack, nbtCompound);
            ItemStack bukkitItemStack = (ItemStack) asBukkitCopy.invoke(null, nmsItemStack);
            return bukkitItemStack;
        } catch(ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getItemStackClass() {
        try {
            String className = "net.minecraft.world.item.ItemStack";
            Class.forName(className);
            return className;
        } catch(ClassNotFoundException ex) {
            String version = VersionUtil.getVersion();
            String className = "net.minecraft.server." + version + ".ItemStack";
            try {
                Class.forName(className);
                return className;
            } catch(ClassNotFoundException e) {
                return null;
            }
        }
    }

    private static String getParserClass() {
        try {
            String className = "net.minecraft.nbt.MojangsonParser";
            Class.forName(className);
            return className;
        } catch(ClassNotFoundException ex) {
            String version = VersionUtil.getVersion();
            String className = "net.minecraft.server." + version + ".MojangsonParser";
            try {
                Class.forName(className);
                return className;
            } catch(ClassNotFoundException e) {
                return null;
            }
        }
    }

    private static String getCompoundClass() {
        try {
            String className = "net.minecraft.nbt.NBTTagCompound";
            Class.forName(className);
            return className;
        } catch(ClassNotFoundException ex) {
            String version = VersionUtil.getVersion();
            String className = "net.minecraft.server." + version + ".NBTTagCompound";
            try {
                Class.forName(className);
                return className;
            } catch(ClassNotFoundException e) {
                return null;
            }
        }
    }
}