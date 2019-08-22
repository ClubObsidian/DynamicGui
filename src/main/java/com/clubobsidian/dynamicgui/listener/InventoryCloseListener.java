/*
   Copyright 2019 Club Obsidian and contributors.

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
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.inventory.InventoryCloseEvent;
import com.clubobsidian.dynamicgui.event.player.PlayerKickEvent;
import com.clubobsidian.dynamicgui.event.player.PlayerQuitEvent;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;
import com.clubobsidian.trident.EventHandler;

public class InventoryCloseListener {

	@EventHandler
	public void inventoryClose(final InventoryCloseEvent e)
	{
		PlayerWrapper<?> playerWrapper = e.getPlayerWrapper();
		Gui gui = GuiManager.get().getCurrentGui(playerWrapper);
		if(gui != null)
		{

			//Cleanup active gui
			GuiManager.get().cleanupGui(e.getPlayerWrapper());

			DynamicGui.get().getServer().getScheduler().scheduleSyncDelayedTask(DynamicGui.get().getPlugin(), () -> 
			{
				System.out.println("close");
				Gui newGui = GuiManager.get().getCurrentGui(playerWrapper);
				if(newGui == null)
				{
					playerWrapper.closeInventory();
				}
			},  1L);
		}
	}

	@EventHandler
	public void onQuit(final PlayerQuitEvent e)
{
		if(GuiManager.get().hasGuiCurrently(e.getPlayerWrapper()))
		{
			GuiManager.get().cleanupGui(e.getPlayerWrapper());
		}
	}
	
	@EventHandler
	public void onKick(final PlayerKickEvent e)
	{
		if(GuiManager.get().hasGuiCurrently(e.getPlayerWrapper()))
		{
			GuiManager.get().cleanupGui(e.getPlayerWrapper());
		}
	}
}