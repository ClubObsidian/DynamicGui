package me.virustotal.dynamicgui.objects.replacers;

import org.bukkit.entity.Player;

import me.virustotal.dynamicgui.objects.Replacer;

public class PlayerReplacer extends Replacer {

	public PlayerReplacer(String toReplace) 
	{
		super(toReplace);
	}

	@Override
	public String replacement(String text, Player player)
	{
		return text.replace(this.getToReplace(), player.getName());
	}	
}