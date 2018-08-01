package me.virustotal.dynamicgui.inventory.item.impl;

import org.bukkit.inventory.ItemStack;

import me.virustotal.dynamicgui.inventory.item.ItemStackWrapper;

public class BukkitItemStackWrapper<T extends ItemStack> extends ItemStackWrapper<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3542885060265738780L;

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