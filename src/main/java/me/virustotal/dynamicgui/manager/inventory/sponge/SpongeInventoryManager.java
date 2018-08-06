package me.virustotal.dynamicgui.manager.inventory.sponge;

import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.property.InventoryCapacity;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.impl.SpongeInventoryWrapper;
import me.virustotal.dynamicgui.manager.inventory.InventoryManager;

public class SpongeInventoryManager extends InventoryManager {

	@Override
	public Object createInventory(int size, String title) 
	{
		return Inventory.builder().property("title", new InventoryTitle(Text.of(title)))
		.property("capacity", new InventoryCapacity(size))
		.build(DynamicGUI.getInstance().getPlugin());
	}

	@Override
	public InventoryWrapper<?> createInventoryWrapper(Object inventory) 
	{
		return new SpongeInventoryWrapper<Inventory>((Inventory) inventory);
	}
}