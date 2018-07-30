package me.virustotal.dynamicgui.objects.function;

import org.bukkit.entity.Player;

import me.virustotal.dynamicgui.objects.Function;

public class NoPermission extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6907686728880861860L;

	public NoPermission(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(final Player player)
	{
		return !player.hasPermission(this.getData());
	}	
}