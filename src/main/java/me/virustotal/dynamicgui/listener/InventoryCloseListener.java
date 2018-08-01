package me.virustotal.dynamicgui.listener;

import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.event.inventory.InventoryCloseEvent;
import me.virustotal.dynamicgui.event.player.PlayerKickEvent;
import me.virustotal.dynamicgui.event.player.PlayerQuitEvent;
import me.virustotal.dynamicgui.gui.GUI;


public class InventoryCloseListener<T,U> implements Listener {
	
	private DynamicGUI<T,U> plugin;
	public InventoryCloseListener(DynamicGUI<T,U> plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler
	public void inventoryClose(final InventoryCloseEvent<T,U> e)
	{
		if(this.plugin.playerGuis.keySet().contains(e.getPlayerWrapper().getName()))
		{
			GUI gui = this.plugin.playerGuis.get(e.getPlayerWrapper().getName());
			if(gui != null)
			{
				if(gui.getTitle().equals(e.getInventoryWrapper().getTitle()))
				{
					this.plugin.playerGuis.remove(e.getPlayerWrapper().getName());
				}
			}
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent<T> e)
	{
		this.plugin.playerGuis.remove(e.getPlayerWrapper().getName());
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent<T> e)
	{
		this.plugin.playerGuis.remove(e.getPlayerWrapper().getName());
	}
}