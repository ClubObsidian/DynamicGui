package me.virustotal.dynamicgui.function.impl;

import me.virustotal.dynamicgui.api.ReplacerAPI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.Function;
import me.virustotal.dynamicgui.util.ChatColor;

public class PlayerMsgFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6244543904061733902L;

	public PlayerMsgFunction(String name, String data) 
	{
		super(name,data);
	}
	
	public PlayerMsgFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(final PlayerWrapper<?> player)
	{
		player.sendMessage(ChatColor.translateAlternateColorCodes(ReplacerAPI.replace(this.getData(), player)));
		return true;
	}	
}