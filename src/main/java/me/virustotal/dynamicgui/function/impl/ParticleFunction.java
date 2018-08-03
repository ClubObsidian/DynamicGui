package me.virustotal.dynamicgui.function.impl;

import me.virustotal.dynamicgui.effect.ParticleWrapper;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.Function;

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
	public boolean function(PlayerWrapper<?> player)
	{
		ParticleWrapper wrapper = new ParticleWrapper(this.getData());
		wrapper.spawnEffect(player);
		return true;
	}	
}