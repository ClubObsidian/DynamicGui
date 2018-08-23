package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.DynamicGUI;
import com.clubobsidian.dynamicgui.api.GuiApi;
import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.GUI;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;

public class SetTypeFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6943230273788425141L;

	public SetTypeFunction(String name) 
	{
		super(name);
	}
	
	@Override
	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		DynamicGUI.get().getLogger().info("Set type function was ran");
		Slot slot = this.getOwner();
		if(slot != null)
		{
			if(playerWrapper.getOpenInventoryWrapper() != null)
			{
				InventoryWrapper<?> inv = playerWrapper.getOpenInventoryWrapper();
				GUI gui = GuiApi.getCurrentGUI(playerWrapper);
				if(inv != null && gui != null)
				{
					for(Slot s : gui.getSlots())
					{
						ItemStackWrapper<?> item = inv.getItem(s.getIndex());
						if(item.getItemStack() != null)
						{
							if(this.getOwner().getIndex() == s.getIndex())
							{
								item.setType(this.getData());
								inv.setItem(this.getOwner().getIndex(), item);
								DynamicGUI.get().getLogger().info("From set type function, itemstack is: " + item.getItemStack());
								break;
							}
						}
					}
				}
			}
		}
		return true;
	}	
}