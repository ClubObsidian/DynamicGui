package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.effect.ParticleWrapper;
import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;

public class ParticleFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6719256169872302172L;

	public ParticleFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		ParticleWrapper wrapper = new ParticleWrapper(this.getData());
		wrapper.spawnEffect(playerWrapper);
		return true;
	}	
}