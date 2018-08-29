package com.clubobsidian.dynamicgui.manager.inventory;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.manager.inventory.bukkit.BukkitItemStackManager;
import com.clubobsidian.dynamicgui.manager.inventory.sponge.SpongeItemStackManager;
import com.clubobsidian.dynamicgui.server.ServerType;

public abstract class ItemStackManager {

	private static ItemStackManager instance;
	
	public static ItemStackManager get()
	{
		if(instance == null)
		{
			if(ServerType.SPIGOT == DynamicGui.get().getServer().getType())
			{
				instance = new BukkitItemStackManager();
			}
			else if(ServerType.SPONGE == DynamicGui.get().getServer().getType())
			{
				instance = new SpongeItemStackManager();
			}
		}
		return instance;
	}
	
	public abstract Object createItemStack(String type, int quantity);
	public abstract ItemStackWrapper<?> createItemStackWrapper(Object itemStack);
	
	public ItemStackWrapper<?> createItemStackWrapper(String type, int quantity)
	{
		Object itemStack = this.createItemStack(type, quantity);
		return this.createItemStackWrapper(itemStack);
	}
}
