package me.virustotal.dynamicgui.function.impl;

import java.util.UUID;

import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.Function;
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

	public boolean function(PlayerWrapper<?> player)
	{
		Slot slot = this.getOwner();
		if(slot != null)
		{
			if(player.getOpenInventoryWrapper() != null)
			{
				InventoryWrapper<?> inv = player.getOpenInventoryWrapper();
				if(inv != null)
				{
					for(int i = 0; i < inv.getSize(); i++)
					{
						ItemStackWrapper<?> item = inv.getItem(i);
						if(item.getItemStack() != null)
						{
							try
							{
								String tag = item.getTag();
								if(tag != null)
								{
									UUID uuid = UUID.fromString(tag);

									if(slot.getUUID().equals(uuid))
									{
										
										item.setName(ChatColor.translateAlternateColorCodes('&', this.getData()));
										inv.setItem(i, item);
										break;
									}
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
		return true;
	}	
}
