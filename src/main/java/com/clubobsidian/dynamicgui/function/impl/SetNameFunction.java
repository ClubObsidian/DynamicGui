/*
 *    Copyright 2021 Club Obsidian and contributors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.manager.dynamicgui.AnimationReplacerManager;
import com.clubobsidian.dynamicgui.manager.dynamicgui.ReplacerManager;
import com.clubobsidian.dynamicgui.util.ChatColor;

public class SetNameFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5599516930903780834L;

	public SetNameFunction(String name) 
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
				Gui gui = slot.getOwner();
				if(gui != null)
				{
					InventoryWrapper<?> inv = gui.getInventoryWrapper();
					if(inv != null)
					{
						ItemStackWrapper<?> item = slot.getItemStack();
						String newName = ChatColor.translateAlternateColorCodes('&', this.getData());
						newName = ReplacerManager.get().replace(newName, playerWrapper);
						newName = AnimationReplacerManager.get().replace(slot, playerWrapper, newName);
						item.setName(newName);
						inv.setItem(slot.getIndex(), item);
						return true;
					}
				}
			}
		}
		return false;
	}
}