/*
   Copyright 2018 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.clubobsidian.dynamicgui.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.ModeEnum;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.world.LocationWrapper;

public class GuiBuilder  {
	
	private String name;
	private String title;
	private int rows;
	private Boolean close;
	private ModeEnum modeEnum;
	private List<Integer> npcIds = new ArrayList<>();
	private List<Slot> slots = new ArrayList<>();
	private List<LocationWrapper<?>> locs = new ArrayList<>();
	private List<Function> functions = new ArrayList<>();
	private Map<String,List<Function>> failFunctions = new HashMap<>();
	
	public GuiBuilder setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public GuiBuilder setTitle(String title)
	{
		this.title = title;
		return this;
	}
	
	public GuiBuilder setRows(int rows)
	{
		this.rows = rows;
		return this;
	}
	
	public GuiBuilder setClose(Boolean close)
	{
		this.close = close;
		return this;
	}
	public GuiBuilder setModeEnum(String mode)
	{
		this.setModeEnum(ModeEnum.valueOf(mode));
		return this;
	}
	
	public GuiBuilder setModeEnum(ModeEnum modeEnum)
	{
		this.modeEnum = modeEnum;
		return this;
	}
	
	public GuiBuilder addNpcId(Integer id)
	{
		this.npcIds.add(id);
		return this;
	}
	
	public GuiBuilder addNpcId(Integer[] npcIds)
	{
		for(Integer id : npcIds)
		{
			this.npcIds.add(id);
		}
		return this;
	}
	
	public GuiBuilder addNpcId(List<Integer> npcIds)
	{
		for(Integer id : npcIds)
		{
			this.npcIds.add(id);
		}
		return this;
	}
	
	public GuiBuilder addSlot(Slot slot)
	{
		this.slots.add(slot);
		return this;
	}
	
	public GuiBuilder addLocation(LocationWrapper<?> loc)
	{
		this.locs.add(loc);
		return this;
	}
	
	public GuiBuilder addFunction(Function function)
	{
		this.functions.add(function);
		return this;
	}
	
	public GuiBuilder addFailFunction(String failOn, Function function)
	{
		if(this.failFunctions.get(failOn) == null)
		{
			this.failFunctions.put(failOn, new ArrayList<>());
		}
		this.failFunctions.get(failOn).add(function);
		return this;
	}
	
	public Gui build()
	{
		return new Gui(this.name, this.title, this.rows, this.close, this.modeEnum, this.npcIds, this.slots, this.locs, this.functions, this.failFunctions);
	}
}