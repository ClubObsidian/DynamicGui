package me.virustotal.dynamicgui.function.impl;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.Function;

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
	public boolean function(final PlayerWrapper<?> player)
	{
		return !player.hasPermission(this.getData());
	}	
}