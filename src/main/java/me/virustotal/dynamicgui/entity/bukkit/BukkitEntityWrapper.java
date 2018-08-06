package me.virustotal.dynamicgui.entity.bukkit;

import org.bukkit.entity.Entity;

import me.virustotal.dynamicgui.entity.EntityWrapper;

public class BukkitEntityWrapper<T extends Entity> extends EntityWrapper<T> {

	public BukkitEntityWrapper(T entity) 
	{
		super(entity);
	}
}