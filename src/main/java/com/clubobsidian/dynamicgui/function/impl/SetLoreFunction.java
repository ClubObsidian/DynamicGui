package com.clubobsidian.dynamicgui.function.impl;

import java.util.ArrayList;
import java.util.List;

import com.clubobsidian.dynamicgui.api.GuiApi;
import com.clubobsidian.dynamicgui.api.ReplacerAPI;
import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.GUI;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
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

	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		if(playerWrapper.getOpenInventoryWrapper() != null)
		{
			InventoryWrapper<?> inv = playerWrapper.getOpenInventoryWrapper();
			GUI gui = GuiApi.getCurrentGUI(playerWrapper);
			if(inv.getInventory() != null)
			{
				for(Slot s : gui.getSlots())
				{
					ItemStackWrapper<?> item = inv.getItem(s.getIndex());
					if(item.getItemStack() != null)
					{
						if(this.getOwner().getIndex() == s.getIndex())
						{

							List<String> lore = new ArrayList<String>();
							if(this.getData().contains(";"))
							{
								for(String str : this.getData().split(";"))
								{
									String l  = ReplacerAPI.replace(ChatColor.translateAlternateColorCodes('&', str), playerWrapper);	

									lore.add(l);
								}
							}
							else
							{
								String l  = ReplacerAPI.replace(ChatColor.translateAlternateColorCodes('&', this.getData()), playerWrapper);

								lore.add(l);
							}

							item.setLore(lore);
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
