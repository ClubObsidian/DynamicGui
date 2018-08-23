package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.api.ReplacerAPI;
import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.util.ChatColor;

public class PlayerMsgFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6244543904061733902L;
	
	public PlayerMsgFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(final PlayerWrapper<?> playerWrapper)
	{
		playerWrapper.sendMessage(ChatColor.translateAlternateColorCodes(ReplacerAPI.replace(this.getData(), playerWrapper)));
		return true;
	}	
}