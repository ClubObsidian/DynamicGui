package me.virustotal.dynamicgui.manager.inventory.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.bukkit.BukkitInventoryWrapper;
import me.virustotal.dynamicgui.manager.inventory.InventoryManager;

public class BukkitInventoryManager extends InventoryManager {

	@Override
	public Object createInventory(int size, String title) 
	{
		return Bukkit.getServer().createInventory(null, size, title);
	}

	@Override
	public InventoryWrapper<?> createInventoryWrapper(Object inventory) 
	{
		return new BukkitInventoryWrapper<Inventory>((Inventory) inventory);
	}
}