package com.clubobsidian.dynamicgui.event;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;

public class InventoryEvent extends PlayerEvent {

	private InventoryWrapper<?> inventoryWrapper;
	public InventoryEvent(PlayerWrapper<?> playerWrapper, InventoryWrapper<?> inventoryWrapper)
	{
		super(playerWrapper);
		this.inventoryWrapper = inventoryWrapper;
	}

	public InventoryWrapper<?> getInventoryWrapper()
	{
		return this.inventoryWrapper;
	}
}