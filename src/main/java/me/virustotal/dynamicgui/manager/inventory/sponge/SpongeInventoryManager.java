package me.virustotal.dynamicgui.manager.inventory.sponge;

import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.sponge.SpongeInventoryWrapper;
import me.virustotal.dynamicgui.manager.inventory.InventoryManager;

public class SpongeInventoryManager extends InventoryManager {

	@Override
	public Object createInventory(int size, String title) 
	{
		return Inventory.builder().property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.of(title)))
		.property(InventoryDimension.PROPERTY_NAME, new InventoryDimension(9, size / 9))
		.build(DynamicGUI.get().getPlugin());
	}

	@Override
	public InventoryWrapper<?> createInventoryWrapper(Object inventory) 
	{
		return new SpongeInventoryWrapper<Inventory>((Inventory) inventory);
	}
}