package me.virustotal.dynamicgui.objects.function;

import org.bukkit.entity.Player;

import me.virustotal.dynamicgui.objects.Function;

public class CheckLevelFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4927665292013293816L;

	public CheckLevelFunction(String name) 
	{
		super(name);
	}

	public boolean function(Player player)
	{
		return player.getLevel() >= Integer.parseInt(this.getData());
	}
	
}
