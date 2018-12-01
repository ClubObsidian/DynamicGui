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
package com.clubobsidian.dynamicgui.event.inventory;


import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.InventoryEvent;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.trident.Cancelable;

public class InventoryClickEvent extends InventoryEvent implements Cancelable {

	private int slot;
	private Click click;
	private boolean cancelled = false;
	public InventoryClickEvent(PlayerWrapper<?> playerWrapper, InventoryWrapper<?> inventoryWrapper, int slot, Click click)
	{
		super(playerWrapper, inventoryWrapper);
		this.slot = slot;
		this.click = click;
	}
	
	public int getSlot()
	{
		return this.slot;
	}
	
	public Click getClick()
	{
		return this.click;
	}

	@Override
	public boolean isCanceled() 
	{
		return this.cancelled;
	}

	@Override
	public void setCanceled(boolean cancelled) 
	{
		this.cancelled = cancelled;
	}
}