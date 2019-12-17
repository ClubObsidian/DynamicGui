package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.world.WorldWrapper;

public class CheckPlayerWorldFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7760274986999938696L;

	public CheckPlayerWorldFunction(String name) 
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
		
		String worldName = this.getData();
		WorldWrapper<?> worldWrapper = playerWrapper.getLocation().getWorld();
		return worldName.equals(worldWrapper.getName());
	}
}