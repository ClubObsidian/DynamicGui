package me.virustotal.dynamicgui.event.inventory;


import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.PlayerEvent;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;

public class InventoryClickEvent<T,U> extends PlayerEvent<T> {

	private InventoryWrapper<U> inventoryWrapper;
	public InventoryClickEvent(PlayerWrapper<T> playerWrapper, InventoryWrapper<U> inventoryWrapper)
	{
		super(playerWrapper);
		this.inventoryWrapper = inventoryWrapper;
	}

	public InventoryWrapper<U> getInventoryWrapper()
	{
		return this.inventoryWrapper;
	}
}