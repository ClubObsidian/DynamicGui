package me.virustotal.dynamicgui.objects.replacers;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.objects.Replacer;

public class OnlinePlayersReplacer extends Replacer {

	public OnlinePlayersReplacer(String toReplace) 
	{
		super(toReplace);
	}

	@Override
	public String replacement(String text, PlayerWrapper<?> player)
	{
		return text.replace(this.getToReplace(), String.valueOf(DynamicGUI.getInstance().getPlugin().getPlayerCount()));
	}
}
