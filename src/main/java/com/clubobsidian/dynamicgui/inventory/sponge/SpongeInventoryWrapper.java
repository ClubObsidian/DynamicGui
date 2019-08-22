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
package com.clubobsidian.dynamicgui.inventory.sponge;

import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;

public class SpongeInventoryWrapper<T extends Inventory> extends InventoryWrapper<T>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4524275635001827647L;

	public SpongeInventoryWrapper(T inventory) 
	{
		super(inventory);
	}

	@Override
	public ItemStackWrapper<ItemStack> getItem(int index) 
	{
		int i = 0;
		Iterator<Inventory> inv = this.getInventory().iterator();
		while(inv.hasNext())
		{
			Inventory next = inv.next();
			DynamicGui.get().getLogger().info("i: " + i + "   " + next.toString());
			i += 1;
		}
		Optional<ItemStack> item = this.getInventory()
		.query(QueryOperationTypes.INVENTORY_PROPERTY
		.of(SlotIndex.of(index))).first().peek();
		
		if(item.isPresent())
		{
			return new SpongeItemStackWrapper<ItemStack>(item.get());
		}
		DynamicGui.get().getLogger().info("Item is not present for get item");
		return new SpongeItemStackWrapper<ItemStack>(null);
	}
	
	@Override
	public void setItem(int index, ItemStackWrapper<?> itemStackWrapper) 
	{
		Runnable setRunnable = () ->
		{
			ItemStack itemStack = (ItemStack) itemStackWrapper.getItemStack();
			DynamicGui.get().getLogger().info("Set itemstack is null: " + itemStack);
			this.getInventory()
			.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(index)))
			.set(itemStack);
			DynamicGui.get().getLogger().info("Inventory " + index + " set get after: " + this.getItem(index).getItemStack());
		};
		setRunnable.run();
		Sponge.getScheduler().createTaskBuilder().execute(setRunnable).delay(1, TimeUnit.MILLISECONDS).submit(DynamicGui.get().getPlugin());
	}

	@Override
	public int getSize() 
	{
		return this.getInventory().capacity();
	}
	
	@Override
	public int getContentSize() 
	{
		return this.getInventory().size();
	}

	@Override
	public void setTitle(PlayerWrapper<?> playerWrapper, String title) 
	{
		// TODO Auto-generated method stub
	}
}