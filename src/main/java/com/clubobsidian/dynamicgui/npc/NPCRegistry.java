package com.clubobsidian.dynamicgui.npc;

import com.clubobsidian.dynamicgui.entity.EntityWrapper;

public interface NPCRegistry {

	public abstract boolean isNPC(EntityWrapper<?> entityWrapper);
	public NPC getNPC(EntityWrapper<?> entityWrapper);

}