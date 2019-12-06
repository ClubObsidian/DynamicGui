package com.clubobsidian.dynamicgui.world.sponge;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.World;

import com.clubobsidian.dynamicgui.world.WorldWrapper;

public class SpongeWorldWrapper extends WorldWrapper<World>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5164322923268176714L;

	public SpongeWorldWrapper(String name) 
	{
		super(name);
	}

	@Override
	public World getWorld() 
	{
		return Sponge.getServer().getWorld(this.getName()).get();
	}

	@Override
	public void setGameRule(String rule, String value) 
	{
		this.getWorld().getProperties().setGameRule(rule, value);
	}

	@Override
	public String getGameRule(String rule) 
	{
		Optional<String> gameRule = this.getWorld().getGameRule(rule);
		if(gameRule.isPresent())
		{
			return gameRule.get();
		}
		
		return null;
	}
}