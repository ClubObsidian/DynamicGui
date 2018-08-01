package me.virustotal.dynamicgui.util.inventory;

import me.virustotal.dynamicgui.inventory.InventoryWrapper;

public final class InventoryUtil {

	public static Object createInventory(int size, String title)
	{
		
	}
	
	public static InventoryWrapper<?> createInventoryWrapper(Object inventory)
	{
		
	}
	
	public static InventoryWrapper<?> createInventoryWrapper(int size, String title)
	{
		Object inventory = createInventory(size,title);
		return createInventoryWrapper(inventory);
	}
	
}
