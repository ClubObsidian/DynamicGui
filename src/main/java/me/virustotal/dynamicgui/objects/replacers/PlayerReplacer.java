package me.virustotal.dynamicgui.objects.replacers;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.objects.Replacer;

public class PlayerReplacer extends Replacer {

	public PlayerReplacer(String toReplace) 
	{
		super(toReplace);
	}

	@Override
	public String replacement(String text, PlayerWrapper<?> player)
	{
		return text.replace(this.getToReplace(), player.getName());
	}	
}