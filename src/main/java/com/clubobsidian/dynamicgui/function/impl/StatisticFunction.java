package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.util.Statistic;

public class StatisticFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8624786841614185001L;

	public StatisticFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		String[] split = this.getData().split(",");

		if(split.length == 3)
		{
			Statistic stat = Statistic.valueOf(split[0]);
			String type = split[1];
			Integer num = Integer.parseInt(split[2]);

			if(stat == Statistic.MINE_BLOCK || stat == Statistic.KILL_ENTITY)
			{
				return playerWrapper.getStatistic(stat, type) >= num;
			}
		}
		else if(split.length == 2)
		{
			Statistic stat = Statistic.valueOf(split[0]);
			Integer num = Integer.parseInt(split[1]);
			return playerWrapper.getStatistic(stat) >= num;
		}
		return false;
	}
}