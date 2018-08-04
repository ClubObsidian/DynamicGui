package me.virustotal.dynamicgui.api;

import java.util.ArrayList;
import java.util.List;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.objects.Replacer;

public class ReplacerAPI {
	
	private static List<Replacer> replacers = new ArrayList<Replacer>();
	
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