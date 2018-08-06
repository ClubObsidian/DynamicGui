package me.virustotal.dynamicgui.manager.inventory;

import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.manager.inventory.bukkit.BukkitInventoryManager;
import me.virustotal.dynamicgui.manager.inventory.sponge.SpongeInventoryManager;
import me.virustotal.dynamicgui.server.ServerType;

public abstract class InventoryManager {

	private static InventoryManager instance;
	
	public static InventoryManager get()
	{
		if(instance == null)
		{
			if(ServerType.SPIGOT == ServerType.get())
			{
				instance = new BukkitInventoryManager();
			}
			else if(ServerType.SPONGE == ServerType.get())
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
		Object inventory = createInventory(size,title);
		return createInventoryWrapper(inventory);
	}
}