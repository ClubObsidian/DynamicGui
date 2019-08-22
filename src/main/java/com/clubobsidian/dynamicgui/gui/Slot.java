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
	private String icon;
	private String name;
	private String nbt;
	private short data;
	private int index;

	private List<String> lore;
	private List<EnchantmentWrapper> enchants;
	private Boolean close;
	private int amount;
	private ItemStackWrapper<?> itemStack;
	private Gui owner;
	private FunctionTree functions;
	private final int updateInterval;
	private int tick;
	public Slot(int index, int amount, String icon, String name, String nbt, short data, Boolean close, List<String> lore, List<EnchantmentWrapper> enchants, FunctionTree functions, int updateInterval)
	{
		this.icon = icon;
		this.data = data;
		this.name = name;
		this.nbt = nbt;
		this.lore = lore;
		this.enchants = enchants;
		this.close = close;
		this.index = index;
		this.amount = amount;
		this.functions = functions;
		this.updateInterval = updateInterval;
		this.tick = 0;
	}
	
	public int getIndex()
	{
		return this.index;
	}
	
	public void setIndex(int index)
	{
		this.index = index;
	}
	
	public int getAmount()
	{
		return this.amount;
	}
	
	public String getIcon()
	{
		return this.icon;
	}
	
	public short getData()
	{
		return this.data;
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
		ItemStackWrapper<?> builderItem = ItemStackManager.get().createItemStackWrapper(this.icon, this.amount);
		
		if(this.getData() != 0)
			builderItem.setDurability(this.getData());
		
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
				newLore.add(newString);
			
			for(int i = 0; i < newLore.size(); i++)
			{
				String lore = ReplacerManager.get().replace(newLore.get(i), playerWrapper);
				lore = AnimationReplacerManager.get().replace(this, playerWrapper, lore);
				newLore.set(i, lore);
			}
		
			builderItem.setLore(newLore);
		}
		
		if(this.enchants != null)
		{
			for(EnchantmentWrapper ench : this.enchants)
				builderItem.addEnchant(ench);
		}
		
		if(this.nbt != null)
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
	public int tick() 
	{
		this.tick += 1;
		
		if(this.tick > 20)
			this.tick = 1;
		
		return this.tick; 
	}
}