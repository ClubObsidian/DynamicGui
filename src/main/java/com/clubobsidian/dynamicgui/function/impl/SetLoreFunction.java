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
package com.clubobsidian.dynamicgui.function.impl;

import java.util.ArrayList;
import java.util.List;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.manager.dynamicgui.ReplacerManager;
import com.clubobsidian.dynamicgui.util.ChatColor;

public class SetLoreFunction extends Function {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6723628078978301156L;

	public SetLoreFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		FunctionOwner owner = this.getOwner();
		if(owner != null)
		{
			if(owner instanceof Slot)
			{
				Slot slot = (Slot) owner;
				if(playerWrapper.getOpenInventoryWrapper() != null)
				{
					InventoryWrapper<?> inv = playerWrapper.getOpenInventoryWrapper();
					Gui gui = GuiManager.get().getCurrentGui(playerWrapper);
					if(inv.getInventory() != null)
					{
						for(Slot s : gui.getSlots())
						{
							ItemStackWrapper<?> item = inv.getItem(s.getIndex());
							if(item.getItemStack() != null)
							{
								if(slot.getIndex() == s.getIndex())
								{

									List<String> lore = new ArrayList<String>();
									if(this.getData().contains(";"))
									{
										for(String str : this.getData().split(";"))
										{
											String l  = ReplacerManager.get().replace(ChatColor.translateAlternateColorCodes('&', str), playerWrapper);	

											lore.add(l);
										}
									}
									else
									{
										String l  = ReplacerManager.get().replace(ChatColor.translateAlternateColorCodes('&', this.getData()), playerWrapper);

										lore.add(l);
									}

									item.setLore(lore);
									inv.setItem(slot.getIndex(), item);
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
}