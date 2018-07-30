package me.virustotal.dynamicgui.npc;

public interface NPCRegistry<E> {

	
	public abstract boolean isNPC(E entity);
	public NPC<E> getNPC(E entity);

}
