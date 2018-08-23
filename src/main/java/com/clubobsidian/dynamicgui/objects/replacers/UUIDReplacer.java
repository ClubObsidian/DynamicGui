package com.clubobsidian.dynamicgui.objects.replacers;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.objects.Replacer;

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