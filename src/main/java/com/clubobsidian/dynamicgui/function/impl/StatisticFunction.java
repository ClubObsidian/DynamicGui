/*
   Copyright 2020 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.util.Statistic;
import com.clubobsidian.dynamicgui.util.Statistic.StatisticType;

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
		String statisticString = null;
		
		if(split.length >= 2)
			statisticString = split[0].toUpperCase();
		
		if(statisticString == null)
			return false;
		
		if(split.length == 3)
		{
			Statistic stat = Statistic.valueOf(statisticString);
			String type = split[1];
			Integer num = Integer.parseInt(split[2]);

			if(stat.getStatisticType() != StatisticType.NONE)
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