package me.virustotal.dynamicgui.npc;

public interface NPCRegistry<T> {

	
	public abstract boolean isNPC(T entity);
	public NPC<T> getNPC(T entity);

}
