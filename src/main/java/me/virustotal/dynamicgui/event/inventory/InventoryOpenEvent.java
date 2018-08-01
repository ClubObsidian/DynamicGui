package me.virustotal.dynamicgui.event.inventory;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.InventoryEvent;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;

public class InventoryOpenEvent<T,U> extends InventoryEvent<T,U> {

	public InventoryOpenEvent(PlayerWrapper<T> playerWrapper, InventoryWrapper<U> inventoryWrapper) 
	{
		super(playerWrapper, inventoryWrapper);
	}
}