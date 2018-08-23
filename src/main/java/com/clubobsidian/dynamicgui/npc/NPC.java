package com.clubobsidian.dynamicgui.npc;

import com.clubobsidian.dynamicgui.entity.EntityWrapper;

public class NPC {

	private EntityWrapper<?> entityWrapper;
	private int id;
	public NPC(EntityWrapper<?> entityWrapper, int id)
	{
		this.entityWrapper = entityWrapper;
		this.id = id;
	}
	
	public EntityWrapper<?> getEntityWrapper()
	{
		return this.entityWrapper;
	}
	public int getId()
	{
		return this.id;
	}
}