package me.virustotal.dynamicgui.manager.inventory;

import me.virustotal.dynamicgui.DynamicGUI;
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
			if(ServerType.SPIGOT == DynamicGUI.getInstance().getServer().getType())
			{
				instance = new BukkitInventoryManager();
			}
			else if(ServerType.SPONGE == DynamicGUI.getInstance().getServer().getType())
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