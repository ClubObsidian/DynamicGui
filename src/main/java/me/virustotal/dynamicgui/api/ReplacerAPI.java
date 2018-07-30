package me.virustotal.dynamicgui.api;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import me.virustotal.dynamicgui.objects.Replacer;

public class ReplacerAPI {
	
	private static ArrayList<Replacer> replacers = new ArrayList<Replacer>();
	
	public static String replace(String text, Player player)
	{
		String newText = text;
		for(Replacer replacer : ReplacerAPI.replacers)
		{
			if(text.contains(replacer.getToReplace()))
				newText = replacer.replacement(newText, player);
		}
		return newText;
	}

	public static void addReplacer(Replacer replacer)
	{
		replacers.add(replacer);
	}
}