package me.virustotal.dynamicgui.objects.function;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.objects.Function;

public class NoPermission<T> extends Function<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6907686728880861860L;

	public NoPermission(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(final PlayerWrapper<T> player)
	{
		return !player.hasPermission(this.getData());
	}	
}