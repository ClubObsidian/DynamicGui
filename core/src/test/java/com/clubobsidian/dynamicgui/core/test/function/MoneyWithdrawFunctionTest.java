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

package com.clubobsidian.dynamicgui.core.test.function;

import com.clubobsidian.dynamicgui.core.economy.NoOpEconomy;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.function.Function;
import com.clubobsidian.dynamicgui.core.permission.NoOpPermission;
import com.clubobsidian.dynamicgui.core.function.MoneyWithdrawFunction;
import com.clubobsidian.dynamicgui.core.test.mock.plugin.MockEconomy;
import com.clubobsidian.dynamicgui.core.test.mock.test.FactoryTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoneyWithdrawFunctionTest extends FactoryTest {

    @Test
    public void testBelowBalance() throws Exception {
        this.getFactory().inject();
        PlayerWrapper<?> player = this.getFactory().createPlayer();
        Function function = new MoneyWithdrawFunction();
        function.setData("10");
        assertFalse(function.function(player));
    }

    @Test
    public void testInvalidData() throws Exception {
        this.getFactory().inject();
        PlayerWrapper<?> player = this.getFactory().createPlayer();
        Function function = new MoneyWithdrawFunction();
        function.setData("a");
        assertFalse(function.function(player));
    }

    @Test
    public void testNoOpEconomy() throws Exception {
        this.getFactory().inject(new NoOpEconomy(), new NoOpPermission());
        PlayerWrapper<?> player = this.getFactory().createPlayer();
        Function function = new MoneyWithdrawFunction();
        function.setData("10");
        assertFalse(function.function(player));
    }

    @Test
    public void testEqualBalance() throws Exception {
        PlayerWrapper<?> player = this.getFactory().createPlayer();
        MockEconomy economy = this.getFactory().inject().getEconomy();
        BigDecimal amount = new BigDecimal(1);
        economy.deposit(player, amount);
        Function function = new MoneyWithdrawFunction();
        function.setData("" + amount.intValue());
        assertTrue(function.function(player));
    }

    @Test
    public void testAboveBalance() throws Exception {
        PlayerWrapper<?> player = this.getFactory().createPlayer();
        MockEconomy economy = this.getFactory().inject().getEconomy();
        int withdrawAmount = 9;
        BigDecimal depositAmount = new BigDecimal(withdrawAmount + 1);
        economy.deposit(player, depositAmount);
        Function function = new MoneyWithdrawFunction();
        function.setData("" + withdrawAmount);
        assertTrue(function.function(player));
    }
}
