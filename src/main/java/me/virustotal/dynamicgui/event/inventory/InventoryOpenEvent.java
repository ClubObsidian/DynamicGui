package me.virustotal.dynamicgui.event.inventory;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.InventoryEvent;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;

public class InventoryOpenEvent extends InventoryEvent {

	public InventoryOpenEvent(PlayerWrapper<?> playerWrapper, InventoryWrapper<?> inventoryWrapper) 
	{
		super(playerWrapper, inventoryWrapper);
	}
}