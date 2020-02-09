package com.clubobsidian.dynamicgui.function.impl.cooldown;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.manager.dynamicgui.cooldown.CooldownManager;

public class SetCooldownFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3204581055961888388L;

	public SetCooldownFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper) 
	{
		if(this.getData() == null)
		{
			return false;
		}
		else if(!this.getData().contains(","))
		{
			return false;
		}
		
		String[] split = this.getData().split(",");
		if(split.length != 2)
		{
			return false;
		}
		
		String name = split[0];
		Long cooldownTime = 0L;
		try
		{
			cooldownTime = Long.parseLong(split[1]);
		}
		catch(NumberFormatException ex)
		{
			return false;
		}
		
		CooldownManager.get().removeCooldown(playerWrapper, name);
		CooldownManager.get().createCooldown(playerWrapper, name, cooldownTime);
		return true;
	}
}