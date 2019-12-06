package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.world.WorldWrapper;

public class SetGameRuleFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 664632502310692150L;

	public SetGameRuleFunction(String name) 
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
		
		String split[] = this.getData().split(",");
		if(split.length == 3)
		{
			String worldName = split[0];
			WorldWrapper<?> world = DynamicGui.get().getServer().getWorld(worldName);
			if(world == null)
			{
				return false;
			}
			
			String rule = split[1];
			String value = split[2];
			world.setGameRule(rule, value);
			return true;
		}
		
		return false;
	}
}
