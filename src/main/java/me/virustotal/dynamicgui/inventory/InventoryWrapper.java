package me.virustotal.dynamicgui.inventory;

import java.io.Serializable;

import me.virustotal.dynamicgui.inventory.item.ItemStackWrapper;

public abstract class InventoryWrapper<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1039529042564261223L;
	
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
	public abstract ItemStackWrapper<?> getItem(int index);
	public abstract void addItem(ItemStackWrapper<?> itemStackWrapper);
	public abstract void setItem(int index, ItemStackWrapper<?> itemStackWrapper);
	public abstract int getSize();
}
