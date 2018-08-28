package com.clubobsidian.dynamicgui.listener.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import com.clubobsidian.dynamicgui.DynamicGUI;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.entity.bukkit.BukkitPlayerWrapper;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.bukkit.BukkitInventoryWrapper;

public class InventoryCloseListener implements Listener {

	@EventHandler
	public void inventoryClose(InventoryCloseEvent e)
	{
		if(e.getPlayer() instanceof Player)
		{
			PlayerWrapper<Player> playerWrapper = new BukkitPlayerWrapper<Player>((Player) e.getPlayer());
			InventoryWrapper<Inventory> inventoryWrapper = new BukkitInventoryWrapper<Inventory>(e.getInventory());
			DynamicGUI.get().getEventManager().callEvent(new com.clubobsidian.dynamicgui.event.inventory.InventoryCloseEvent(playerWrapper, inventoryWrapper));
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		PlayerWrapper<Player> playerWrapper = new BukkitPlayerWrapper<Player>(e.getPlayer());
		DynamicGUI.get().getEventManager().callEvent(new com.clubobsidian.dynamicgui.event.player.PlayerQuitEvent(playerWrapper));
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e)
	{
		PlayerWrapper<Player> playerWrapper = new BukkitPlayerWrapper<Player>(e.getPlayer());
		DynamicGUI.get().getEventManager().callEvent(new com.clubobsidian.dynamicgui.event.player.PlayerKickEvent(playerWrapper));
	}
}