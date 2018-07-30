package me.virustotal.dynamicgui.listener;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.gui.GUI;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InventoryCloseListener implements Listener {
	
	private DynamicGUI plugin;
	public InventoryCloseListener(DynamicGUI plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void inventoryClose(InventoryCloseEvent e)
	{
		if(this.plugin.playerGuis.keySet().contains(e.getPlayer().getName()))
		{
			GUI gui = this.plugin.playerGuis.get(e.getPlayer().getName());
			if(gui != null)
			{
				if(gui.getTitle().equals(e.getInventory().getTitle()))
				{
					this.plugin.playerGuis.remove(e.getPlayer().getName());
				}
			}
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		if(this.plugin.playerGuis.keySet().contains(e.getPlayer().getName()))
		{
			GUI gui = this.plugin.playerGuis.get(e.getPlayer().getName());
			if(gui != null)
			{
				this.plugin.playerGuis.remove(e.getPlayer().getName());
			}
		}
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e)
	{
		if(this.plugin.playerGuis.keySet().contains(e.getPlayer().getName()))
		{
			GUI gui = this.plugin.playerGuis.get(e.getPlayer().getName());
			if(gui != null)
			{
				this.plugin.playerGuis.remove(e.getPlayer().getName());
			}
		}
	}
}