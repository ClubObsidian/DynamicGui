package me.virustotal.dynamicgui.listener.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.entity.player.impl.BukkitPlayerWrapper;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.impl.BukkitInventoryWrapper;

public class InventoryCloseListener {

	@EventHandler
	public void inventoryClose(InventoryCloseEvent e)
	{
		if(e.getPlayer() instanceof Player)
		{
			PlayerWrapper<Player> playerWrapper = new BukkitPlayerWrapper<Player>((Player) e.getPlayer());
			InventoryWrapper<Inventory> inventoryWrapper = new BukkitInventoryWrapper<Inventory>(e.getInventory());
			DynamicGUI.getInstance().getEventManager().callEvent(new me.virustotal.dynamicgui.event.inventory.InventoryCloseEvent(playerWrapper, inventoryWrapper));
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		PlayerWrapper<Player> playerWrapper = new BukkitPlayerWrapper<Player>(e.getPlayer());
		DynamicGUI.getInstance().getEventManager().callEvent(new me.virustotal.dynamicgui.event.player.PlayerQuitEvent(playerWrapper));
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e)
	{
		PlayerWrapper<Player> playerWrapper = new BukkitPlayerWrapper<Player>(e.getPlayer());
		DynamicGUI.getInstance().getEventManager().callEvent(new me.virustotal.dynamicgui.event.player.PlayerKickEvent(playerWrapper));
	}
}