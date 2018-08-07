package me.virustotal.dynamicgui.listener;

import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.event.inventory.InventoryOpenEvent;

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