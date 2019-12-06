package com.clubobsidian.dynamicgui.function.impl.cooldown;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.manager.dynamicgui.cooldown.CooldownManager;

public class IsNotOnCooldownFunction extends Function {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2369277150280303056L;

	public IsNotOnCooldownFunction(String name) 
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
		
		return !CooldownManager.get().isOnCooldown(playerWrapper, this.getData());
	}
}