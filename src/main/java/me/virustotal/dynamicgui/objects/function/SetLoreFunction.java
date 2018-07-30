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

import me.clip.placeholderapi.PlaceholderAPI;
import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.api.ReplacerAPI;
import me.virustotal.dynamicgui.gui.Slot;
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

	public boolean function(PlayerWrapper player)
	{
		Slot slot = this.getOwner();
		if(slot != null)
		{
			if(player.getOpenInventoryWrapper() != null)
			{
				InventoryView inv = player.getOpenInventoryWrapper();
				if(inv != null)
				{
					for(int i = 0; i < inv.countSlots(); i++)
					{
						ItemStack item = inv.getItem(i);
						if(item != null && item.getType() != Material.AIR)
						{
							try
							{
								NBTItem nbtItem = new NBTItem(item);
								if(nbtItem.hasKey(DynamicGUI.TAG))
								{
									UUID uuid = UUID.fromString(nbtItem.getString(DynamicGUI.TAG));

									if(slot.getUUID().equals(uuid))
									{
										ItemMeta meta = item.getItemMeta();
										List<String> lore = new ArrayList<String>();
										if(this.getData().contains(";"))
										{
											for(String str : this.getData().split(";"))
											{
												String l  = ReplacerAPI.replace(ChatColor.translateAlternateColorCodes('&', str), player);

												if(DynamicGUI.getPlaceholderAPI())
												{
													l = PlaceholderAPI.setPlaceholders(player, l);
												}
												
												lore.add(l);
											}
										}
										else
										{
											String l  = ReplacerAPI.replace(ChatColor.translateAlternateColorCodes('&', this.getData()), player);

											if(DynamicGUI.getPlaceholderAPI())
											{
												l = PlaceholderAPI.setPlaceholders(player, l);
											}
											
											lore.add(l);
										}
										meta.setLore(lore);
										item.setItemMeta(meta);
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
