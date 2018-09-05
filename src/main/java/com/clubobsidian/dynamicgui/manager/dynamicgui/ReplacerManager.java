package com.clubobsidian.dynamicgui.manager.dynamicgui;

import java.util.ArrayList;
import java.util.List;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.registry.replacer.ReplacerRegistry;
import com.clubobsidian.dynamicgui.registry.replacer.impl.DynamicGuiReplacerRegistry;
import com.clubobsidian.dynamicgui.replacer.impl.Replacer;

public class ReplacerManager {
	
	private static ReplacerManager instance;
	
	private List<ReplacerRegistry> registries;
	private ReplacerManager()
	{
		this.registries = new ArrayList<>();
	}
	
	public static ReplacerManager get()
	{
		if(instance == null)
		{
			instance = new ReplacerManager();
		}
		return instance;
	}
	
	public String replace(String text, PlayerWrapper<?> playerWrapper)
	{
		String newText = text;
		for(ReplacerRegistry registry : this.registries)
		{
			newText = registry.replace(playerWrapper, text);
		}
		return newText;
	}

	public void registerReplacerRegistry(ReplacerRegistry replacerRegistry) 
	{
		this.registries.add(replacerRegistry);
	}
}