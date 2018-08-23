package com.clubobsidian.dynamicgui.plugin;

import java.io.File;
import java.util.List;

import com.clubobsidian.dynamicgui.economy.Economy;
import com.clubobsidian.dynamicgui.npc.NPCRegistry;

public interface DynamicGUIPlugin<T,U> extends NPCRegistry<U> {

	public void start();
	public void stop();
	public Economy getEconomy();
	public List<NPCRegistry<U>> getNPCRegistries();
	public File getDataFolder();
	public File getConfigFile();
	public File getGuiFolder();
	public void createCommand(String guiName, String alias);
	
}