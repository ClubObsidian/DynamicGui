package com.clubobsidian.dynamicgui.manager.entity.bukkit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.EntityType;

import com.clubobsidian.dynamicgui.manager.entity.EntityManager;

public class BukkitEntityManager extends EntityManager {

	private List<String> entityTypes;
	public BukkitEntityManager()
	{
		this.loadEntityTypes();
	}
	
	private void loadEntityTypes()
	{
		this.entityTypes = new ArrayList<>();
		for(EntityType type : EntityType.values())
		{
			this.entityTypes.add(type.name());
		}
	}
	
	@Override
	public List<String> getEntityTypes() 
	{
		return this.entityTypes;
	}
}