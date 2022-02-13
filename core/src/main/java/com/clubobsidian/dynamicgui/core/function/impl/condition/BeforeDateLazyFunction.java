package com.clubobsidian.dynamicgui.core.function.impl.condition;

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
            if(now.before(expected)) {
                return ConditionFunction.ONE;
            }
        } catch(ParseException ignore) {
            DynamicGui.get().getLogger().error(String.format("Invalid Date: %s",
                    lazyParams.get(0).getString()));
        }
        return ConditionFunction.ZERO;
    }
}
