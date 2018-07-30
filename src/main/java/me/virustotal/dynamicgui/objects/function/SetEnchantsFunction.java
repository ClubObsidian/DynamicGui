package me.virustotal.dynamicgui.objects.function;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.gui.Slot;
import me.virustotal.dynamicgui.nbt.NBTItem;
import me.virustotal.dynamicgui.objects.Function;

public class SetEnchantsFunction extends Function {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8291956007296368761L;

	public SetEnchantsFunction(String name) 
	{
		super(name);
	}

	public boolean function(PlayerWrapper player)
	{
		Slot slot = this.getOwner();
		if(slot != null)
		{
			if(player.getOpenInventory() != null)
			{
				InventoryView inv = player.getOpenInventory();
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
										HashMap<String, Integer> enchants = new HashMap<String, Integer>();
										if(this.getData().contains(";"))
										{
											for(String str : this.getData().split(";"))
											{
												String[] split = str.split(",");
												enchants.put(split[0], Integer.valueOf(split[1]));
											}
										}
										else
										{
											String[] split = this.getData().split(",");
											enchants.put(split[0], Integer.valueOf(split[1]));
										}
										ItemMeta meta = item.getItemMeta();
										for(Enchantment ench : meta.getEnchants().keySet())
										{
											meta.removeEnchant(ench);
										}
										for(String str : enchants.keySet())
										{
											meta.addEnchant(Enchantment.getByName(str), enchants.get(str), true);
										}
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
