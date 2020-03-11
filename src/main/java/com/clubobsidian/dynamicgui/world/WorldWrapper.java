package com.clubobsidian.dynamicgui.world;

import java.io.Serializable;

public abstract class WorldWrapper<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2342470292001970943L;

	private String name;
	public WorldWrapper(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public abstract T getWorld();
	public abstract void setGameRule(String rule, String value);
	public abstract String getGameRule(String rule);

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) 
	{
		if (this == obj)
		{
			return true;
		}
		else if (obj == null)
		{
			return false;
		}
		else if (getClass() != obj.getClass())
		{
			return false;
		}
		
		WorldWrapper<T> otherWrapper = (WorldWrapper<T>) obj;
		T world = this.getWorld();
		if(world == null)
		{
			return false;
		}
		
		String otherName = otherWrapper.getName();
		
		return this.getName().equals(otherName);
	}	
}