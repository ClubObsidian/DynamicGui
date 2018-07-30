package me.virustotal.dynamicgui.objects;

import org.bukkit.entity.Player;

public class Replacer {
	
	private String toReplace;
	public Replacer(String toReplace)
	{
		this.toReplace = toReplace;
	}
	
	public String getToReplace()
	{
		return this.toReplace;
	}
	
	public String replacement(String text, Player player)
	{
		return "";
	}
}