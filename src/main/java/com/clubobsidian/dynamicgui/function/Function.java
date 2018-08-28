package com.clubobsidian.dynamicgui.function;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Slot;

public abstract class Function implements Cloneable, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1492427006104061443L;
	private String name;
	private String data;
	private FunctionOwner owner;
	private int index = -1;
	
	public Function(String name)
	{
		this.name = name;
		this.data = null;
	}
	
	public Function(String name, String data)
	{
		this.name = name;
		this.data = data;
	}
	
	public Function(Function function) 
	{
		this.name = function.getName();
		this.data = function.getData();
	}

	public abstract boolean function(PlayerWrapper<?> playerWrapper);
		
	public String getName()
	{
		return this.name;
	}
	
	public String getData()
	{
		return this.data;
	}
	
	public void setData(String data)
	{
		this.data = data;
	}
	
	public void setOwner(FunctionOwner slot)
	{
		this.owner = slot;
	}
	
	public FunctionOwner getOwner()
	{
		return this.owner;
	}
	
	public void setIndex(int index)
	{
		this.index = index;
	}
	
	public int getIndex()
	{
		return this.index;
	}
	
	public Function clone()
	{
		return SerializationUtils.clone(this);
	}
}