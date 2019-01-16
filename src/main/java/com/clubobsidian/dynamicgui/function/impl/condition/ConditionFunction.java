package com.clubobsidian.dynamicgui.function.impl.condition;

import java.math.BigDecimal;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.udojava.evalex.Expression;
import com.udojava.evalex.Expression.LazyNumber;

public class ConditionFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3905599553938205838L;

	public ConditionFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper) 
	{
		try
		{
			Expression expr = new Expression(this.getData());
			expr.addLazyFunction(new EqualLazyFunction());
			
			if(!expr.isBoolean())
				return false;

			return expr.eval().intValue() == 1;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	public static LazyNumber ZERO = new LazyNumber() 
	{
		public BigDecimal eval() 
		{
			return BigDecimal.ZERO;
		}
		public String getString() 
		{
			return "0";
		}
	};

	public static LazyNumber ONE = new LazyNumber() 
	{
		public BigDecimal eval() 
		{
			return BigDecimal.ONE;
		}         
		public String getString() 
		{
			return null;
		}
	};  
}