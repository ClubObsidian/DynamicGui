package com.clubobsidian.dynamicgui.inventory.bukkit;

import java.io.Serializable;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;

public class BukkitInventoryWrapper<T extends Inventory> extends InventoryWrapper<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 954426075975347755L;

	public BukkitInventoryWrapper(T inventory) 
	{
		super(inventory);
	}

	@Override
	public String getTitle() 
	{
		return this.getInventory().getTitle();
	}

	@Override
	public ItemStackWrapper<ItemStack> getItem(int index) 
	{
		return new BukkitItemStackWrapper<ItemStack>(this.getInventory().getItem(index));
	}

	@Override
	public void setItem(int index, ItemStackWrapper<?> itemStackWrapper) 
	{
		System.out.println("Set item at index " + index + " for item stack wrapper " + itemStackWrapper.toString());
		this.getInventory().setItem(index, (ItemStack) itemStackWrapper.getItemStack()); 
	}
	
	@Override
	public int getSize() 
	{
		return this.getInventory().getSize();
	}
}