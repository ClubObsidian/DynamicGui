package com.clubobsidian.dynamicgui.event.inventory;

import java.util.Map;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.InventoryEvent;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.trident.Cancelable;

public class InventoryDragEvent extends InventoryEvent implements Cancelable {

	private Map<Integer, ItemStackWrapper<?>> slotItems;
	private boolean cancelled;
 	public InventoryDragEvent(PlayerWrapper<?> playerWrapper, InventoryWrapper<?> inventoryWrapper, Map<Integer, ItemStackWrapper<?>> slotItems)
	{
		super(playerWrapper, inventoryWrapper);
		this.slotItems = slotItems;
		this.cancelled = false;
	}

 	public Map<Integer, ItemStackWrapper<?>> getSlotItems()
 	{
 		return this.slotItems;
 	}
 	
	@Override
	public boolean isCanceled() 
	{
		return this.cancelled;
	}

	@Override
	public void setCanceled(boolean cancel) 
	{
		this.cancelled = cancel;
	}
}