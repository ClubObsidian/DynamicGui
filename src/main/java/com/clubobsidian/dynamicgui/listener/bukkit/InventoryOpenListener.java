package com.clubobsidian.dynamicgui.listener.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import com.clubobsidian.dynamicgui.DynamicGUI;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.bukkit.BukkitPlayerWrapper;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.bukkit.BukkitInventoryWrapper;

public class InventoryOpenListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void inventoryOpen(InventoryOpenEvent e)
	{
		if(e.getPlayer() instanceof Player)
		{
			Player player = (Player) e.getPlayer();
			PlayerWrapper<?> playerWrapper = new BukkitPlayerWrapper<Player>(player);
			InventoryWrapper<?> inventoryWrapper = new BukkitInventoryWrapper<Inventory>(e.getInventory());
			com.clubobsidian.dynamicgui.event.inventory.InventoryOpenEvent inventoryOpenEvent = new com.clubobsidian.dynamicgui.event.inventory.InventoryOpenEvent(playerWrapper, inventoryWrapper);
			DynamicGUI.get().getEventManager().callEvent(inventoryOpenEvent);
		}
	}
}