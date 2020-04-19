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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.clubobsidian.dynamicgui.animation.AnimationHolder;
import com.clubobsidian.dynamicgui.enchantment.EnchantmentWrapper;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.manager.dynamicgui.AnimationReplacerManager;
import com.clubobsidian.dynamicgui.manager.dynamicgui.ReplacerManager;
import com.clubobsidian.dynamicgui.manager.inventory.ItemStackManager;
import com.clubobsidian.dynamicgui.parser.function.tree.FunctionTree;

public class Slot implements Serializable, FunctionOwner, AnimationHolder {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2366997214615469494L;
	private int index;
	private String icon;
	private String name;
	private String nbt;
	private short data;
	private boolean glow;

	private List<String> lore;
	private List<EnchantmentWrapper> enchants;
	private Boolean close;
	private int amount;
	private transient ItemStackWrapper<?> itemStack;
	private Gui owner;
	private FunctionTree functions;
	private final int updateInterval;
	private int tick;
	private int frame;
	private Map<String, String> metadata;
	private boolean update;
	public Slot(int index, int amount, String icon, String name, String nbt, short data, boolean glow, Boolean close, List<String> lore, List<EnchantmentWrapper> enchants, FunctionTree functions, int updateInterval, Map<String, String> metadata)
	{
		this.icon = icon;
		this.data = data;
		this.name = name;
		this.nbt = nbt;
		this.glow = glow;
		this.lore = lore;
		this.enchants = enchants;
		this.close = close;
		this.index = index;
		this.amount = amount;
		this.functions = functions;
		this.updateInterval = updateInterval;
		this.tick = 0;
		this.frame = 0;
		this.metadata = metadata;
		this.update = false;
	}
	
	public int getIndex()
	{
		return this.index;
	}
	
	public void setIndex(int index)
	{
		this.index = index;
	}
	
	public String getIcon()
	{
		return this.icon;
	}
	
	public int getAmount()
	{
		return this.amount;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getNBT()
	{
		return this.nbt;
	}
	
	public boolean getGlow()
	{
		return this.glow;
	}
	
	public short getData()
	{
		return this.data;
	}
	
	public List<String> getLore()
	{
		return this.lore;
	}
	
	public List<EnchantmentWrapper> getEnchants()
	{
		return this.enchants;
	}
	
	public Boolean getClose()
	{
		return this.close;
	}
	
	public void setClose(Boolean close)
	{
		this.close = close;
	}
	
	@Override
	public FunctionTree getFunctions()
	{
		return this.functions;
	}
	
	public ItemStackWrapper<?> buildItemStack(PlayerWrapper<?> playerWrapper)
	{
		ItemStackWrapper<?> builderItem = this.itemStack;
		
		if(builderItem == null) 
		{
			builderItem = ItemStackManager.get().createItemStackWrapper(this.icon, this.amount);
		}
		else
		{
			builderItem.setType(this.icon);
			builderItem.setAmount(this.amount);
		}
		
		if(this.data != 0)
		{
			builderItem.setDurability(this.data);
		}
		
		if(this.name != null)
		{
			String newName = this.name;
			newName = ReplacerManager.get().replace(newName, playerWrapper);
			newName = AnimationReplacerManager.get().replace(this, playerWrapper, newName);
			builderItem.setName(newName);
		}
		
		if(this.lore != null)
		{
			List<String> newLore = new ArrayList<>();
			
			for(String newString : this.lore)
			{
				String lore = ReplacerManager.get().replace(newString, playerWrapper);
				lore = AnimationReplacerManager.get().replace(this, playerWrapper, lore);
				if(lore.contains("\n"))
				{
					String[] split = lore.split("\n");
					for(String sp : split)
					{
						newLore.add(sp);
					}
				}
				else
				{
					newLore.add(lore);
				}
			}
		
			builderItem.setLore(newLore);
		}
		
		if(this.enchants != null)
		{
			for(EnchantmentWrapper ench : this.enchants)
			{
				builderItem.addEnchant(ench);
			}
		}
		
		if(this.glow)
		{
			builderItem.setGlowing(true);
		}
		
		if(this.nbt != null && !this.nbt.equals(""))
		{
			builderItem.setNBT(ReplacerManager.get().replace(this.nbt, playerWrapper));
		}
		
		this.itemStack = builderItem;
		return builderItem;
	}
	
	public ItemStackWrapper<?> getItemStack()
	{
		return this.itemStack;
	}
	
	public void setOwner(Gui gui)
	{
		this.owner = gui;
	}
	
	public Gui getOwner()
	{
		return this.owner;
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
	public void resetTick() 
	{
		this.tick = 0;
	}

	@Override
	public int tick() 
	{
		this.tick += 1;
		
		if(this.tick > 20)
		{
			this.tick = 1;
			this.frame += 1;
			
			//Reset frame
			if(this.frame == Integer.MAX_VALUE)
			{
				this.frame = 0;
			}
		}
		
		return this.tick; 
	}
	
	@Override
	public int getFrame()
	{
		return this.frame;
	}
	
	@Override
	public void resetFrame()
	{
		this.frame = 0;
	}
	
	public Map<String, String> getMetadata()
	{
		return this.metadata;
	}
	
	public boolean getUpdate()
	{
		return this.update;
	}
	
	public void setUpdate(boolean update)
	{
		this.update = update;
	}
}