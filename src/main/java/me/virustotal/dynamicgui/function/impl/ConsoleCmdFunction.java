package me.virustotal.dynamicgui.function.impl;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.ReplacerAPI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.Function;

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