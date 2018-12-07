package com.clubobsidian.dynamicgui.replacer.impl;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.replacer.Replacer;
import com.clubobsidian.dynamicgui.util.Statistic;

/*
 *  Generated in DynamicGuiReplacerRegistry
 */
public class StatisticReplacer extends Replacer {

	private Statistic statistic;
	private String data;
	public StatisticReplacer(String toReplace, Statistic statistic)
	{
		this(toReplace, statistic, null);
	}
	
	public StatisticReplacer(String toReplace, Statistic statistic, String data) 
	{
		super(toReplace);
		this.statistic = statistic;
		this.data = data;
	}

	@Override
	public String replacement(String text, PlayerWrapper<?> playerWrapper) 
	{
		if(this.data != null)
		{
			return String.valueOf(playerWrapper.getStatistic(this.statistic, data));
		}
		return String.valueOf(playerWrapper.getStatistic(this.statistic));
	}
}