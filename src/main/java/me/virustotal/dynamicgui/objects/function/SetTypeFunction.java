package me.virustotal.dynamicgui.objects.function;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.gui.Slot;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.item.ItemStackWrapper;
import me.virustotal.dynamicgui.nbt.NBTItem;
import me.virustotal.dynamicgui.objects.Function;

public class SetTypeFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6943230273788425141L;

	public SetTypeFunction(String name) 
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
								NBTItem nbtItem = new NBTItem(item);
								if(nbtItem.hasKey(DynamicGUI.TAG))
								{
									UUID uuid = UUID.fromString(nbtItem.getString(DynamicGUI.TAG));
									if(slot.getUUID().equals(uuid))
									{
										item.setType(this.getData());
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
