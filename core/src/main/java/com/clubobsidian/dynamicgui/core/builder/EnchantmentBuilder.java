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
package com.clubobsidian.dynamicgui.core.builder;

import com.clubobsidian.dynamicgui.core.enchantment.EnchantmentWrapper;

public class EnchantmentBuilder {

    private String enchantment;
    private int level = 0;

    public EnchantmentBuilder setEnchantment(String enchantment) {
        this.enchantment = enchantment;
        return this;
    }

    public EnchantmentBuilder setLevel(int level) {
        this.level = level;
        return this;
    }

    public EnchantmentWrapper build() {
        return new EnchantmentWrapper(this.enchantment, level);
    }
}