package com.clubobsidian.dynamicgui.manager.entity;

import java.util.List;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;

public abstract class EntityManager {

	private static EntityManager instance;
	
	public static EntityManager get()
	{
		if(instance == null)
		{
			instance = DynamicGui.get().getInjector().getInstance(EntityManager.class);
		}
		
		return instance;
	}
	
	public abstract PlayerWrapper<?> createPlayerWrapper(Object player);
	public abstract List<String> getEntityTypes();
}