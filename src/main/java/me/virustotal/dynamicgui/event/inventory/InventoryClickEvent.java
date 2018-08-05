package me.virustotal.dynamicgui.event.inventory;


import com.clubobsidian.trident.Cancellable;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.InventoryEvent;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;

public class InventoryClickEvent extends InventoryEvent implements Cancellable {

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
	public boolean isCancelled() 
	{
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) 
	{
		this.cancelled = cancelled;
	}
}