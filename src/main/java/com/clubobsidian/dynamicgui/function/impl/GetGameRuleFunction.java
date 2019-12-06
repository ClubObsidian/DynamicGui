package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.world.WorldWrapper;

public class GetGameRuleFunction extends Function {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5840073572003297982L;

	public GetGameRuleFunction(String name) 
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
			String worldRule = world.getGameRule(rule);
			return value.equals(worldRule);
		}
		
		return false;
	}
}
