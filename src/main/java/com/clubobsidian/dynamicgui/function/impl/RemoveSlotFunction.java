package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.api.GuiApi;
import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.GUI;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
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

	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		if(this.getData().equalsIgnoreCase("this"))
		{
			Slot slot = this.getOwner();
			if(slot != null)
			{
				if(playerWrapper.getOpenInventoryWrapper().getInventory() != null)
				{
					InventoryWrapper<?> inv = playerWrapper.getOpenInventoryWrapper();
					GUI gui = GuiApi.getCurrentGUI(playerWrapper);
					if(inv != null && gui != null)
					{
						for(Slot s : gui.getSlots())
						{
							ItemStackWrapper<?> item = s.getItemStack();
							if(item.getItemStack() != null)
							{
								try
								{
									if(this.getOwner().getIndex() == s.getIndex())
									{
										inv.setItem(this.getOwner().getIndex(), ItemStackManager.get().createItemStackWrapper("AIR", 1));
										break;
									}
								}
								catch(SecurityException | IllegalArgumentException ex)
								{
									ex.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
		return true;
	}	
}
