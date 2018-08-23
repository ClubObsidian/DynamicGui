package com.clubobsidian.dynamicgui.listener;

import com.clubobsidian.dynamicgui.api.GuiApi;
import com.clubobsidian.dynamicgui.event.inventory.InventoryOpenEvent;
import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

public class InventoryOpenListener implements Listener {

	@EventHandler
	public void inventoryOpen(final InventoryOpenEvent e)
	{
		if(GuiApi.hasGUICurrently(e.getPlayerWrapper()))
		{
			if(e.getPlayerWrapper().getOpenInventoryWrapper().getInventory() != null)
				e.getPlayerWrapper().closeInventory();
			return;
		}
	}
}