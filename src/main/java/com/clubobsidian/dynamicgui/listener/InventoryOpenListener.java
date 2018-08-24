package com.clubobsidian.dynamicgui.listener;

import com.clubobsidian.dynamicgui.event.inventory.InventoryOpenEvent;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;
import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

public class InventoryOpenListener implements Listener {

	@EventHandler
	public void inventoryOpen(final InventoryOpenEvent e)
	{
		if(GuiManager.get().hasGUICurrently(e.getPlayerWrapper()))
		{
			if(e.getPlayerWrapper().getOpenInventoryWrapper().getInventory() != null)
				e.getPlayerWrapper().closeInventory();
		}
	}
}