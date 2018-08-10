package me.virustotal.dynamicgui.function.impl;

import java.util.ArrayList;
import java.util.List;

import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.api.ReplacerAPI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.Function;
import me.virustotal.dynamicgui.gui.GUI;
import me.virustotal.dynamicgui.gui.Slot;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.ItemStackWrapper;
import me.virustotal.dynamicgui.util.ChatColor;

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
