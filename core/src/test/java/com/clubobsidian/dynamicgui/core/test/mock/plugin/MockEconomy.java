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

package com.clubobsidian.dynamicgui.core.test.mock.plugin;

import com.clubobsidian.dynamicgui.core.economy.Economy;
import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MockEconomy implements Economy {

    private final Map<PlayerWrapper<?>, BigDecimal> balances = new HashMap<>();

    @Override
    public boolean setup() {
        return true;
    }

    @Override
    public BigDecimal getBalance(PlayerWrapper<?> playerWrapper) {
        return this.balances.getOrDefault(playerWrapper, new BigDecimal(0));
    }

    @Override
    public boolean withdraw(PlayerWrapper<?> playerWrapper, BigDecimal amt) {
        BigDecimal balance = this.getBalance(playerWrapper);
        if(balance.intValue() >= amt.intValue()) {
            balance = balance.subtract(amt);
            this.balances.put(playerWrapper, balance);
            return true;
        }
        return false;
    }

    @Override
    public boolean deposit(PlayerWrapper<?> playerWrapper, BigDecimal amt) {
        BigDecimal balance = this.getBalance(playerWrapper);
        balance = balance.add(amt);
        this.balances.put(playerWrapper, balance);
        return true;
    }

    public Map<PlayerWrapper<?>, BigDecimal> getBalances() {
        return this.balances;
    }
}
