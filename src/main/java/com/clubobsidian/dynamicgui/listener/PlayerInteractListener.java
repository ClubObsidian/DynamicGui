/*
   Copyright 2018 Club Obsidian and contributors.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.clubobsidian.dynamicgui.listener;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.event.block.PlayerInteractEvent;
import com.clubobsidian.dynamicgui.event.player.Action;
import com.clubobsidian.dynamicgui.gui.Gui;
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
			for(Gui gui : GuiManager.get().getGuis())
			{
				if(gui.getLocations() != null)
				{
					for(LocationWrapper<?> guiLocation : gui.getLocations())
					{
						if(e.getLocationWrapper().equals(guiLocation))
						{
							DynamicGui.get().getServer().getScheduler().scheduleSyncDelayedTask(DynamicGui.get().getPlugin(), new Runnable()
							{
								@Override
								public void run()
								{
									GuiManager.get().openGui(e.getPlayerWrapper(), gui);
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