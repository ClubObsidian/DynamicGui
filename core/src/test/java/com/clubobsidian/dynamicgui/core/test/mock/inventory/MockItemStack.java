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

package com.clubobsidian.dynamicgui.core.test.mock.inventory;

import com.clubobsidian.dynamicgui.core.enchantment.EnchantmentWrapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MockItemStack {

    private final String type;
    private int amount;
    private short durability = 0;
    private final Map<String, EnchantmentWrapper> enchants = new LinkedHashMap<>();
    private boolean glowing = false;
    private List<String> lore = new ArrayList<>();

    public MockItemStack(String type) {
        this(type, 1);
    }

    public MockItemStack(String type, int quantity) {
        this.type = type;
        this.amount = quantity;
    }

    public String getType() {
        return this.type;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public short getDurability() {
        return this.durability;
    }

    public void setDurability(short durability) {
        this.durability = durability;
    }

    public List<EnchantmentWrapper> getEnchants() {
        return new ArrayList<>(this.enchants.values());
    }

    public void addEnchant(EnchantmentWrapper enchant) {
        this.enchants.put(enchant.getEnchant(), enchant);
    }

    public void removeEnchant(EnchantmentWrapper enchant) {
        this.enchants.remove(enchant.getEnchant());
    }

    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public void setLore(List<String> lore) {
        List<String> l = lore == null ? new ArrayList<>() : lore;
        this.lore = l;
    }
}