package com.clubobsidian.dynamicgui.listener;

import com.clubobsidian.dynamicgui.event.inventory.InventoryCloseEvent;
import com.clubobsidian.dynamicgui.event.player.PlayerKickEvent;
import com.clubobsidian.dynamicgui.event.player.PlayerQuitEvent;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;
import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

public class InventoryCloseListener implements Listener {

	@EventHandler
	public void inventoryClose(final InventoryCloseEvent e)
	{
		if(GuiManager.get().hasGUICurrently(e.getPlayerWrapper()))
		{
			GuiManager.get().cleanupGUI(e.getPlayerWrapper());
		}
	}

	@EventHandler
	public void onQuit(final PlayerQuitEvent e)
	{
		if(GuiManager.get().hasGUICurrently(e.getPlayerWrapper()))
		{
			GuiManager.get().cleanupGUI(e.getPlayerWrapper());
		}
	}
	
	@EventHandler
	public void onKick(final PlayerKickEvent e)
	{
		if(GuiManager.get().hasGUICurrently(e.getPlayerWrapper()))
		{
			GuiManager.get().cleanupGUI(e.getPlayerWrapper());
		}
	}
}