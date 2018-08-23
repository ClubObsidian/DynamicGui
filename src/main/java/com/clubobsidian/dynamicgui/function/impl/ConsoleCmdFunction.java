package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.DynamicGUI;
import com.clubobsidian.dynamicgui.api.ReplacerAPI;
import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;

public class ConsoleCmdFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4802600274176592465L;

	public ConsoleCmdFunction(String name, String data) 
	{
		super(name, data);
	}
	
	public ConsoleCmdFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(final PlayerWrapper<?> player)
	{
		DynamicGUI.get().getServer().dispatchServerCommand(ReplacerAPI.replace(this.getData(), player));
		return true;
	}
}