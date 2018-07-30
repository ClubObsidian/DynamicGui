package me.virustotal.dynamicgui.inventory.item;

public abstract class ItemStackWrapper<T> {

	private T itemStack;
	public ItemStackWrapper(T itemStack)
	{
		this.itemStack = itemStack;
	}
	
	public T getItemStack()
	{
		return this.itemStack;
	}

}
