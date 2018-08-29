package com.clubobsidian.dynamicgui.listener;

import com.clubobsidian.dynamicgui.DynamicGui2;
import com.clubobsidian.dynamicgui.entity.EntityWrapper;
import com.clubobsidian.dynamicgui.event.inventory.PlayerInteractEntityEvent;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;
import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

public class EntityClickListener implements Listener {

	@EventHandler
	public void onEntityClick(PlayerInteractEntityEvent e)
	{
		EntityWrapper<?> entityWrapper = e.getEntityWrapper();
		if(DynamicGui2.get().getPlugin().isNPC(entityWrapper))
		{
			for(Gui gui : GuiManager.get().getGuis())
			{
				if(gui.getNpcIds().contains(DynamicGui2.get().getPlugin().getNPC(entityWrapper).getId()))
				{
					GuiManager.get().openGui(e.getPlayerWrapper(), gui);
					break;
				}
			}
		}
	}
}