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

package com.clubobsidian.dynamicgui.core.function.impl;

import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.core.function.Function;

import java.math.BigDecimal;

public class MoneyBalanceFunction extends Function {


    /**
     *
     */
    private static final long serialVersionUID = -2637510737725573158L;

    public MoneyBalanceFunction() {
        super("moneybalance");
    }

    @Override
    public boolean function(final PlayerWrapper<?> playerWrapper) {
        double amt;
        try {
            amt = Double.parseDouble(this.getData());
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        if (DynamicGui.get().getPlugin().getEconomy() == null) {
            return false;
        }

        BigDecimal decimalAmt = new BigDecimal(amt);
        return DynamicGui.get().getPlugin().getEconomy().getBalance(playerWrapper).compareTo(decimalAmt) >= 0;
    }
}