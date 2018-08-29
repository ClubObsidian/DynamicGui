package com.clubobsidian.dynamicgui.function.impl;

import java.math.BigDecimal;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;

public class PayFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8941864727381394744L;

	public PayFunction(String name) 
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
		if(DynamicGui.get().getPlugin().getEconomy().getBalance(playerWrapper).compareTo(decimalAmt) == -1)
		{
			return false;
		}

		DynamicGui.get().getPlugin().getEconomy().withdraw(playerWrapper, decimalAmt);
		return true;
	}
}
