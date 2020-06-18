/*
   Copyright 2020 Club Obsidian and contributors.

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
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.InventoryView;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.parser.function.FunctionType;
import com.clubobsidian.dynamicgui.parser.function.tree.FunctionNode;
import com.clubobsidian.dynamicgui.util.FunctionUtil;

import com.clubobsidian.trident.EventHandler;

public class InventoryClickListener {

	@EventHandler
	public void invClick(final InventoryClickEvent e)
	{
		if(e.getPlayerWrapper().getOpenInventoryWrapper() == null)
		{
			return;
		}
		else if(!GuiManager.get().hasGuiCurrently(e.getPlayerWrapper()))
		{
			return;
		}
		
		
		ItemStackWrapper<?> item = e.getItemStackWrapper();
		if(item.getItemStack() == null)
		{
			return;
		}
		else if(e.getView() != InventoryView.TOP)
		{
			return;
		}
		else if(e.getClick() == null) //For other types of clicks besides left, right, middle
		{
			return;
		}

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
		{
			return;
		}
		
		if(!slot.isMoveable())
		{
			e.setCanceled(true);
		}

		List<FunctionNode> functions = slot.getFunctions().getRootNodes();

		if(functions.size() == 0)
		{
			return;
		}

		String clickString = e.getClick().toString();
		FunctionUtil.tryFunctions(slot, FunctionType.valueOf(clickString), player);
		
		Boolean close = null;
		if(slot.getClose() != null)
		{
			close = slot.getClose();
		}
		else if(gui.getClose() != null)
		{
			close = gui.getClose();
		}
		else
		{
			close = true;
		}

		if(close)
		{
			player.closeInventory();
		}
	}
}