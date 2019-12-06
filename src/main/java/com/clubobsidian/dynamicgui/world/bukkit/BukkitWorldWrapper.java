package com.clubobsidian.dynamicgui.world.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.clubobsidian.dynamicgui.world.WorldWrapper;

public class BukkitWorldWrapper extends WorldWrapper<World> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3743616842652828642L;

	public BukkitWorldWrapper(String name) 
	{
		super(name);
	}

	@Override
	public World getWorld() 
	{
		return Bukkit.getServer().getWorld(this.getName());
	}

	@Override
	public void setGameRule(String rule, String value) 
	{
		this.getWorld().setGameRuleValue(rule, value);
	}

	@Override
	public String getGameRule(String rule) 
	{
		return this.getWorld().getGameRuleValue(rule);
	}
}