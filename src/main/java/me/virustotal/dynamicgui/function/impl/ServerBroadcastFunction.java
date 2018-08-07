package me.virustotal.dynamicgui.function.impl;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.ReplacerAPI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.Function;
import me.virustotal.dynamicgui.util.ChatColor;


public class ServerBroadcastFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8252199196221271208L;

	public ServerBroadcastFunction(String name, String data) 
	{
		super(name,data);
	}
	
	public ServerBroadcastFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(PlayerWrapper<?> player)
	{
		DynamicGUI.get().getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',  ReplacerAPI.replace(this.getData(), player)));
		return true;
	}
}