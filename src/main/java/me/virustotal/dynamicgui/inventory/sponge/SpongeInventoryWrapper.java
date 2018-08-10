package me.virustotal.dynamicgui.inventory.sponge;

import java.util.Optional;

import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.query.QueryOperationTypes;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.ItemStackWrapper;

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
		DynamicGUI.get().getLogger().info("Index is: " + index);
		Optional<ItemStack> item = this.getInventory().query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(index))).peek();
		
		if(item.isPresent())
		{
			return new SpongeItemStackWrapper<ItemStack>(item.get());
		}
		return new SpongeItemStackWrapper<ItemStack>(null);
	}
	
	@Override
	public void setItem(int index, ItemStackWrapper<?> itemStackWrapper) 
	{
		InventoryTransactionResult result = this.getInventory()
		.query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(index)))
		.set((ItemStack) itemStackWrapper.getItemStack());
		DynamicGUI.get().getLogger().info("Inventory set result: " + result.getType());
	}
	
	@Override
	public int getSize() 
	{
		return this.getInventory().capacity();
	}
}