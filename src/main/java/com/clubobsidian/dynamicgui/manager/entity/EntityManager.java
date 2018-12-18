package com.clubobsidian.dynamicgui.manager.entity;

import java.util.List;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.manager.entity.bukkit.BukkitEntityManager;
import com.clubobsidian.dynamicgui.manager.entity.sponge.SpongeEntityManager;
import com.clubobsidian.dynamicgui.server.ServerType;

public abstract class EntityManager {

	private static EntityManager instance;
	
	public static EntityManager get()
	{
		if(instance == null)
		{
			if(ServerType.SPIGOT == DynamicGui.get().getServer().getType())
			{
				instance = new BukkitEntityManager();
			}
			else if(ServerType.SPONGE == DynamicGui.get().getServer().getType())
			{
				instance = new SpongeEntityManager();
			}
		}
		return instance;
	}
	
	public abstract PlayerWrapper<?> createPlayerWrapper(Object player);
	public abstract List<String> getEntityTypes();
}