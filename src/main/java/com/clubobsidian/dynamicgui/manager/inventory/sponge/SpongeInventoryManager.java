package com.clubobsidian.dynamicgui.manager.inventory.sponge;

import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.sponge.SpongeInventoryWrapper;
import com.clubobsidian.dynamicgui.manager.inventory.InventoryManager;

public class SpongeInventoryManager extends InventoryManager {

	@Override
	public Object createInventory(int size, String title) 
	{
		return Inventory.builder()
				.of(InventoryArchetypes.DOUBLE_CHEST)
				.property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.of(title)))
				.property(InventoryDimension.PROPERTY_NAME, new InventoryDimension(9, size / 9))
				.build(DynamicGui.get().getPlugin());
	}

	@Override
	public InventoryWrapper<?> createInventoryWrapper(Object inventory) 
	{
		return new SpongeInventoryWrapper<Inventory>((Inventory) inventory);
	}
}