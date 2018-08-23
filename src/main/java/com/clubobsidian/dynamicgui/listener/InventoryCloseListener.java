package com.clubobsidian.dynamicgui.listener;

import com.clubobsidian.dynamicgui.api.GuiApi;
import com.clubobsidian.dynamicgui.event.inventory.InventoryCloseEvent;
import com.clubobsidian.dynamicgui.event.player.PlayerKickEvent;
import com.clubobsidian.dynamicgui.event.player.PlayerQuitEvent;

import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

public class InventoryCloseListener implements Listener {

	@EventHandler
	public void inventoryClose(final InventoryCloseEvent e)
	{
		if(GuiApi.hasGUICurrently(e.getPlayerWrapper()))
		{
			GuiApi.cleanupGUI(e.getPlayerWrapper());
		}
	}

	@EventHandler
	public void onQuit(final PlayerQuitEvent e)
	{
		if(GuiApi.hasGUICurrently(e.getPlayerWrapper()))
		{
			GuiApi.cleanupGUI(e.getPlayerWrapper());
		}
	}
	
	@EventHandler
	public void onKick(final PlayerKickEvent e)
	{
		if(GuiApi.hasGUICurrently(e.getPlayerWrapper()))
		{
			GuiApi.cleanupGUI(e.getPlayerWrapper());
		}
	}
}