package me.virustotal.dynamicgui.function.impl;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.Function;
import me.virustotal.dynamicgui.util.Statistic;

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
	public boolean function(PlayerWrapper<?> player)
	{
		String[] split = this.getData().split(",");

		if(split.length == 3)
		{
			Statistic stat = Statistic.valueOf(split[0]);
			String type = split[1];
			Integer num = Integer.parseInt(split[2]);

			if(stat == Statistic.MINE_BLOCK || stat == Statistic.KILL_ENTITY)
			{
				return player.getStatistic(stat, type) >= num;
			}
		}
		else if(split.length == 2)
		{
			Statistic stat = Statistic.valueOf(split[0]);
			Integer num = Integer.parseInt(split[1]);
			return player.getStatistic(stat) >= num;
		}
		return false;
	}
}