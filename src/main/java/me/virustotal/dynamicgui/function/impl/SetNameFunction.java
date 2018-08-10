package me.virustotal.dynamicgui.function.impl;

import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.Function;
import me.virustotal.dynamicgui.gui.GUI;
import me.virustotal.dynamicgui.gui.Slot;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.ItemStackWrapper;
import me.virustotal.dynamicgui.util.ChatColor;

public class SetNameFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5599516930903780834L;

	public SetNameFunction(String name) 
	{
		super(name);
	}

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