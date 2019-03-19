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
package com.clubobsidian.dynamicgui.listener.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.bukkit.BukkitPlayerWrapper;
import com.clubobsidian.dynamicgui.event.inventory.Click;
import com.clubobsidian.dynamicgui.gui.InventoryView;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.inventory.bukkit.BukkitInventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.bukkit.BukkitItemStackWrapper;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		Inventory inventory = e.getInventory();
		if(inventory == null)
			return;
		
		int slot = e.getSlot();
		int rawSlot = e.getRawSlot();
		InventoryView view = InventoryView.TOP;
		if(rawSlot >= e.getInventory().getSize())
		{
			view = InventoryView.BOTTOM;
		}
		
		if(e.getWhoClicked() instanceof Player)
		{
			Click clickType = null;
			if(e.getClick() == ClickType.LEFT || e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.MIDDLE)
			{
				clickType = Click.valueOf(e.getClick().toString());
			}
			
			Player player = (Player) e.getWhoClicked();
			InventoryWrapper<?> inventoryWrapper = new BukkitInventoryWrapper<Inventory>(inventory);
			PlayerWrapper<?> playerWrapper = new BukkitPlayerWrapper<Player>(player);
			ItemStack itemStack = null;
			if(slot >= 0 && slot < inventory.getSize())
			{
				itemStack = e.getInventory().getItem(slot);
			}
			
			ItemStackWrapper<?> itemStackWrapper = null;
			if(itemStack == null)
			{
				itemStackWrapper = new BukkitItemStackWrapper<ItemStack>(null);
			}
			else
			{
				itemStackWrapper = new BukkitItemStackWrapper<ItemStack>(itemStack);
			}
			
			com.clubobsidian.dynamicgui.event.inventory.InventoryClickEvent clickEvent = new com.clubobsidian.dynamicgui.event.inventory.InventoryClickEvent(playerWrapper, inventoryWrapper, itemStackWrapper, slot, clickType, view);
			DynamicGui.get().getEventManager().callEvent(clickEvent);
			if(clickEvent.isCanceled())
			{
				e.setCancelled(true);
			}
		}
	}	
}