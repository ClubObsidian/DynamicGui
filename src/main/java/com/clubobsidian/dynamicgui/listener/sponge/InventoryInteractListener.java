/*
   Copyright 2020 Club Obsidian and contributors.

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
package com.clubobsidian.dynamicgui.listener.sponge;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.sponge.SpongePlayerWrapper;
import com.clubobsidian.dynamicgui.event.inventory.Click;
import com.clubobsidian.dynamicgui.gui.InventoryView;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.inventory.sponge.SpongeInventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.sponge.SpongeItemStackWrapper;

public class InventoryInteractListener {

	@Listener
	public void inventoryClick(ClickInventoryEvent e, @First Player player)
	{
		Click clickType = null;
		if(e instanceof ClickInventoryEvent.Primary)
		{
			clickType = Click.LEFT;
		}
		else if(e instanceof ClickInventoryEvent.Middle)
		{
			clickType = Click.MIDDLE;
		}
		else if (e instanceof ClickInventoryEvent.Secondary)
		{
			clickType = Click.RIGHT;
		}
		else if(e instanceof ClickInventoryEvent.Shift.Primary)
		{
			clickType = Click.SHIFT_LEFT;
		}
		else if(e instanceof ClickInventoryEvent.Shift.Secondary)
		{
			clickType = Click.SHIFT_RIGHT;
		}
		

		DynamicGui.get().getLogger().info("Event name: " + e.getClass().getName());
		
		DynamicGui.get().getLogger().info("Click type: " + clickType);
		List<SlotTransaction> transactions = e.getTransactions();
		if(transactions.size() > 0)
		{
			SlotTransaction transaction = transactions.get(0);
			Slot slot = transaction.getSlot().transform();
			Optional<SlotIndex> slotIndex = slot.getInventoryProperty(SlotIndex.class);
			if(slotIndex.isPresent())
			{
				
				Inventory inventory = slot.parent();
				Optional<InventoryTitle> title = inventory.getInventoryProperty(InventoryTitle.class);
				if(title.isPresent())
				{
					DynamicGui.get().getLogger().info("inventory: " + title.get().getValue().toPlain());
				}
				
				int slotIndexClicked = slotIndex.get().getValue();
				ItemStack itemStack = transaction.getOriginal().createStack();
				ItemStackWrapper<?> itemStackWrapper = null;
				if(itemStack.getType() == ItemTypes.AIR)
				{
					itemStackWrapper = new SpongeItemStackWrapper<ItemStack>(null);
				}
				else
				{
					itemStackWrapper = new SpongeItemStackWrapper<ItemStack>(itemStack);
				}
				DynamicGui.get().getLogger().info("SlotIndex: " + slotIndexClicked);
				DynamicGui.get().getLogger().info("ItemStack: " + itemStack);
				
				InventoryView view = InventoryView.TOP;
				if(slotIndexClicked >= inventory.capacity())
				{
					view = InventoryView.BOTTOM;
				}
				
				PlayerWrapper<?> playerWrapper = new SpongePlayerWrapper<Player>(player);
				InventoryWrapper<?> inventoryWrapper = new SpongeInventoryWrapper<Inventory>(inventory);
				
				com.clubobsidian.dynamicgui.event.inventory.InventoryClickEvent clickEvent = new com.clubobsidian.dynamicgui.event.inventory.InventoryClickEvent(playerWrapper, inventoryWrapper, itemStackWrapper, slotIndexClicked, clickType, view);
				DynamicGui.get().getEventBus().callEvent(clickEvent);
				if(clickEvent.isCanceled())
				{
					e.setCancelled(true);
				}
				DynamicGui.get().getLogger().info("Is trident event cancelled: " + clickEvent.isCanceled());
				DynamicGui.get().getLogger().info("Is sponge event canclled: " + e.isCancelled());
			}
		}
	}
}