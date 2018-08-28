package com.clubobsidian.dynamicgui.event.inventory;


import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.InventoryEvent;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.trident.Cancelable;

public class InventoryClickEvent extends InventoryEvent implements Cancelable {

	private int slot;
	private Click click;
	private boolean cancelled = false;
	public InventoryClickEvent(PlayerWrapper<?> playerWrapper, InventoryWrapper<?> inventoryWrapper, int slot, Click click)
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

	@Override
	public boolean isCanceled() 
	{
		return this.cancelled;
	}

	@Override
	public void setCanceled(boolean cancelled) 
	{
		this.cancelled = cancelled;
	}
}