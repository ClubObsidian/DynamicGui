/*
 *    Copyright 2021 Club Obsidian and contributors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.clubobsidian.dynamicgui.function;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.fuzzutil.StringFuzz;

public abstract class Function implements Cloneable, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1492427006104061443L;
	private String name;
	private String data;
	private FunctionOwner owner;
	private int index = -1;
	
	public Function(String name, String data)
	{
		this.name = StringFuzz.normalize(name);
		this.data = data;
	}
	
	public Function(String name)
	{
		this(name, null);
	}
	
	public Function(Function function) 
	{
		this(function.getName(), function.getData());
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