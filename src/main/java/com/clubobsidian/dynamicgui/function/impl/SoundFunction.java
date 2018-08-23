package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.objects.SoundWrapper;

public class SoundFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8363807525418126179L;

	public SoundFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		SoundWrapper wrapper = new SoundWrapper(this.getData());
		wrapper.playSoundToPlayer(playerWrapper);
		return true;
	}
}