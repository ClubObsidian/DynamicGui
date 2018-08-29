package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.DynamicGui2;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.manager.dynamicgui.ReplacerManager;
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
		DynamicGui2.get().getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',  ReplacerManager.get().replace(this.getData(), playerWrapper)));
		return true;
	}
}