package com.clubobsidian.dynamicgui.replacer.impl;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;

public class PlayerReplacer extends Replacer {

	public PlayerReplacer(String toReplace) 
	{
		super(toReplace);
	}

	@Override
	public String replacement(String text, PlayerWrapper<?> player)
	{
		return player.getName();
	}	
}