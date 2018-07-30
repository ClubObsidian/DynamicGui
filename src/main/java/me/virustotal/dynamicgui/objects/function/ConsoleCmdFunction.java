package me.virustotal.dynamicgui.objects.function;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.virustotal.dynamicgui.api.ReplacerAPI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.objects.Function;

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
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ReplacerAPI.replace(this.getData(), player));
		return true;
	}
	
}
