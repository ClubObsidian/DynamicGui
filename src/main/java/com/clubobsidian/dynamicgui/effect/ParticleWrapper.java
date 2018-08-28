package com.clubobsidian.dynamicgui.effect;

import java.io.Serializable;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;

public class ParticleWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5003322741003989392L;
	private String effect;
	private int data;
	
	public ParticleWrapper(String str)
	{
		String[] args = str.split(",");
		this.effect = args[0];
		this.data = Integer.parseInt(args[1]);
	}
	
	public void spawnEffect(PlayerWrapper<?> player)
	{
		player.playEffect(this.effect, this.data);
	}	
}