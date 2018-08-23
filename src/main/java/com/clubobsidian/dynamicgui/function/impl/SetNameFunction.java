package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.api.GuiApi;
import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.GUI;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
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
		if(playerWrapper.getOpenInventoryWrapper() != null)
		{
			InventoryWrapper<?> inv = playerWrapper.getOpenInventoryWrapper();
			GUI gui = GuiApi.getCurrentGUI(playerWrapper);
			if(inv != null)
			{
				for(Slot s : gui.getSlots())
				{
					ItemStackWrapper<?> item = inv.getItem(s.getIndex());
					if(item.getItemStack() != null)
					{
						if(this.getOwner().getIndex() == s.getIndex())
						{

							item.setName(ChatColor.translateAlternateColorCodes('&', this.getData()));
							inv.setItem(this.getOwner().getIndex(), item);
							break;
						}
					}
				}
			}
		}
		return true;
	}
}