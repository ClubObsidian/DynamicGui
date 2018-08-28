package com.clubobsidian.dynamicgui.replacer.impl;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;

public class PlayerLevelReplacer extends Replacer {

	public PlayerLevelReplacer(String toReplace) 
	{
		super(toReplace);
	}

	@Override
	public String replacement(String text, PlayerWrapper<?> player)
	{
		return String.valueOf(player.getLevel());
	}	
}