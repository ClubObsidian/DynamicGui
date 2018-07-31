package me.virustotal.dynamicgui.entity.impl;

import org.spongepowered.api.entity.Entity;

import me.virustotal.dynamicgui.entity.EntityWrapper;

public class SpongeEntityWrapper<T extends Entity> extends EntityWrapper<T> {

	public SpongeEntityWrapper(T entity) 
	{
		super(entity);
	}
}