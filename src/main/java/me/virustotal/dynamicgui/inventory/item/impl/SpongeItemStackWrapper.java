package me.virustotal.dynamicgui.inventory.item.impl;

import org.spongepowered.api.item.inventory.ItemStack;

import me.virustotal.dynamicgui.inventory.item.ItemStackWrapper;

public class SpongeItemStackWrapper<T extends ItemStack> extends ItemStackWrapper<T> {

	public SpongeItemStackWrapper(T itemStack) 
	{
		super(itemStack);
	}

	

}
