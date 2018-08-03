package me.virustotal.dynamicgui.objects.function;

import java.util.HashMap;
import java.util.Map;
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
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.item.ItemStackWrapper;
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
								String tag = item.getString(DynamicGUI.TAG);
								if(tag != null)
								{
									UUID uuid = UUID.fromString(tag);

									if(slot.getUUID().equals(uuid))
									{
										Map<String, Integer> enchants = new HashMap<String, Integer>();
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
