package com.clubobsidian.dynamicgui.api;

import java.util.ArrayList;
import java.util.List;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.objects.Replacer;

public class ReplacerAPI {
	
	private static List<Replacer> replacers = new ArrayList<>();
	
	public static String replace(String text, PlayerWrapper<?> player)
	{
		String newText = text;
		for(Replacer replacer : ReplacerAPI.replacers)
		{
			newText = newText.replace(replacer.getToReplace(), replacer.replacement(newText, player));
		}
		return newText;
	}

	public static void addReplacer(Replacer replacer)
	{
		replacers.add(replacer);
	}
}