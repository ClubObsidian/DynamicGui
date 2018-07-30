package me.virustotal.dynamicgui.entity;

public abstract class EntityWrapper<T> {

	public T entity;
	public EntityWrapper(T entity)
	{
		this.entity = entity;
	}
	
	public T getEntity()
	{
		return this.entity;
	}

}
