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

import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.functions.FunctionParameter;
import com.ezylang.evalex.parser.Token;
import com.ezylang.evalex.functions.AbstractFunction;

import java.math.BigDecimal;

@FunctionParameter(name = "value", isLazy = true)
@FunctionParameter(name = "startsWith", isLazy = true)
public class StartsWithLazyFunction extends AbstractFunction {

    @Override
    public EvaluationValue evaluate(Expression expression, Token functionToken, EvaluationValue... parameterValues) {
        if(parameterValues[0].getStringValue().startsWith(parameterValues[1].getStringValue())){
            return new EvaluationValue(BigDecimal.ONE);
        }
        return new EvaluationValue(BigDecimal.ZERO);
    }
}