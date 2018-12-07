package com.clubobsidian.dynamicgui.manager.material;

import java.util.List;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.manager.material.bukkit.BukkitMaterialManager;
import com.clubobsidian.dynamicgui.manager.material.sponge.SpongeMaterialManager;
import com.clubobsidian.dynamicgui.server.ServerType;

public abstract class MaterialManager {

	private static MaterialManager instance;
	
	public static MaterialManager get()
	{
		if(instance == null)
		{
			if(ServerType.SPIGOT == DynamicGui.get().getServer().getType())
			{
				instance = new BukkitMaterialManager();
			}
			else if(ServerType.SPONGE == DynamicGui.get().getServer().getType())
			{
				instance = new SpongeMaterialManager();
			}
		}
		return instance;
	}
	
	public abstract List<String> getMaterials();

}