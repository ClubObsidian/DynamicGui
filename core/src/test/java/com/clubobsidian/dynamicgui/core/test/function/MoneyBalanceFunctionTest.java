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

package com.clubobsidian.dynamicgui.core.test.function;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.function.impl.MoneyBalanceFunction;
import com.clubobsidian.dynamicgui.core.test.mock.MockFactory;
import com.clubobsidian.dynamicgui.core.test.mock.plugin.MockEconomy;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MoneyBalanceFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void testBelowBalance() {
        this.factory.inject();
        PlayerWrapper<?> player = this.factory.createPlayer();
        Function function = new MoneyBalanceFunction();
        function.setData("10");
        assertFalse(function.function(player));
    }

    @Test
    public void testInvalidData() {
        this.factory.inject();
        PlayerWrapper<?> player = this.factory.createPlayer();
        Function function = new MoneyBalanceFunction();
        function.setData("a");
        assertFalse(function.function(player));
    }

    @Test
    public void testNullEconomy() {
        this.factory.inject().getPlugin().economy = null;
        PlayerWrapper<?> player = this.factory.createPlayer();
        Function function = new MoneyBalanceFunction();
        function.setData("10");
        assertFalse(function.function(player));
    }

    @Test
    public void testEqualBalance() {
        PlayerWrapper<?> player = this.factory.createPlayer();
        MockEconomy economy = this.factory.inject().getEconomy();
        BigDecimal amount = new BigDecimal(1);
        economy.deposit(player, amount);
        Function function = new MoneyBalanceFunction();
        function.setData("" + amount.intValue());
        assertTrue(function.function(player));
    }

    @Test
    public void testAboveBalance() {
        PlayerWrapper<?> player = this.factory.createPlayer();
        MockEconomy economy = this.factory.inject().getEconomy();
        int checkAmount = 9;
        BigDecimal depositAmount = new BigDecimal(checkAmount + 1);
        economy.deposit(player, depositAmount);
        Function function = new MoneyBalanceFunction();
        function.setData("" + checkAmount);
        assertTrue(function.function(player));
    }
}
