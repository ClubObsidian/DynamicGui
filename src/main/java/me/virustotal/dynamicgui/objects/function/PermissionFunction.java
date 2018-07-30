package me.virustotal.dynamicgui.objects.function;

import org.bukkit.entity.Player;

import me.virustotal.dynamicgui.objects.Function;

public class PermissionFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6578996849784218130L;

	public PermissionFunction(String name,String data) 
	{
		super(name,data);
	}
	
	public PermissionFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(final Player player)
	{
		return player.hasPermission(this.getData());
	}

}
