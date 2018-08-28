package com.clubobsidian.dynamicgui.event.inventory;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.InventoryEvent;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;

public class InventoryOpenEvent extends InventoryEvent {

	public InventoryOpenEvent(PlayerWrapper<?> playerWrapper, InventoryWrapper<?> inventoryWrapper) 
	{
		super(playerWrapper, inventoryWrapper);
	}
}