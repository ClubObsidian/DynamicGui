package me.virustotal.dynamicgui.inventory.item.impl;

import org.bukkit.inventory.ItemStack;

import me.virustotal.dynamicgui.inventory.item.ItemStackWrapper;

public class BukkitItemStackWrapper<T extends ItemStack> extends ItemStackWrapper<T> {

	public BukkitItemStackWrapper(T itemStack) 
	{
		super(itemStack);
	}

	@Override
	public String getType() 
	{
		return this.getItemStack().getType().toString();
	}
}