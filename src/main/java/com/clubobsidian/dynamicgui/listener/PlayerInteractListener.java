package com.clubobsidian.dynamicgui.listener;

import com.clubobsidian.dynamicgui.DynamicGUI;
import com.clubobsidian.dynamicgui.event.block.PlayerInteractEvent;
import com.clubobsidian.dynamicgui.event.player.Action;
import com.clubobsidian.dynamicgui.gui.GUI;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.world.LocationWrapper;

import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

public class PlayerInteractListener implements Listener {
	
	@EventHandler
	public void playerInteract(final PlayerInteractEvent e)
	{
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK)
		{
			for(GUI gui : GuiManager.get().getGuis())
			{
				if(gui.getLocations() != null)
				{
					for(LocationWrapper<?> guiLocation : gui.getLocations())
					{
						if(e.getLocationWrapper().equals(guiLocation))
						{
							DynamicGUI.get().getServer().getScheduler().scheduleSyncDelayedTask(DynamicGUI.get().getPlugin(), new Runnable()
							{
								@Override
								public void run()
								{
									GuiManager.get().openGUI(e.getPlayerWrapper(), gui);
								}
							}, 1L);
							break;
						}
					}
				}
			}
		}
	}
}