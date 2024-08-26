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

package com.clubobsidian.dynamicgui.api.inventory;

import com.clubobsidian.dynamicgui.api.enchantment.EnchantmentWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class ItemStackWrapper<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7902733103453967016L;

    @Nullable
    protected T itemStack;

    public ItemStackWrapper(@Nullable T itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Gets the underlying native item stack for this wrapper.
     *
     * @return the native item stack
     */
    @Nullable
    public T getItemStack() {
        return this.itemStack;
    }

    /**
     * Sets the underlying item stack for this wrapper.
     *
     * @param itemStack the native item stack to set
     */
    @SuppressWarnings("unchecked")
    protected void setItemStack(@Nullable Object itemStack) {
        this.itemStack = (T) itemStack;
    }

    /**
     * Gets the amount of items in the underlying item stack
     *
     * @return amount of items
     */
    public abstract int getAmount();

    /**
     * Sets the amount of items in the underlying item stack
     *
     * @param amount the amount to set
     */
    public abstract void setAmount(int amount);

    /**
     * Gets the max stack size, for example cobblestone is 64
     * but enderpearls are 16.
     *
     * @return the item stack max size
     */
    public abstract int getMaxStackSize();

    /**
     * Gets the type of the underlying item stack
     * as a string. This is platform independent,
     * data will be different depending on the platform.
     *
     * @return the type as a string
     */
    public abstract String getType();

    /**
     * Sets the type of the underlying item stack.
     * Fails if the material is null or an invalid.
     *
     * @param type the type to set
     * @return if setting the type was successful
     */
    public abstract boolean setType(String type);

    /**
     * Gets the custom name of the underlying item stack.
     *
     * @return the custom name or null
     */
    @Nullable
    public abstract String getName();

    /**
     * Sets the custom name of the underlying item stack.
     *
     * @param name the name to set
     */
    public abstract void setName(String name);

    /**
     * Gets the lore of the underlying item stack.
     *
     * @return the lore
     */
    public abstract List<String> getLore();

    /**
     * Sets the lore of the underlying item stack.
     *
     * @param lore the lore to set
     */
    public abstract void setLore(List<String> lore);

    /**
     * Gets the current durability of the underlying item stack.
     *
     * @return the durability as a short
     */
    public abstract short getDurability();

    /**
     * Sets the current durability of the underlying item stack
     *
     * @param durability the durability to set
     */
    public abstract void setDurability(short durability);

    /**
     * Gets the current enchantments of the underlying item stack
     * as a list of enchantment wrappers.
     *
     * @return list of enchantment wrappers
     */
    public abstract List<EnchantmentWrapper> getEnchants();

    /**
     * Adds an enchantment to the underlying item stack.
     *
     * @param enchant the enchantment to add
     */
    public abstract void addEnchant(@NotNull EnchantmentWrapper enchant);

    /**
     * Removes an enchantment from the underlying item stack.
     *
     * @param enchant the enchantment to remove
     */
    public abstract void removeEnchant(@NotNull EnchantmentWrapper enchant);

    /**
     * Gets the NBT of the underlying item stack.
     *
     * @return the nbt as a string
     */
    public abstract String getNBT();

    /**
     * Sets the NBT for the underlying item stack.
     *
     * @param nbt the nbt to set
     */
    public abstract void setNBT(String nbt);

    /**
     * Gets a map of the components
     * from the underlying item stack
     * the items are serialized and
     * returns as a map of strings.
     *
     * @return map of strings
     */
    public abstract Map<String, String> getDataComponents();

    /**
     * Sets the data components underlying item stack.
     *
     * @param components the components to set
     */
    public abstract void setDataComponents(Map<String, String> components);

    /**
     * Sets whether the underlying item stack should glow.
     *
     * @param glowing whether the item stack should glow
     */
    public abstract void setGlowing(boolean glowing);

    /**
     * Gets the item flags of the underlying item stack as a list
     * of strings.
     *
     * @return the item flags as a list of string
     */
    @Unmodifiable
    public abstract List<String> getItemFlags();

    /**
     * Adds a list of item flags to the underlying item stack.
     *
     * @param itemFlags the item flags to add from a list of strings
     */
    public abstract void addItemFlags(List<String> itemFlags);

    /**
     * Compares whether the underlying item of two item stacks
     * is similar.
     *
     * @param compareTo the item wrapper to compare
     * @return whether the items are similar
     */
    public abstract boolean isSimilar(ItemStackWrapper<?> compareTo);

    /**
     * Gets the model data for the underlying item stack.
     *
     * @return the model data if any
     */
    public abstract int getModelData();

    /**
     * Sets the model data for the underlying item stack.
     *
     * @param data the data to set
     * @return if the action was successful
     */
    public abstract boolean setModel(int data);

    /**
     * Gets whether the underlying item stack has a custom model
     *
     * @return if there is a custom model
     */
    public abstract boolean hasCustomModel();

    /**
     * Checks if the item stack is air
     * <p>
     * return if the stack is air
     */
    public abstract boolean isAir();

}