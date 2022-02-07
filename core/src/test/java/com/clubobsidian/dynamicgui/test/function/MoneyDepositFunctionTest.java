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

package com.clubobsidian.dynamicgui.test.function;

import com.clubobsidian.dynamicgui.core.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;
import com.clubobsidian.dynamicgui.core.function.impl.MoneyDepositFunction;
import com.clubobsidian.dynamicgui.test.mock.MockFactory;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MoneyDepositFunctionTest {

    private final MockFactory factory = new MockFactory();

    @Test
    public void testInvalidData() {
        this.factory.inject();
        PlayerWrapper<?> player = this.factory.createPlayer();
        Function function = new MoneyDepositFunction();
        function.setData("a");
        assertFalse(function.function(player));
    }

    @Test
    public void testNullEconomy() {
        this.factory.inject().getPlugin().economy = null;
        PlayerWrapper<?> player = this.factory.createPlayer();
        Function function = new MoneyDepositFunction();
        function.setData("10");
        assertFalse(function.function(player));
    }

    @Test
    public void testValidDeposit() {
        PlayerWrapper<?> player = this.factory.createPlayer();
        this.factory.inject();
        Function function = new MoneyDepositFunction();
        function.setData("10");
        assertTrue(function.function(player));
    }
}
