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

package com.clubobsidian.dynamicgui.api.enchantment;

import java.io.Serializable;

public class EnchantmentWrapper implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1056076873542226033L;

    public static final String TEST_ENCHANT_1 = "enchant_1";
    public static final String TEST_ENCHANT_2 = "enchant_2";
    public static final String TEST_ENCHANT_3 = "enchant_3";

    private final String enchant;
    private final int level;

    public EnchantmentWrapper(String enchant, int level) {
        this.enchant = enchant;
        this.level = level;
    }

    public String getEnchant() {
        return this.enchant;
    }

    public int getLevel() {
        return this.level;
    }
}