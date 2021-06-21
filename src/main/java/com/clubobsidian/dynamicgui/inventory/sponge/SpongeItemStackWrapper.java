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
package com.clubobsidian.dynamicgui.inventory.sponge;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.enchantment.EnchantmentWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.manager.material.MaterialManager;
import com.clubobsidian.dynamicgui.util.ReflectionUtil;
import com.clubobsidian.dynamicgui.util.sponge.SpongeNBTUtil;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentType;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpongeItemStackWrapper<T extends ItemStack> extends ItemStackWrapper<T> {

    /**
     *
     */
    private static final long serialVersionUID = 4740479272146276094L;

    public SpongeItemStackWrapper(T itemStack) {
        super(itemStack);
    }

    @Override
    public String getType() {
        return this.getItemStack().getType().getId(); //TODO - Translate to enum like in bukkit
    }

    @Override
    public boolean setType(final String type) {
        if(type == null) {
            return false;
        }

        String normalizedType = MaterialManager.get().normalizeMaterial(type);

        Field typeField = ReflectionUtil.getFieldByName(ItemTypes.class, normalizedType);
        if(typeField != null) {
            ItemType itemStackType = new ReflectionUtil.ReflectionHelper<ItemType>().get(typeField);
            if(itemStackType == null) {
                return false;
            }

            ItemStack itemStack = ItemStack.builder()
                    .itemType(itemStackType)
                    .from(this.getItemStack())
                    .itemType(itemStackType)
                    .build();
            this.setItemStack(itemStack);
            return true;
        }

        return false;
    }

    @Override
    public int getAmount() {
        return this.getItemStack().getQuantity();
    }

    @Override
    public void setAmount(int amount) {
        this.getItemStack().setQuantity(amount);
    }

    @Override
    public int getMaxStackSize() {
        return this.getItemStack().getMaxStackQuantity();
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getName() {
        Optional<Text> name = this.getItemStack().get(Keys.DISPLAY_NAME);
        if(name.isPresent()) {
            return TextSerializers.LEGACY_FORMATTING_CODE.serialize(name.get());
        }
        return null;
    }

    @Override
    public void setName(String name) {
        this.getItemStack().offer(Keys.DISPLAY_NAME, Text.of(name));
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        Optional<List<Text>> itemLore = this.getItemStack().get(Keys.ITEM_LORE);
        if(itemLore.isPresent()) {
            for(Text text : itemLore.get()) {
                lore.add(TextSerializers.LEGACY_FORMATTING_CODE.serialize(text));
            }
        }
        return lore;
    }

    @Override
    public void setLore(List<String> lore) {
        List<Text> textLore = new ArrayList<>();
        for(String l : lore) {
            textLore.add(Text.of(l));
        }
        this.getItemStack().offer(Keys.ITEM_LORE, textLore);
    }

    @Override
    public short getDurability() {
        Optional<Integer> itemDurability = this.getItemStack().get(Keys.ITEM_DURABILITY);
        if(itemDurability.isPresent()) {
            int dura = itemDurability.get();
            if(dura <= Short.MAX_VALUE) {
                return (short) dura;
            }
        }
        return 0;
    }

    @Override
    public void setDurability(short durability) {
        DynamicGui.get().getLogger().info("ItemStack is null: " + (this.getItemStack() == null));
        DataContainer container = this.getItemStack().toContainer();
        container = container.set(DataQuery.of("UnsafeDamage"), (int) durability);
        ItemStack newStack = ItemStack.builder().fromContainer(container).build();
        this.setItemStack(newStack);
    }

    @Override
    public void addEnchant(EnchantmentWrapper enchant) {
        List<Enchantment> enchants = new ArrayList<>();
        Optional<List<Enchantment>> itemEnchants = this.getItemStack().get(Keys.ITEM_ENCHANTMENTS);
        if(itemEnchants.isPresent()) {
            enchants = itemEnchants.get();
        }
        Field enchantmentField = ReflectionUtil.getFieldByName(EnchantmentTypes.class, enchant.getEnchant().toUpperCase());
        if(enchantmentField == null)
            return;

        EnchantmentType enchantmentType = new ReflectionUtil.ReflectionHelper<EnchantmentType>().get(enchantmentField);
        if(enchantmentType != null) {
            enchants.add(Enchantment.builder().type(enchantmentType).level(enchant.getLevel()).build());
        }

        if(enchants.size() > 0) {
            this.getItemStack().offer(Keys.ITEM_ENCHANTMENTS, enchants);
        }
    }

    @Override
    public void removeEnchant(EnchantmentWrapper enchant) {
        List<Enchantment> enchants = new ArrayList<>();
        Optional<List<Enchantment>> itemEnchants = this.getItemStack().get(Keys.ITEM_ENCHANTMENTS);
        if(itemEnchants.isPresent()) {
            enchants = itemEnchants.get();
        }
        Optional<EnchantmentType> enchantmentType = Sponge.getGame().getRegistry().getType(EnchantmentType.class, enchant.getEnchant());
        if(enchantmentType.isPresent()) {
            for(Enchantment ench : enchants) {
                if(ench.getType().equals(enchantmentType.get())) {
                    enchants.remove(ench);
                    break;
                }
            }
        }
        this.getItemStack().offer(Keys.ITEM_ENCHANTMENTS, enchants);
    }

    @Override
    public List<EnchantmentWrapper> getEnchants() {
        List<EnchantmentWrapper> enchants = new ArrayList<>();
        Optional<List<Enchantment>> itemEnchants = this.getItemStack().get(Keys.ITEM_ENCHANTMENTS);
        if(itemEnchants.isPresent()) {
            for(Enchantment ench : itemEnchants.get()) {
                enchants.add(new EnchantmentWrapper(ench.getType().getId(), ench.getLevel()));
            }
        }
        return enchants;
    }

    @Override
    public String getNBT() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setNBT(String nbt) {
        String name = this.getName();
        List<String> lore = this.getLore();
        List<EnchantmentWrapper> enchants = this.getEnchants();

        SpongeNBTUtil.setTag(this.getItemStack(), nbt);

        if(name != null) {
            this.setName(name);
        }

        this.setLore(lore);
        for(EnchantmentWrapper ench : enchants) {
            this.addEnchant(ench);
        }
    }

    @Override
    public void setGlowing(boolean glowing) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addItemFlags(List<String> ItemFlag) {
        // TODO
    }

    @Override
    public boolean isSimilar(ItemStackWrapper<?> compareTo) {
        // TODO Auto-generated method stub
        return false;
    }
}