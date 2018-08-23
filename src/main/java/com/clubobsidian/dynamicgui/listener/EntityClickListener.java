package com.clubobsidian.dynamicgui.listener;

import com.clubobsidian.dynamicgui.DynamicGUI;
import com.clubobsidian.dynamicgui.api.GuiApi;
import com.clubobsidian.dynamicgui.command.GUIExecutor;
import com.clubobsidian.dynamicgui.entity.EntityWrapper;
import com.clubobsidian.dynamicgui.event.inventory.PlayerInteractEntityEvent;
import com.clubobsidian.dynamicgui.gui.GUI;
import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

public class EntityClickListener implements Listener {

	@EventHandler
	public void onEntityClick(PlayerInteractEntityEvent e)
	{
		EntityWrapper<?> entityWrapper = e.getEntityWrapper();
		if(DynamicGUI.get().getPlugin().isNPC(entityWrapper))
		{
			for(GUI gui : GuiApi.getGuis())
			{
				if(gui.getNpcIds().contains(DynamicGUI.get().getPlugin().getNPC(entityWrapper).getId()))
				{
					new GUIExecutor().execute(e.getPlayerWrapper(), gui);
					break;
				}
			}
		}
	}
}