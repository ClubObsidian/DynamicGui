package com.clubobsidian.dynamicgui.objects.replacers;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.objects.Replacer;

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