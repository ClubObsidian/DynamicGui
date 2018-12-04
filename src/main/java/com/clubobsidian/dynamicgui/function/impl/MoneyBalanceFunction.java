package com.clubobsidian.dynamicgui.function.impl;

import java.math.BigDecimal;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;

public class MoneyBalanceFunction extends Function {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2637510737725573158L;

	public MoneyBalanceFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(final PlayerWrapper<?> playerWrapper)
	{
		double amt;
		try
		{
			amt = Double.parseDouble(this.getData());
		}
		catch(Exception ex)
		{ 
			ex.printStackTrace();
			return false;
		}
		
		if(DynamicGui.get().getPlugin().getEconomy() == null)
			return false;
		
		BigDecimal decimalAmt = new BigDecimal(amt);

		return DynamicGui.get().getPlugin().getEconomy().getBalance(playerWrapper).compareTo(decimalAmt) >= 0;
	}
}