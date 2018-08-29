package com.clubobsidian.dynamicgui.manager.inventory;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.manager.inventory.bukkit.BukkitInventoryManager;
import com.clubobsidian.dynamicgui.manager.inventory.sponge.SpongeInventoryManager;
import com.clubobsidian.dynamicgui.server.ServerType;

public abstract class InventoryManager {

	private static InventoryManager instance;
	
	public static InventoryManager get()
	{
		if(instance == null)
		{
			if(ServerType.SPIGOT == DynamicGui.get().getServer().getType())
			{
				instance = new BukkitInventoryManager();
			}
			else if(ServerType.SPONGE == DynamicGui.get().getServer().getType())
			{
				instance = new SpongeInventoryManager();
			}
		}
		return instance;
	}
	
	public abstract Object createInventory(int size, String title);
	
	public abstract InventoryWrapper<?> createInventoryWrapper(Object inventory);
	
	public InventoryWrapper<?> createInventoryWrapper(int size, String title)
	{
		Object inventory = this.createInventory(size,title);
		return this.createInventoryWrapper(inventory);
	}
}