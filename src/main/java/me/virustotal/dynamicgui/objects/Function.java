package me.virustotal.dynamicgui.objects;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
import org.bukkit.entity.Player;

import me.virustotal.dynamicgui.gui.Slot;

public class Function implements Cloneable, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1492427006104061443L;
	private String name;
	private String data;
	private Slot owner;
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

	public boolean function(Player player)
	{
		System.out.println("default function");
		return false;
	}
		
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
	
	public void setOwner(Slot slot)
	{
		this.owner = slot;
	}
	
	public Slot getOwner()
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