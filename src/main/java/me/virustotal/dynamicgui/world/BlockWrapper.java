package me.virustotal.dynamicgui.world;

import java.io.Serializable;

public abstract class BlockWrapper<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5767063869351289962L;
	private T block;
	public BlockWrapper(T block)
	{
		this.block = block;
	}
	
	public T getBlock()
	{
		return this.block;
	}
	
	public abstract LocationWrapper<?> getLocation();
	
}