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
		this.replacers.add(new PlayerReplacer("%player%"));
		this.replacers.add(new OnlinePlayersReplacer("%online-players%"));
		this.replacers.add(new UUIDReplacer("%uuid%"));
		this.replacers.add(new PlayerLevelReplacer("%player-level%"));
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