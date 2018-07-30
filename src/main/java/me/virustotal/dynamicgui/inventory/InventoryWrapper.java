package me.virustotal.dynamicgui.inventory;

import me.virustotal.dynamicgui.inventory.item.ItemStackWrapper;

public abstract class InventoryWrapper<T,U> {

	private T inventory;
	public InventoryWrapper(T inventory) 
	{
		this.inventory = inventory;
	}
	
	public T getInventory()
	{
		return this.inventory;
	}
	
	public abstract String getTitle();
	public abstract ItemStackWrapper<U> getItem(int index);
}
