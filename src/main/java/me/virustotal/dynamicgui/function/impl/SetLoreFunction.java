package me.virustotal.dynamicgui.function.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.virustotal.dynamicgui.api.ReplacerAPI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.Function;
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

	public boolean function(PlayerWrapper<?> player)
	{
		Slot slot = this.getOwner();
		if(slot != null)
		{
			if(player.getOpenInventoryWrapper() != null)
			{
				InventoryWrapper<?> inv = player.getOpenInventoryWrapper();
				if(inv.getInventory() != null)
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
										
										List<String> lore = new ArrayList<String>();
										if(this.getData().contains(";"))
										{
											for(String str : this.getData().split(";"))
											{
												String l  = ReplacerAPI.replace(ChatColor.translateAlternateColorCodes('&', str), player);	
												
												lore.add(l);
											}
										}
										else
										{
											String l  = ReplacerAPI.replace(ChatColor.translateAlternateColorCodes('&', this.getData()), player);
											
											lore.add(l);
										}
										
										item.setLore(lore);
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
