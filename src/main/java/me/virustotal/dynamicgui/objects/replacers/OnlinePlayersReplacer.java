package me.virustotal.dynamicgui.objects.replacers;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.objects.Replacer;

import org.bukkit.entity.Player;

public class OnlinePlayersReplacer extends Replacer {

	public OnlinePlayersReplacer(String toReplace) 
	{
		super(toReplace);
	}

	@Override
	public String replacement(String text, Player player)
	{
		return text.replace(this.getToReplace(), String.valueOf(DynamicGUI.getPlugin().getPlayerCountAll()));
	}
}
