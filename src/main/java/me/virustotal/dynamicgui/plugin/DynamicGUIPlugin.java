package me.virustotal.dynamicgui.plugin;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import me.virustotal.dynamicgui.economy.Economy;
import me.virustotal.dynamicgui.npc.NPCRegistry;

public interface DynamicGUIPlugin<T,U> extends NPCRegistry<U> {

	public void start();
	public void stop();
	public int getPlayerCount();
	public Economy getEconomy();
	public List<NPCRegistry<U>> getNPCRegistries();
	public Logger getLogger();
	public File getGuiFolder();
	
}