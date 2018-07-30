package me.virustotal.dynamicgui.plugin;

import java.util.List;

import me.virustotal.dynamicgui.economy.Economy;
import me.virustotal.dynamicgui.npc.NPCRegistry;

public interface DynamicGUIPlugin<P,E> extends NPCRegistry<E> {

	public void start();
	public void stop();
	public int getPlayerCount();
	public Economy<P> getEconomy();
	public List<NPCRegistry<E>> getNPCRegistries();
	
}