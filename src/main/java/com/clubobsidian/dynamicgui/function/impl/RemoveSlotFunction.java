/*   Copyright 2018 Club Obsidian and contributors.

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

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.manager.inventory.ItemStackManager;

public class RemoveSlotFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -88925446185236878L;

	public RemoveSlotFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		if(this.getData().equalsIgnoreCase("this"))
		{
			FunctionOwner owner = this.getOwner();
			if(owner != null)
			{
				if(owner instanceof Slot)
				{
					Slot slot = (Slot) owner;
					if(playerWrapper.getOpenInventoryWrapper().getInventory() != null)
					{
						InventoryWrapper<?> inv = playerWrapper.getOpenInventoryWrapper();
						Gui gui = GuiManager.get().getCurrentGui(playerWrapper);
						if(inv != null && gui != null)
						{
							for(Slot s : gui.getSlots())
							{
								ItemStackWrapper<?> item = s.getItemStack();
								if(item.getItemStack() != null)
								{
									if(slot.getIndex() == s.getIndex())
									{
										inv.setItem(slot.getIndex(), ItemStackManager.get().createItemStackWrapper("AIR", 1));
										return true;
									}
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
