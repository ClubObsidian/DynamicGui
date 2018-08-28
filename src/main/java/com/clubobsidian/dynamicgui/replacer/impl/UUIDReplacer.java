package com.clubobsidian.dynamicgui.replacer.impl;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;

public class UUIDReplacer extends Replacer {

	public UUIDReplacer(String toReplace) 
	{
		super(toReplace);
	}

	@Override
	public String replacement(String text, PlayerWrapper<?> player)
	{
		return player.getUniqueId().toString();
	}	
}