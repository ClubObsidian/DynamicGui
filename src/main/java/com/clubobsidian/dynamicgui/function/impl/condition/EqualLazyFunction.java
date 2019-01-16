package com.clubobsidian.dynamicgui.function.impl.condition;

import java.util.List;

import com.udojava.evalex.AbstractLazyFunction;
import com.udojava.evalex.Expression.LazyNumber;

public class EqualLazyFunction extends AbstractLazyFunction {

	protected EqualLazyFunction() 
	{
		super("STREQUAL", 2, true);
	}

	@Override
	public LazyNumber lazyEval(List<LazyNumber> lazyParams) 
	{
		if(lazyParams.get(0).getString().equals(lazyParams.get(1).getString()))
		{
			return ConditionFunction.ONE;
		}
		return ConditionFunction.ZERO;
	}
}