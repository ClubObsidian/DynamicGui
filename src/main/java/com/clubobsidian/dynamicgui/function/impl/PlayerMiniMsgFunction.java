package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.manager.dynamicgui.MiniMessageManager;

public class PlayerMiniMsgFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9216295481570082778L;

	public PlayerMiniMsgFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper) 
	{
		if(this.getData() == null)
		{
			return false;
		}
		
		String json = MiniMessageManager.get().toJson(this.getData());
		playerWrapper.sendJsonMessage(json);
		return true;
	}
}