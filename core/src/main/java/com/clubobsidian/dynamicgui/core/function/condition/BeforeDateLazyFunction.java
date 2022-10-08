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

package com.clubobsidian.dynamicgui.core.function.condition;

import com.clubobsidian.dynamicgui.core.DynamicGui;
import com.udojava.evalex.AbstractLazyFunction;
import com.udojava.evalex.Expression.LazyNumber;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class BeforeDateLazyFunction extends AbstractLazyFunction {

    protected BeforeDateLazyFunction() {
        super("BEFOREDATE", 1, true);
    }

    @Override
    public LazyNumber lazyEval(List<LazyNumber> lazyParams) {
        try {
            String format = DynamicGui.get().getDateTimeFormat();
            Date now = Date.from(Instant.now());
            Date expected = new SimpleDateFormat(format).parse(lazyParams.get(0).getString());
            if (now.before(expected)) {
                return ConditionFunction.ONE;
            }
        } catch (ParseException ignore) {
            DynamicGui.get().getLogger().error(String.format("Invalid Date: %s",
                    lazyParams.get(0).getString()));
        }
        return ConditionFunction.ZERO;
    }
}
