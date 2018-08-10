package me.virustotal.dynamicgui.manager.inventory.bukkit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.virustotal.dynamicgui.inventory.ItemStackWrapper;
import me.virustotal.dynamicgui.inventory.bukkit.BukkitItemStackWrapper;
import me.virustotal.dynamicgui.manager.inventory.ItemStackManager;

public class BukkitItemStackManager extends ItemStackManager {

	@Override
	public Object createItemStack(String type, int quantity) 
	{
		return new ItemStack(Material.valueOf(type), quantity);
	}

	@Override
	public ItemStackWrapper<?> createItemStackWrapper(Object itemStack) 
	{
		if(itemStack == null)
		{
			return new BukkitItemStackWrapper<ItemStack>(null);
		}
		return new BukkitItemStackWrapper<ItemStack>((ItemStack) itemStack);
	}
}