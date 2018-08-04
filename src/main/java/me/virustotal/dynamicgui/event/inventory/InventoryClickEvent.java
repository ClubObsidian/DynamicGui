package me.virustotal.dynamicgui.event.inventory;


import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.InventoryEvent;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;

public class InventoryClickEvent<T,U> extends InventoryEvent<T,U> {

	private int slot;
	private Click click;
	public InventoryClickEvent(PlayerWrapper<T> playerWrapper, InventoryWrapper<U> inventoryWrapper, int slot, Click click)
	{
		super(playerWrapper, inventoryWrapper);
		this.slot = slot;
		this.click = click;
	}
	
	public int getSlot()
	{
		return this.slot;
	}
	
	public Click getClick()
	{
		return this.click;
	}
}