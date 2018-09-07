/*
   Copyright 2018 Club Obsidian and contributors.

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
import java.util.List;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.registry.replacer.ReplacerRegistry;
import com.clubobsidian.dynamicgui.replacer.impl.OnlinePlayersReplacer;
import com.clubobsidian.dynamicgui.replacer.impl.PlayerLevelReplacer;
import com.clubobsidian.dynamicgui.replacer.impl.PlayerReplacer;
import com.clubobsidian.dynamicgui.replacer.impl.Replacer;
import com.clubobsidian.dynamicgui.replacer.impl.UUIDReplacer;

public class DynamicGuiReplacerRegistry implements ReplacerRegistry {

	private static DynamicGuiReplacerRegistry instance;
	
	private List<Replacer> replacers;
	private DynamicGuiReplacerRegistry()
	{
		this.replacers = new ArrayList<>();
		this.addReplacer(new PlayerReplacer("%player%"));
		this.addReplacer(new OnlinePlayersReplacer("%online-players%"));
		this.addReplacer(new UUIDReplacer("%uuid%"));
		this.addReplacer(new PlayerLevelReplacer("%player-level%"));
	}
	
	@Override
	public String replace(PlayerWrapper<?> playerWrapper, String text) 
	{
		for(Replacer replacer : this.replacers)
		{
			text = replacer.replacement(text, playerWrapper);
		}
		return text;
	}

	public static DynamicGuiReplacerRegistry get()
	{
		if(instance == null)
		{
			instance = new DynamicGuiReplacerRegistry();
		}
		return instance;
	}
	
	public void addReplacer(Replacer replacer)
	{
		this.replacers.add(replacer);
	}
}
