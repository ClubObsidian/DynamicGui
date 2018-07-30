package me.virustotal.dynamicgui.inventory;

public abstract class InventoryWrapper<I> {

	private I inventory;
	public InventoryWrapper(I inventory) 
	{
		this.inventory = inventory;
	}
	
	public I getInventory()
	{
		return this.inventory;
	}
}
