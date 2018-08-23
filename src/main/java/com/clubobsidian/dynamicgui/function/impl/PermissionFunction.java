package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;

public class PermissionFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6578996849784218130L;

	public PermissionFunction(String name,String data) 
	{
		super(name,data);
	}
	
	public PermissionFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(final PlayerWrapper<?> player)
	{
		return player.hasPermission(this.getData());
	}
}