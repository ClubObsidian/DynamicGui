package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.udojava.evalex.Expression;

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
		Expression expr = new Expression(this.getData());
		if(!expr.isBoolean())
			return false;
		
		return expr.eval().intValue() == 1;
	}
}