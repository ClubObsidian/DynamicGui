/*
   Copyright 2019 Club Obsidian and contributors.

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
package com.clubobsidian.dynamicgui.gui;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;

import com.clubobsidian.dynamicgui.animation.AnimationHolder;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.manager.dynamicgui.AnimationReplacerManager;
import com.clubobsidian.dynamicgui.manager.dynamicgui.ReplacerManager;
import com.clubobsidian.dynamicgui.manager.inventory.InventoryManager;
import com.clubobsidian.dynamicgui.parser.function.tree.FunctionTree;
import com.clubobsidian.dynamicgui.util.ChatColor;
import com.clubobsidian.dynamicgui.world.LocationWrapper;


public class Gui implements Serializable, FunctionOwner, AnimationHolder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6294818826223305057L;
	private List<Slot> slots;
	private String name;
	private String type;
	private String title;
	private int rows;
	private Boolean close;
	private ModeEnum modeEnum;
	private List<LocationWrapper<?>> locations;
	private Map<String, List<Integer>> npcIds;
	private transient InventoryWrapper<?> inventoryWrapper;
	private FunctionTree functions;
	private final int updateInterval;
	private int tick;
	public Gui(String name, String type, String title, int rows, Boolean close, ModeEnum modeEnum, Map<String, List<Integer>> npcIds, List<Slot> slots, List<LocationWrapper<?>> locations, FunctionTree functions, int updateInterval)
	{
		this.name = name;
		this.type = type;
		this.title = ChatColor.translateAlternateColorCodes(title);
		this.rows = rows;
		this.slots = slots;
		this.close = close;
		this.modeEnum = modeEnum;
		this.npcIds = npcIds;
		this.locations = locations;
		this.inventoryWrapper = null;
		this.functions = functions;
		this.updateInterval = updateInterval;
		this.tick = 0;
	}

	public InventoryWrapper<?> buildInventory(PlayerWrapper<?> playerWrapper)
	{	
		String inventoryTitle = ReplacerManager.get().replace(this.title, playerWrapper);
		inventoryTitle = AnimationReplacerManager.get().replace(this, playerWrapper, inventoryTitle);
		
		if(inventoryTitle.length() > 32)
			inventoryTitle = inventoryTitle.substring(0,31);
		
		Object serverInventory = null;
		if(this.type == null || this.type.equals(InventoryType.CHEST.toString()))
		{
			serverInventory = InventoryManager.get().createInventory(this.rows * 9, inventoryTitle);
		}
		else
		{
			serverInventory = InventoryManager.get().createInventory(inventoryTitle, this.type);
		}
		
		InventoryWrapper<?> inventoryWrapper = InventoryManager.get().createInventoryWrapper(serverInventory);

		for(int i = 0; i < this.slots.size(); i++)
		{
			Slot slot = this.slots.get(i);
			if(slot != null)
			{
				slot.setOwner(this);
				ItemStackWrapper<?> item = slot.buildItemStack(playerWrapper);

				if(this.modeEnum == ModeEnum.ADD)
				{
					int itemIndex = inventoryWrapper.addItem(item);
					if(itemIndex != -1)
					{
						slot.setIndex(itemIndex);
					}
				}
				else
				{
					inventoryWrapper.setItem(slot.getIndex(), item);
				}
			}
		}
		
		this.inventoryWrapper = inventoryWrapper;
		return inventoryWrapper;
	}

	public String getName()
	{
		return this.name;
	}
	
	public String getType()
	{
		return this.type;
	}

	public String getTitle()
	{
		return this.title;
	}
	
	public int getRows()
	{
		return this.rows;
	}
	
	public List<Slot> getSlots()
	{
		return this.slots;
	}
	
	public Boolean getClose()
	{
		return this.close;
	}
	
	public void setClose(Boolean close)
	{
		this.close = close;
	}
	
	public Map<String, List<Integer>> getNpcIds()
	{
		return this.npcIds;
	}
	
	public List<LocationWrapper<?>> getLocations()
	{
		return this.locations;
	}
	
	public ModeEnum getModeEnum()
	{
		return this.modeEnum;
	}
	
	public InventoryWrapper<?> getInventoryWrapper()
	{
		return this.inventoryWrapper;
	}
	
	@Override
	public FunctionTree getFunctions()
	{
		return this.functions;
	}
	
	@Override
	public int getUpdateInterval() 
	{
		return this.updateInterval;
	}

	@Override
	public int getCurrentTick() 
	{
		return this.tick;
	}

	@Override
	public int tick() 
	{
		this.tick += 1;
		
		if(this.tick > 20)
			this.tick = 1;
		
		return this.tick; 
	}
	
	public Gui clone()
	{
		return SerializationUtils.clone(this);
	}
}