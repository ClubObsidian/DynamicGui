package com.clubobsidian.dynamicgui.manager.entity;

import java.util.List;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.google.inject.Inject;

public abstract class EntityManager {

	@Inject
	private static EntityManager instance;
	
	public static EntityManager get()
	{	
		return instance;
	}
	
	public abstract PlayerWrapper<?> createPlayerWrapper(Object player);
	public abstract List<String> getEntityTypes();
}