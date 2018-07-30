package me.virustotal.dynamicgui.objects;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;

public abstract class Replacer {
	
	private String toReplace;
	public Replacer(String toReplace)
	{
		this.toReplace = toReplace;
	}
	
	public String getToReplace()
	{
		return this.toReplace;
	}
	
	public abstract String replacement(String text, PlayerWrapper<?> player);
}