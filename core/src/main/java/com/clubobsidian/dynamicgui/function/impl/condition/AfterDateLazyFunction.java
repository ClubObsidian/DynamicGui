package com.clubobsidian.dynamicgui.function.impl.condition;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.udojava.evalex.AbstractLazyFunction;
import com.udojava.evalex.Expression.LazyNumber;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class AfterDateLazyFunction extends AbstractLazyFunction {

    protected AfterDateLazyFunction() {
        super("AFTERDATE", 1, true);
    }

    @Override
    public LazyNumber lazyEval(List<LazyNumber> lazyParams) {
        try {
            String format = DynamicGui.get().getDateTimeFormat();
            Date now = Date.from(Instant.now());
            Date expected = new SimpleDateFormat(format).parse(lazyParams.get(0).getString());
            if(now.after(expected)) {
                return ConditionFunction.ONE;
            }
        } catch (ParseException ignore) {
            DynamicGui.get().getLogger().error(String.format("Invalid Date: %s",
                    lazyParams.get(0).getString()));
        }
        return ConditionFunction.ZERO;
    }
}