package com.clubobsidian.dynamicgui.manager.entity.sponge;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.entity.EntityTypes;

import com.clubobsidian.dynamicgui.manager.entity.EntityManager;

public class SpongeEntityManager extends EntityManager {

	private List<String> entityTypes;
	public SpongeEntityManager() 
	{
		this.loadEntityTypes();
	}
	
	private void loadEntityTypes()
	{
		this.entityTypes = new ArrayList<>();
		for(Field field : EntityTypes.class.getDeclaredFields())
		{
			this.entityTypes.add(field.getName());
		}
	}

	@Override
	public List<String> getEntityTypes() 
	{
		return this.entityTypes;
	}
}