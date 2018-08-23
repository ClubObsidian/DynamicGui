package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.api.ReplacerAPI;
import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;

public class PlayerCmdFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 220426382325192292L;
 
	public PlayerCmdFunction(String name, String data) 
	{
		super(name,data);
	}
	
	public PlayerCmdFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(PlayerWrapper<?> player)
	{
		player.chat("/" + ReplacerAPI.replace(this.getData(), player));
		return true;
	}
}