package com.clubobsidian.dynamicgui.objects.replacers;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.objects.Replacer;

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