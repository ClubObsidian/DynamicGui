package me.virustotal.dynamicgui.manager.inventory;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.inventory.ItemStackWrapper;
import me.virustotal.dynamicgui.manager.inventory.bukkit.BukkitItemStackManager;
import me.virustotal.dynamicgui.manager.inventory.sponge.SpongeItemStackManager;
import me.virustotal.dynamicgui.server.ServerType;

public abstract class ItemStackManager {

	private static ItemStackManager instance;
	
	public static ItemStackManager get()
	{
		if(instance == null)
		{
			if(ServerType.SPIGOT == DynamicGUI.getInstance().getServer().getType())
			{
				instance = new BukkitItemStackManager();
			}
			else if(ServerType.SPONGE == DynamicGUI.getInstance().getServer().getType())
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
