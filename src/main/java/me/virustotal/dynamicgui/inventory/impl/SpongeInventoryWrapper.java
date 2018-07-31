package me.virustotal.dynamicgui.inventory.impl;

import org.spongepowered.api.item.inventory.Inventory;

import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.item.ItemStackWrapper;

public class SpongeInventoryWrapper<T extends Inventory> extends InventoryWrapper<T>{

	public SpongeInventoryWrapper(T inventory) 
	{
		super(inventory);
	}

	@Override
	public String getTitle() 
	{
		return this.getInventory().getName().get();
	}

	@Override
	public ItemStackWrapper<?> getItem(int index) {
		
	}

	
}
