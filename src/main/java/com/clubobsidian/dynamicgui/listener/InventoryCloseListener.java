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
		GuiApi.cleanupGUI(e.getPlayerWrapper());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		GuiApi.cleanupGUI(e.getPlayerWrapper());
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e)
	{
		GuiApi.cleanupGUI(e.getPlayerWrapper());
	}
}