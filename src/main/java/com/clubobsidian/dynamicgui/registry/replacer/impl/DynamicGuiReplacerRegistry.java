/*
   Copyright 2019 Club Obsidian and contributors.

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
package com.clubobsidian.dynamicgui.registry.replacer.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.DynamicGuiReloadEvent;
import com.clubobsidian.dynamicgui.manager.entity.EntityManager;
import com.clubobsidian.dynamicgui.manager.material.MaterialManager;
import com.clubobsidian.dynamicgui.registry.replacer.ReplacerRegistry;
import com.clubobsidian.dynamicgui.replacer.Replacer;
import com.clubobsidian.dynamicgui.replacer.impl.GlobalPlayerCountReplacer;
import com.clubobsidian.dynamicgui.replacer.impl.OnlinePlayersReplacer;
import com.clubobsidian.dynamicgui.replacer.impl.PlayerLevelReplacer;
import com.clubobsidian.dynamicgui.replacer.impl.PlayerReplacer;
import com.clubobsidian.dynamicgui.replacer.impl.StatisticReplacer;
import com.clubobsidian.dynamicgui.replacer.impl.UUIDReplacer;
import com.clubobsidian.dynamicgui.util.Statistic;
import com.clubobsidian.dynamicgui.util.Statistic.StatisticType;
import com.clubobsidian.trident.EventHandler;

public class DynamicGuiReplacerRegistry implements ReplacerRegistry {

	private static DynamicGuiReplacerRegistry instance;
	
	private Map<String,Replacer> replacers;
	private Map<String, List<Replacer>> cachedReplacers;
	private DynamicGuiReplacerRegistry()
	{
		this.replacers = new HashMap<>();
		this.cachedReplacers = new HashMap<>();
		this.addReplacer(new PlayerReplacer("%player%"));
		this.addReplacer(new OnlinePlayersReplacer("%online-players%"));
		this.addReplacer(new GlobalPlayerCountReplacer("%global-playercount%"));
		this.addReplacer(new UUIDReplacer("%uuid%"));
		this.addReplacer(new PlayerLevelReplacer("%player-level%"));
		this.generateStatisticReplacers();
		DynamicGui.get().getEventBus().registerEvents(this);
	}
	
	@Override
	public String replace(final PlayerWrapper<?> playerWrapper, final String text) 
	{
		String newText = text;
		List<Replacer> cachedReplacerList = this.cachedReplacers.get(text);
		if(cachedReplacerList != null)
		{
			for(Replacer replacer : cachedReplacerList)
			{
				newText = StringUtils.replace(newText, replacer.getToReplace(), replacer.replacement(newText, playerWrapper));
			}
		}
		else
		{
			cachedReplacerList = new ArrayList<>();
			for(Replacer replacer : this.replacers.values())
			{
				if(newText.contains(replacer.getToReplace()))
				{
					newText = StringUtils.replace(newText, replacer.getToReplace(), replacer.replacement(newText, playerWrapper));
					cachedReplacerList.add(replacer);
				}
			}
			
			this.cachedReplacers.put(text, cachedReplacerList);
		}
		
		
		return newText;
	}

	public static DynamicGuiReplacerRegistry get()
	{
		if(instance == null)
		{
			instance = new DynamicGuiReplacerRegistry();
		}
		return instance;
	}
	
	public boolean addReplacer(Replacer replacer)
	{
		boolean put = this.replacers.put(replacer.getToReplace(), replacer) == null;
		this.cachedReplacers.clear();
		return put;
	}
	
	@EventHandler
	public void onReload(DynamicGuiReloadEvent event)
	{
		this.cachedReplacers.clear();
	}
	
	private void generateStatisticReplacers()
	{
		for(Statistic statistic : Statistic.values())
		{
			if(statistic.getStatisticType() == StatisticType.MATERIAL)
			{
				for(String material : MaterialManager.get().getMaterials())
				{
					String lowerMaterial = material.toLowerCase();
					String replacerName = "%statistic-" + statistic.name().toLowerCase() + "-" + lowerMaterial + "%";
					this.addReplacer(new StatisticReplacer(replacerName, statistic, lowerMaterial));
				}
			}
			else if(statistic.getStatisticType() == StatisticType.ENTITY)
			{
				for(String entityType : EntityManager.get().getEntityTypes())
				{
					String lowerEntityType = entityType.toLowerCase();
					String replacerName = "%statistic-" + statistic.name().toLowerCase() + "-" + lowerEntityType + "%";
					this.addReplacer(new StatisticReplacer(replacerName, statistic, lowerEntityType));
				}
			}
			else
			{
				String replacerName = "%statistic-" + statistic.name().toLowerCase() + "%";
				this.addReplacer(new StatisticReplacer(replacerName, statistic));
			}
		}
	}
}