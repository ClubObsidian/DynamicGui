package com.clubobsidian.dynamicgui.npc;

import com.clubobsidian.dynamicgui.entity.EntityWrapper;

public interface NPCRegistry<T> {

	public abstract boolean isNPC(EntityWrapper<?> entity);
	public NPC<T> getNPC(EntityWrapper<?> entity);

}