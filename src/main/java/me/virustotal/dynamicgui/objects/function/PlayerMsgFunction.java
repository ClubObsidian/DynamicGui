package me.virustotal.dynamicgui.objects.function;

import me.virustotal.dynamicgui.ChatColor;
import me.virustotal.dynamicgui.api.ReplacerAPI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.objects.Function;

public class PlayerMsgFunction<T> extends Function<T> {

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
	public boolean function(final PlayerWrapper<T> player)
	{
		player.sendMessage(ChatColor.translateAlternateColorCodes(ReplacerAPI.replace(this.getData(), player)));
		return true;
	}	
}