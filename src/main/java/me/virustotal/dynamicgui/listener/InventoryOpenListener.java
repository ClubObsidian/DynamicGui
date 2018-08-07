package me.virustotal.dynamicgui.listener;

import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.event.inventory.InventoryOpenEvent;

public class InventoryOpenListener implements Listener {

	@EventHandler
	public void inventoryOpen(final InventoryOpenEvent e)
	{
		if(DynamicGUI.get().invPlayers.contains(e.getPlayerWrapper().getName()))
		{
			if(e.getPlayerWrapper().getOpenInventoryWrapper().getInventory() != null)
				e.getPlayerWrapper().closeInventory();
			return;
		}

		if(GuiApi.hasGuiTitle(e.getInventoryWrapper().getTitle()))
		{
			DynamicGUI.get().invPlayers.add(e.getPlayerWrapper().getName());
			DynamicGUI.get().getServer().getScheduler().scheduleSyncDelayedTask(DynamicGUI.get().getPlugin(), new Runnable()
			{
				@Override
				public void run()
				{
					DynamicGUI.get().invPlayers.remove(e.getPlayerWrapper().getName());
				}
			},3L);
		}
	}
}