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

import com.clubobsidian.dynamicgui.api.DynamicGui;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.functions.AbstractFunction;
import com.ezylang.evalex.functions.FunctionParameter;
import com.ezylang.evalex.parser.Token;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@FunctionParameter(name = "BeforeDate", isLazy = true)
public class BeforeDateLazyFunction extends AbstractFunction {

    @Override
    public EvaluationValue evaluate(Expression expression, Token functionToken, EvaluationValue... parameterValues) {
        try {
            String format = DynamicGui.get().getConfig().getDateTimeFormat();
            Date now = Date.from(Instant.now());
            Date expected = new SimpleDateFormat(format).parse(parameterValues[0].getStringValue());
            if (now.before(expected)) {
                return new EvaluationValue(1);
            }
        } catch (ParseException ignore) {
            DynamicGui.get().getLogger().error("Invalid Date: %s", parameterValues[0].getStringValue());
        }
        return new EvaluationValue(0);
    }
}
