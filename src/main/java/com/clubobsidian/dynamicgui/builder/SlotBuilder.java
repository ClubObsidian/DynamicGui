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
package com.clubobsidian.dynamicgui.builder;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import com.clubobsidian.dynamicgui.enchantment.EnchantmentWrapper;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.parser.function.tree.FunctionTree;

public class SlotBuilder {
	
	private String icon;
	private String name;
	private String nbt;
	private short data;
	private Boolean close;
	private List<String> lore;
	private List<EnchantmentWrapper> enchants;
	private int index;
	private int amount;
	private int updateInterval;
	private FunctionTree functionTree;
	public SlotBuilder()
	{
		this.enchants = new ArrayList<>();
		this.amount = 1;
		this.updateInterval = 0;
		this.functionTree = new FunctionTree();
	}
	
	public SlotBuilder setIcon(Material icon)
	{
		this.icon = icon.name();
		return this;
	}
	
	public SlotBuilder setIcon(String icon)
    {
    	this.icon = icon;
    	return this;
    }
	
	public SlotBuilder setName(String name)
	{
		this.name = name;
		return this;
	}
	
	public SlotBuilder setNBT(String nbt)
	{
		this.nbt = nbt;
		return this;
	}
	
	public SlotBuilder setData(int data)
	{
		this.setData((short) data);
		return this;
	}
	
	public SlotBuilder setData(short data)
	{
		this.data = data;
		return this;
	}
	
	public SlotBuilder setClose(Boolean close)
	{
		this.close = close;
		return this;
	}
	
	public SlotBuilder setIndex(int index)
	{
		this.index = index;
		return this;
	}
	
	public SlotBuilder setAmount(int amount)
	{
		this.amount = amount;
		return this;
	}
	
	public SlotBuilder setUpdateInterval(int interval)
	{
		this.updateInterval = interval;
		return this;
	}
	
	public SlotBuilder addLore(String lore)
	{
		if(this.lore == null)
		{
			this.lore = new ArrayList<String>();
			this.lore.add(lore);
		}
		else
		{
			this.lore.add(lore);
		}
		return this;
	}
	
	public SlotBuilder addLore(String... lore)
	{
		for(String l : lore)
		{
			this.addLore(l);
		}
		
		return this;
	}
	
	public SlotBuilder addEnchant(EnchantmentWrapper enchant)
	{
		this.enchants.add(enchant);
		return this;
	}
	
	public SlotBuilder setFunctionTree(FunctionTree functionTree)
	{
		this.functionTree = functionTree;
		return this;
	}
	
	public Slot build()
	{
		return new Slot(this.index, this.amount,this.icon, this.name, this.nbt, this.data, this.close, this.lore, this.enchants, this.functionTree, this.updateInterval);
	}
}