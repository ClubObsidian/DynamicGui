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

import java.util.List;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.event.inventory.InventoryClickEvent;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.util.FunctionUtil;

import com.clubobsidian.trident.EventHandler;
import com.clubobsidian.trident.Listener;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void invClick(final InventoryClickEvent e)
	{
		if(e.getPlayerWrapper().getOpenInventoryWrapper() == null)
		{
			return;
		}
		
		if(!GuiManager.get().hasGuiCurrently(e.getPlayerWrapper()))
		{
			return;
		}
		
		e.setCanceled(true);
		
		ItemStackWrapper<?> item = e.getInventoryWrapper().getItem(e.getSlot());
		if(item.getItemStack() == null)
		{
			return;
		}

		if(e.getClick() == null) //For other types of clicks besides left, right, middle
			return;

		PlayerWrapper<?> player = e.getPlayerWrapper();
		Gui gui = GuiManager.get().getCurrentGui(player);
		Slot slot = null;
		for(Slot s : gui.getSlots())
		{
			if(e.getSlot() == s.getIndex())
			{
				slot = s;
				break;
			}
		}

		if(slot == null)
			return;

		List<Function> functions = slot.getFunctions();
		List<Function> leftClickFunctions = slot.getLeftClickFunctions();
		List<Function> rightClickFunctions = slot.getRightClickFunctions();
		List<Function> middleClickFunctions = slot.getMiddleClickFunctions();

		if(functions == null && leftClickFunctions == null && rightClickFunctions == null && middleClickFunctions != null)
			return;

		Boolean close = null;
		if(slot.getClose() != null)
			close = slot.getClose();
		else if(gui.getClose() != null)
			close = gui.getClose();
		else
			close = true;

		if(close)
			player.closeInventory();

		FunctionUtil.tryFunctions(slot, e.getClick(), player);

	}
}
