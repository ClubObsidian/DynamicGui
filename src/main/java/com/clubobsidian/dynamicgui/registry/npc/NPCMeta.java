package com.clubobsidian.dynamicgui.registry.npc;

public class NPCMeta {

	private int id;
	private String plugin;
	public NPCMeta(int id, String plugin) 
	{
		this.id = id;
		this.plugin = plugin;
	}

	public int getId()
	{
		return this.id;
	}
	
	public String getPlugin()
	{
		return this.plugin;
	}
}