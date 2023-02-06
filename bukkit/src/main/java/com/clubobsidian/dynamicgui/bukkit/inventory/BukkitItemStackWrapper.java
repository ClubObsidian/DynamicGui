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

package com.clubobsidian.dynamicgui.bukkit.inventory;

import com.clubobsidian.dynamicgui.api.enchantment.EnchantmentWrapper;
import com.clubobsidian.dynamicgui.api.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.api.manager.material.MaterialManager;
import com.clubobsidian.dynamicgui.bukkit.util.BukkitNBTUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class BukkitItemStackWrapper<T extends ItemStack> extends ItemStackWrapper<T> {

    /**
     *
     */
    private static final long serialVersionUID = 3542885060265738780L;

    private static final Method SET_CUSTOM_MODEL_DATA = setCustomModelData();
    private static final Method HAS_CUSTOM_MODEL_DATA = hasCustomModelData();
    private static final Method GET_CUSTOM_MODEL_DATA = getCustomModelData();

    private static Method setCustomModelData() {
        try {
            Method set = ItemMeta.class.getDeclaredMethod("setCustomModelData", Integer.class);
            set.setAccessible(true);
            return set;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private static Method hasCustomModelData() {
        try {
            Method has = ItemMeta.class.getDeclaredMethod("hasCustomModelData");
            has.setAccessible(true);
            return has;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private static Method getCustomModelData() {
        try {
            Method get = ItemMeta.class.getDeclaredMethod("getCustomModelData");
            get.setAccessible(true);
            return get;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public BukkitItemStackWrapper(T itemStack) {
        super(itemStack);
    }

    @Override
    public int getAmount() {
        return this.getItemStack().getAmount();
    }

    @Override
    public void setAmount(int amount) {
        this.getItemStack().setAmount(amount);
    }

    @Override
    public int getMaxStackSize() {
        return this.getItemStack().getMaxStackSize();
    }

    @Override
    public String getType() {
        return this.getItemStack().getType().toString();
    }

    @Override
    public boolean setType(final String type) {
        if (type == null) {
            return false;
        }

        String normalizedType = MaterialManager.get().normalizeMaterial(type);
        Material mat;

        try {
            mat = Material.valueOf(normalizedType);
        } catch (Exception ex) {
            return false;
        }

        this.getItemStack().setType(mat);
        return true;
    }

    @Override
    public @Nullable String getName() {
        ItemMeta itemMeta = this.getItemStack().getItemMeta();
        if (itemMeta.hasDisplayName()) {
            return itemMeta.getDisplayName();
        }
        return null;
    }

    @Override
    public void setName(String name) {
        ItemMeta itemMeta = this.getItemStack().getItemMeta();
        itemMeta.setDisplayName(name);
        this.getItemStack().setItemMeta(itemMeta);
    }

    @Override
    public List<String> getLore() {
        ItemMeta itemMeta = this.getItemStack().getItemMeta();
        if (!itemMeta.hasLore()) {
            return new ArrayList<>();
        }

        return itemMeta.getLore();
    }

    @Override
    public void setLore(List<String> lore) {
        ItemMeta itemMeta = this.getItemStack().getItemMeta();
        itemMeta.setLore(lore);
        this.getItemStack().setItemMeta(itemMeta);
    }

    @Override
    public short getDurability() {
        return this.getItemStack().getDurability();
    }

    @Override
    public void setDurability(short durability) {
        this.getItemStack().setDurability(durability);
    }

    @Override
    public void addEnchant(EnchantmentWrapper enchant) {
        ItemMeta itemMeta = this.getItemStack().getItemMeta();
        itemMeta.addEnchant(Enchantment.getByName(enchant.getEnchant()), enchant.getLevel(), true);
        this.getItemStack().setItemMeta(itemMeta);
    }

    @Override
    public void removeEnchant(EnchantmentWrapper enchant) {
        ItemMeta itemMeta = this.getItemStack().getItemMeta();
        itemMeta.removeEnchant(Enchantment.getByName(enchant.getEnchant()));
        this.getItemStack().setItemMeta(itemMeta);
    }

    @Override
    public List<EnchantmentWrapper> getEnchants() {
        List<EnchantmentWrapper> enchants = new ArrayList<>();
        ItemMeta itemMeta = this.getItemStack().getItemMeta();
        if (itemMeta.hasEnchants()) {
            Iterator<Entry<Enchantment, Integer>> it = itemMeta.getEnchants().entrySet().iterator();
            while (it.hasNext()) {
                Entry<Enchantment, Integer> next = it.next();
                enchants.add(new EnchantmentWrapper(next.getKey().getName(), next.getValue()));
            }
        }
        return enchants;
    }

    @Override
    public String getNBT() {
        return BukkitNBTUtil.getTag(this.getItemStack());
    }

    @Override
    public void setNBT(String nbt) {
        ItemStack oldItemStack = this.getItemStack();
        ItemStack newItemStack = BukkitNBTUtil.setTag(this.getItemStack(), nbt);

        if (oldItemStack.hasItemMeta()) {
            ItemMeta meta = oldItemStack.getItemMeta();
            ItemMeta newMeta = newItemStack.getItemMeta();
            if (meta.hasDisplayName()) {
                newMeta.setDisplayName(meta.getDisplayName());
            }

            if (meta.hasEnchants()) {
                Iterator<Entry<Enchantment, Integer>> it = meta.getEnchants().entrySet().iterator();
                while (it.hasNext()) {
                    Entry<Enchantment, Integer> next = it.next();
                    newMeta.addEnchant(next.getKey(), next.getValue(), true);
                }
            }
            if (meta.hasLore()) {
                newMeta.setLore(meta.getLore());
            }

            for (ItemFlag flag : meta.getItemFlags()) {
                newMeta.addItemFlags(flag);
            }

            newItemStack.setItemMeta(newMeta);
        }

        this.setItemStack(newItemStack);
    }

    @Override
    public void setGlowing(boolean glowing) {
        ItemStack item = this.getItemStack();
        ItemMeta meta = item.getItemMeta();
        if (glowing) {
            meta.addEnchant(Enchantment.DIG_SPEED, -1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.removeEnchant(Enchantment.DIG_SPEED);
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);

        }
        item.setItemMeta(meta);
    }

    @Override
    public @Unmodifiable List<String> getItemFlags() {
        List<String> flags = new ArrayList<>();
        ItemStack item = this.getItemStack();
        ItemMeta meta = item.getItemMeta();
        for (ItemFlag flag : meta.getItemFlags()) {
            flags.add(flag.name());
        }
        return Collections.unmodifiableList(flags);
    }

    @Override
    public void addItemFlags(List<String> itemFlags) {
        ItemStack item = this.getItemStack();
        ItemMeta meta = item.getItemMeta();
        for (String itemFlag : itemFlags) {
            ItemFlag flag = ItemFlag.valueOf(itemFlag);
            if (flag != null) {
                meta.addItemFlags(flag);
            }
        }
        item.setItemMeta(meta);
    }

    @Override
    public boolean isSimilar(ItemStackWrapper<?> compareTo) {
        return this.getItemStack().isSimilar((ItemStack) compareTo.getItemStack());
    }

    @Override
    public boolean setModel(int data) {
        if (SET_CUSTOM_MODEL_DATA == null) {
            return false;
        }
        ItemMeta meta = getItemStack().getItemMeta();
        try {
            SET_CUSTOM_MODEL_DATA.invoke(meta, data);
            this.getItemStack().setItemMeta(meta);
            return true;
        } catch (IllegalAccessException | InvocationTargetException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean hasCustomModel() {
        if (HAS_CUSTOM_MODEL_DATA == null) {
            return false;
        }
        ItemMeta meta = getItemStack().getItemMeta();
        try {
            return (boolean) HAS_CUSTOM_MODEL_DATA.invoke(meta);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isAir() {
        return this.itemStack == null || this.itemStack.getType() == Material.AIR;
    }

    @Override
    public int getModelData() {
        if (GET_CUSTOM_MODEL_DATA == null) {
            return -1;
        }
        ItemMeta meta = getItemStack().getItemMeta();
        try {
            return (int) GET_CUSTOM_MODEL_DATA.invoke(meta);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }
}