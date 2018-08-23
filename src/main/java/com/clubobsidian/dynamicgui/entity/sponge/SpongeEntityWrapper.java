package com.clubobsidian.dynamicgui.entity.sponge;

import org.spongepowered.api.entity.Entity;

import com.clubobsidian.dynamicgui.entity.EntityWrapper;

public class SpongeEntityWrapper<T extends Entity> extends EntityWrapper<T> {

	public SpongeEntityWrapper(T entity) 
	{
		super(entity);
	}
}