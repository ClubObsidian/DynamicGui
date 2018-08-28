package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.manager.dynamicgui.ReplacerManager;

public class PlayerCmdFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 220426382325192292L;
	
	public PlayerCmdFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		playerWrapper.chat("/" + ReplacerManager.get().replace(this.getData(), playerWrapper));
		return true;
	}
}