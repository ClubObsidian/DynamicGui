package me.virustotal.dynamicgui.event.inventory;


import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.event.InventoryEvent;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.util.inventory.InventoryClick;

public class InventoryClickEvent<T,U> extends InventoryEvent<T,U> {

	private int slot;
	private InventoryClick click;
	public InventoryClickEvent(PlayerWrapper<T> playerWrapper, InventoryWrapper<U> inventoryWrapper, int slot, InventoryClick click)
	{
		super(playerWrapper, inventoryWrapper);
		this.slot = slot;
		this.click = click;
	}
	
	public int getSlot()
	{
		return this.slot;
	}
	
	public InventoryClick getClick()
	{
		return this.click;
	}
}