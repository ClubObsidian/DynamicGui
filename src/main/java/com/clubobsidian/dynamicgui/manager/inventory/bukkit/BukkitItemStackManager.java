package com.clubobsidian.dynamicgui.manager.inventory.bukkit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.inventory.bukkit.BukkitItemStackWrapper;
import com.clubobsidian.dynamicgui.manager.inventory.ItemStackManager;

public class BukkitItemStackManager extends ItemStackManager {

	@Override
	public Object createItemStack(String type, int quantity) 
	{
		return new ItemStack(Material.valueOf(type), quantity);
	}

	@Override
	public ItemStackWrapper<?> createItemStackWrapper(Object itemStack) 
	{
		if(itemStack == null)
		{
			return new BukkitItemStackWrapper<ItemStack>(null);
		}
		return new BukkitItemStackWrapper<ItemStack>((ItemStack) itemStack);
	}
}