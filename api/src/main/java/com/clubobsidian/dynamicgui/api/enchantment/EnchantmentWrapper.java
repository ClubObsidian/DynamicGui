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

package com.clubobsidian.dynamicgui.api.enchantment;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class EnchantmentWrapper implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1056076873542226033L;

    @ApiStatus.Internal
    public static final String TEST_ENCHANT_1 = "enchant_1";
    @ApiStatus.Internal
    public static final String TEST_ENCHANT_2 = "enchant_2";
    @ApiStatus.Internal
    public static final String TEST_ENCHANT_3 = "enchant_3";

    private final String enchant;
    private final int level;

    public EnchantmentWrapper(@NotNull String enchant, int level) {
        this.enchant = Objects.requireNonNull(enchant);
        this.level = level;
    }

    /**
     * Gets the enchantment
     * @return String enchantment
     */
    public String getEnchant() {
        return this.enchant;
    }

    /**
     * Gets the level of the enchantment
     * @return int level of the enchantment
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Static builder for the enchantment wrapper
     */
    public static class Builder {

        private transient String enchantment;
        private transient int level = 0;

        public Builder setEnchantment(@NotNull String enchantment) {
            this.enchantment = Objects.requireNonNull(enchantment);
            return this;
        }

        public Builder setLevel(int level) {
            this.level = level;
            return this;
        }

        public EnchantmentWrapper build() {
            return new EnchantmentWrapper(this.enchantment, level);
        }
    }
}