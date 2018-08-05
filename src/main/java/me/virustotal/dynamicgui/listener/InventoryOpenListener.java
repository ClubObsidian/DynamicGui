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
		if(DynamicGUI.getInstance().invPlayers.contains(e.getPlayerWrapper().getName()))
		{
			if(e.getPlayerWrapper().getOpenInventoryWrapper().getInventory() != null)
				e.getPlayerWrapper().closeInventory();
			return;
		}

		if(GuiApi.hasGuiTitle(e.getInventoryWrapper().getTitle()))
		{
			DynamicGUI.getInstance().invPlayers.add(e.getPlayerWrapper().getName());
			DynamicGUI.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(DynamicGUI.getInstance().getPlugin(), new Runnable()
			{
				@Override
				public void run()
				{
					DynamicGUI.getInstance().invPlayers.remove(e.getPlayerWrapper().getName());
				}
			},3L);
		}
	}
}