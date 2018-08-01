package me.virustotal.dynamicgui.event;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;

public class InventoryEvent<T,U> extends  PlayerEvent<T> {

	private InventoryWrapper<U> inventoryWrapper;
	public InventoryEvent(PlayerWrapper<T> playerWrapper, InventoryWrapper<U> inventoryWrapper)
	{
		super(playerWrapper);
		this.inventoryWrapper = inventoryWrapper;
	}

	public InventoryWrapper<U> getInventoryWrapper()
	{
		return this.inventoryWrapper;
	}
}