package me.virustotal.dynamicgui.function.impl;

import java.math.BigDecimal;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.Function;

public class PayFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8941864727381394744L;

	public PayFunction(String name, String data) 
	{
		super(name,data);
	}

	public PayFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(final PlayerWrapper<?> player)
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
		if(DynamicGUI.getInstance().getPlugin().getEconomy() == null)
			return false;
		
		BigDecimal decimalAmt = new BigDecimal(amt);
		if(DynamicGUI.getInstance().getPlugin().getEconomy().getBalance(player).compareTo(decimalAmt) == -1)
		{
			return false;
		}

		DynamicGUI.getInstance().getPlugin().getEconomy().withdraw(player, decimalAmt);
		return true;
	}
}
