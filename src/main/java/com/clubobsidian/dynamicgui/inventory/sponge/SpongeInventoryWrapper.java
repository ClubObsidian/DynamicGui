package com.clubobsidian.dynamicgui.inventory.sponge;

import java.util.Optional;

import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;

public class SpongeInventoryWrapper<T extends Inventory> extends InventoryWrapper<T>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4524275635001827647L;

	public SpongeInventoryWrapper(T inventory) 
	{
		super(inventory);
	}

	@Override
	public String getTitle() 
	{
		return this.getInventory().getInventoryProperty(InventoryTitle.class).get().getValue().toPlain();
	}

	@Override
	public ItemStackWrapper<ItemStack> getItem(int index) 
	{
		Optional<ItemStack> item = this.getInventory()
		.query(QueryOperationTypes.INVENTORY_PROPERTY
		.of(SlotIndex.of(index))).peek();
		
		if(item.isPresent())
		{
			return new SpongeItemStackWrapper<ItemStack>(item.get());
		}
		DynamicGui.get().getLogger().info("Item is not present for get item");
		return new SpongeItemStackWrapper<ItemStack>(null);
	}
	
	@Override
	public void setItem(int index, ItemStackWrapper<?> itemStackWrapper) 
	{
		ItemStack itemStack = (ItemStack) itemStackWrapper.getItemStack();
		DynamicGui.get().getLogger().info("Set itemstack is null: " + itemStack);
		this.getInventory()
		.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(index)))
		.set(itemStack);
		DynamicGui.get().getLogger().info("Inventory " + index + " set get after: " + this.getItem(index).getItemStack());
	}
	
	@Override
	public int getSize() 
	{
		return this.getInventory().capacity();
	}
}