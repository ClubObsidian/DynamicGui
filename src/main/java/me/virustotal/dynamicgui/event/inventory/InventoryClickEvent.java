package me.virustotal.dynamicgui.event.inventory;


import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.InventoryEvent;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;

public class InventoryClickEvent<T,U> extends InventoryEvent<T,U> {

	private int slot;
	public InventoryClickEvent(PlayerWrapper<T> playerWrapper, InventoryWrapper<U> inventoryWrapper, int slot)
	{
		super(playerWrapper, inventoryWrapper);
		this.slot = slot;
	}
	
	public int getSlot()
	{
		return this.slot;
	}
}