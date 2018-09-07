/*   Copyright 2018 Club Obsidian and contributors.

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
package com.clubobsidian.dynamicgui.inventory.bukkit;

import java.io.Serializable;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;

public class BukkitInventoryWrapper<T extends Inventory> extends InventoryWrapper<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 954426075975347755L;

	public BukkitInventoryWrapper(T inventory) 
	{
		super(inventory);
	}

	@Override
	public String getTitle() 
	{
		return this.getInventory().getTitle();
	}

	@Override
	public ItemStackWrapper<ItemStack> getItem(int index) 
	{
		return new BukkitItemStackWrapper<ItemStack>(this.getInventory().getItem(index));
	}

	@Override
	public void setItem(int index, ItemStackWrapper<?> itemStackWrapper) 
	{
		this.getInventory().setItem(index, (ItemStack) itemStackWrapper.getItemStack()); 
	}
	
	@Override
	public int getSize() 
	{
		return this.getInventory().getSize();
	}
}
