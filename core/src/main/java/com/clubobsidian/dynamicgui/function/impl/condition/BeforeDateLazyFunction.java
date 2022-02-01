package com.clubobsidian.dynamicgui.function.impl.condition;

import com.udojava.evalex.AbstractLazyFunction;
import com.udojava.evalex.Expression.LazyNumber;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

public class BeforeDateLazyFunction extends AbstractLazyFunction {

    protected BeforeDateLazyFunction() {
        super("BFRDATE", 1, true);
    }

    @Override
    public LazyNumber lazyEval(List<LazyNumber> lazyParams) {
        try {
            Date now = Date.from(Instant.now());
            Date expected = new SimpleDateFormat("MM dd, yyyy HH:mm:ss").parse(lazyParams.get(0).getString());
            if(now.before(expected)) {
                return ConditionFunction.ONE;
            }
        } catch (ParseException ignore) {
            // Log here? Throw?
            return ConditionFunction.ZERO;
        }
        return ConditionFunction.ZERO;
    }
}
