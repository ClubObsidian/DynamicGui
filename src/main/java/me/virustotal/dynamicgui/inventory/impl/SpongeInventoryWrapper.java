package me.virustotal.dynamicgui.inventory.impl;

import java.util.Optional;

import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;

import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.item.ItemStackWrapper;
import me.virustotal.dynamicgui.inventory.item.impl.SpongeItemStackWrapper;

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
	public ItemStackWrapper<ItemStack> getItem(int index) 
	{
		Optional<SlotIndex> slotIndex = this.getInventory().getProperty(SlotIndex.class, index);
		if(slotIndex.isPresent())
		{
			ItemStack item = (ItemStack) this.getInventory().query(QueryOperationTypes.INVENTORY_PROPERTY.of(slotIndex.get())).first();
			return new SpongeItemStackWrapper<ItemStack>(item);
		}
		return new SpongeItemStackWrapper<ItemStack>(null);
	}

	@Override
	public void setItem(int index, ItemStackWrapper<?> itemStackWrapper) 
	{
		Optional<SlotIndex> slotIndex = this.getInventory().getProperty(SlotIndex.class, index);
		if(slotIndex.isPresent())
		{
			this.getInventory()
			.query(QueryOperationTypes.INVENTORY_PROPERTY.of(slotIndex.get()))
			.set((ItemStack) itemStackWrapper.getItemStack());
		}
		
	}
	
	@Override
	public int getSize() 
	{
		return this.getInventory().capacity();
	}
}