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
package com.clubobsidian.dynamicgui.inventory;

import java.io.Serializable;
import java.util.List;

import com.clubobsidian.dynamicgui.enchantment.EnchantmentWrapper;

public abstract class ItemStackWrapper<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7902733103453967016L;
	
	private T itemStack;
	public ItemStackWrapper(T itemStack)
	{
		this.itemStack = itemStack;
	}
	
	public T getItemStack()
	{
		return this.itemStack;
	}
	
	@SuppressWarnings("unchecked")
	protected void setItemStack(Object itemStack)
	{
		this.itemStack = (T) itemStack;
	}

	public abstract int getAmount();
	public abstract void setAmount(int amount);
	
	public abstract String getType();
	public abstract void setType(String type);
	
	public abstract String getName();
	public abstract void setName(String name);
	
	public abstract List<String> getLore();
	public abstract void setLore(List<String> lore);
	
	public abstract short getDurability();
	public abstract void setDurability(short durability);
	
	public abstract void addEnchant(EnchantmentWrapper enchant);
	public abstract void removeEnchant(EnchantmentWrapper enchant);
	public abstract List<EnchantmentWrapper> getEnchants();
	
	public abstract void setNBT(String nbt);
	
}