package com.clubobsidian.dynamicgui.function.impl;

import java.util.ArrayList;
import java.util.List;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
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
