package com.clubobsidian.dynamicgui.manager.dynamicgui.cooldown;

public class Cooldown {

	private String name;
	private Long time;
	private Long cooldown;
	public Cooldown(String name, Long time, Long cooldown)
	{
		this.name = name;
		this.time = time;
		this.cooldown = cooldown;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public Long getTime()
	{
		return this.time;
	}
	
	public Long getCooldown()
	{
		return this.cooldown;
	}
}