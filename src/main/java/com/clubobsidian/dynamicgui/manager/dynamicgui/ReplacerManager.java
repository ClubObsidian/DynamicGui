package com.clubobsidian.dynamicgui.manager.dynamicgui;

import java.util.ArrayList;
import java.util.List;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.replacer.impl.Replacer;

public class ReplacerManager {
	
	private static ReplacerManager instance;
	
	private List<Replacer> replacers;
	private ReplacerManager()
	{
		this.replacers = new ArrayList<>();
	}
	
	public static ReplacerManager get()
	{
		if(instance == null)
		{
			instance = new ReplacerManager();
		}
		return instance;
	}
	
	public String replace(String text, PlayerWrapper<?> player)
	{
		String newText = text;
		for(Replacer replacer : this.replacers)
		{
			newText = newText.replace(replacer.getToReplace(), replacer.replacement(newText, player));
		}
		return newText;
	}

	public void addReplacer(Replacer replacer)
	{
		this.replacers.add(replacer);
	}
}