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