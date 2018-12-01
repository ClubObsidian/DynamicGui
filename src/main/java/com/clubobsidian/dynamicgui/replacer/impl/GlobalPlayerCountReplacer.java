package com.clubobsidian.dynamicgui.replacer.impl;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.replacer.Replacer;

public class GlobalPlayerCountReplacer extends Replacer {

	public GlobalPlayerCountReplacer(String toReplace) 
	{
		super(toReplace);
	}

	@Override
	public String replacement(String text, PlayerWrapper<?> playerWrapper) 
	{
		return String.valueOf(DynamicGui.get().getServer().getGlobalPlayerCount());
	}
}