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

package com.clubobsidian.dynamicgui.core.economy;

import com.clubobsidian.dynamicgui.api.economy.Economy;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class NoOpEconomy implements Economy {

    @Override
    public boolean setup() {
        return false;
    }

    @Override
    public @NotNull BigDecimal getBalance(@NotNull PlayerWrapper<?> playerWrapper) {
        return new BigDecimal(-1);
    }

    @Override
    public boolean withdraw(@NotNull PlayerWrapper<?> playerWrapper, @NotNull BigDecimal amt) {
        return false;
    }

    @Override
    public boolean deposit(@NotNull PlayerWrapper<?> playerWrapper, @NotNull BigDecimal amt) {
        return false;
    }
}
