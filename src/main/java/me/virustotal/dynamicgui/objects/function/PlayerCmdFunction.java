package me.virustotal.dynamicgui.objects.function;

import me.virustotal.dynamicgui.api.ReplacerAPI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.objects.Function;

public class PlayerCmdFunction<P> extends Function<P> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 220426382325192292L;
 
	public PlayerCmdFunction(String name, String data) 
	{
		super(name,data);
	}
	
	public PlayerCmdFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(PlayerWrapper<P> player)
	{
		player.chat("/" + ReplacerAPI.replace(this.getData(), player));
		return true;
	}
}