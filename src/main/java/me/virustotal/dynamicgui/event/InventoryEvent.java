package me.virustotal.dynamicgui.event;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;

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