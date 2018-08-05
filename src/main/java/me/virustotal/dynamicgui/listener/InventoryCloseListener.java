package me.virustotal.dynamicgui.listener;

import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.event.inventory.InventoryCloseEvent;
import me.virustotal.dynamicgui.event.player.PlayerKickEvent;
import me.virustotal.dynamicgui.event.player.PlayerQuitEvent;

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