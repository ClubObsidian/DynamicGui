package me.virustotal.dynamicgui.inventory.impl;

import java.io.Serializable;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.item.ItemStackWrapper;
import me.virustotal.dynamicgui.inventory.item.impl.BukkitItemStackWrapper;

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
	public void addItem(ItemStackWrapper<?> itemStackWrapper) 
	{
		this.getInventory().addItem((ItemStack) itemStackWrapper.getItemStack());
	}

	@Override
	public void setItem(int index, ItemStackWrapper<?> itemStackWrapper) 
	{
		this.getInventory().setItem(index, (ItemStack) itemStackWrapper.getItemStack()); 
	}
	
	@Override
	public int getSize() 
	{
		return this.getInventory().getSize();
	}
}