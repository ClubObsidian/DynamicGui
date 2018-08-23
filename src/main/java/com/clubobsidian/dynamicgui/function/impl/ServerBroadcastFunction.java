package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.DynamicGUI;
import com.clubobsidian.dynamicgui.api.ReplacerAPI;
import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.util.ChatColor;


public class ServerBroadcastFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8252199196221271208L;
	
	public ServerBroadcastFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		DynamicGUI.get().getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',  ReplacerAPI.replace(this.getData(), playerWrapper)));
		return true;
	}
}