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

package com.clubobsidian.dynamicgui.core.function.condition;

import com.clubobsidian.dynamicgui.api.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.api.function.Function;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.config.ExpressionConfiguration;

import java.math.BigDecimal;
import java.util.Map;

public class ConditionFunction extends Function {

    /**
     *
     */
    private static final long serialVersionUID = -3905599553938205838L;

    public ConditionFunction() {
        super("condition");
    }

    @Override
    public boolean function(PlayerWrapper<?> playerWrapper) {
        try {
            ExpressionConfiguration config = ExpressionConfiguration.defaultConfiguration().withAdditionalFunctions(
                    Map.entry("STREQUAL", new EqualLazyFunction()),
                    Map.entry("STREQUALIGNORECASE", new IgnoreCaseEqualLazyFunction()),
                    Map.entry("STRCONTAINS", new ContainsLazyFunction()),
                    Map.entry("STRENDSWITH", new EndsWithLazyFunction()),
                    Map.entry("STRSTARTSWITH", new StartsWithLazyFunction()),
                    Map.entry("AFTERDATE", new AfterDateLazyFunction()),
                    Map.entry("BEFOREDATE", new BeforeDateLazyFunction())
                    );
            Expression expr = new Expression(this.getData(), config);
            return expr.evaluate().getNumberValue().equals(BigDecimal.ONE);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}