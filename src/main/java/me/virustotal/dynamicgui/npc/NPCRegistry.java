package me.virustotal.dynamicgui.npc;

import me.virustotal.dynamicgui.entity.EntityWrapper;

public interface NPCRegistry<T> {

	public abstract boolean isNPC(EntityWrapper<?> entity);
	public NPC<T> getNPC(EntityWrapper<?> entity);

}