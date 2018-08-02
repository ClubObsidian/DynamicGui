package me.virustotal.dynamicgui.listener;

import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.event.inventory.InventoryCloseEvent;
import me.virustotal.dynamicgui.event.player.PlayerKickEvent;
import me.virustotal.dynamicgui.event.player.PlayerQuitEvent;

public class InventoryCloseListener<T,U> implements Listener {

	@EventHandler
	public void inventoryClose(final InventoryCloseEvent<T,U> e)
	{
		GuiApi.cleanupGUI(e.getPlayerWrapper());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent<T> e)
	{
		GuiApi.cleanupGUI(e.getPlayerWrapper());
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent<T> e)
	{
		GuiApi.cleanupGUI(e.getPlayerWrapper());
	}
}