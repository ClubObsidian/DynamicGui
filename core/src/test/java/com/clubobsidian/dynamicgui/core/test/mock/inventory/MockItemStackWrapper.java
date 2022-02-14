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
import com.clubobsidian.dynamicgui.core.inventory.ItemStackWrapper;

import java.util.List;

public abstract class MockItemStackWrapper extends ItemStackWrapper<MockItemStack> {

    public MockItemStackWrapper(MockItemStack itemStack) {
        super(itemStack);
    }

    @Override
    public String getType() {
        return this.getItemStack().getType();
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
    public short getDurability() {
        return this.getItemStack().getDurability();
    }

    @Override
    public void setDurability(short durability) {
        this.getItemStack().setDurability(durability);
    }

    @Override
    public List<EnchantmentWrapper> getEnchants() {
        return this.getItemStack().getEnchants();
    }

    @Override
    public void addEnchant(EnchantmentWrapper enchant) {
        this.getItemStack().addEnchant(enchant);
    }

    @Override
    public void removeEnchant(EnchantmentWrapper enchant) {
        this.getItemStack().removeEnchant(enchant);
    }

    @Override
    public void setGlowing(boolean glowing) {
        this.getItemStack().setGlowing(glowing);
    }

    @Override
    public boolean isGlowing() {
        return this.getItemStack().isGlowing();
    }
}
