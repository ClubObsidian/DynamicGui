package me.virustotal.dynamicgui.objects.function;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.ReplacerAPI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.gui.Slot;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.item.ItemStackWrapper;
import me.virustotal.dynamicgui.nbt.NBTItem;
import me.virustotal.dynamicgui.objects.Function;

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
								NBTItem nbtItem = new NBTItem(item);
								if(nbtItem.hasKey(DynamicGUI.TAG))
								{
									UUID uuid = UUID.fromString(nbtItem.getString(DynamicGUI.TAG));

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
