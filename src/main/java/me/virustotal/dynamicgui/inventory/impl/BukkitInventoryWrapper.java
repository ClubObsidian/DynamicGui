package me.virustotal.dynamicgui.inventory.impl;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.item.ItemStackWrapper;
import me.virustotal.dynamicgui.inventory.item.impl.BukkitItemStackWrapper;

public class BukkitInventoryWrapper<T extends Inventory, U extends ItemStack> extends InventoryWrapper<T,U>{

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
	public ItemStackWrapper<U> getItem(int index) 
	{
		return new BukkitItemStackWrapper<ItemStack>(this.getInventory().getItem(index));
	}
}