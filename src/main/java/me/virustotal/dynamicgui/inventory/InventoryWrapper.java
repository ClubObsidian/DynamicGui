package me.virustotal.dynamicgui.inventory;

import java.io.Serializable;

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
	public abstract void setItem(int index, ItemStackWrapper<?> itemStackWrapper);
	public abstract int getSize();
	
	public int addItem(ItemStackWrapper<?> itemStackWrapper) 
	{
		for(int i = 0; i < this.getSize(); i++)
		{
			if(this.getItem(i).getItemStack() == null)
			{
				this.setItem(i, itemStackWrapper);
				return i;
			}
		}
		return -1;
	}
}
