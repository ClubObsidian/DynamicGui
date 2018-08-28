package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
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
						Gui gui = GuiManager.get().getCurrentGUI(playerWrapper);
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
