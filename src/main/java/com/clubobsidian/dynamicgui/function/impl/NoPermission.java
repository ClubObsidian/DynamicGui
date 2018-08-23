package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;

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
	public boolean function(final PlayerWrapper<?> playerWrapper)
	{
		return !playerWrapper.hasPermission(this.getData());
	}	
}