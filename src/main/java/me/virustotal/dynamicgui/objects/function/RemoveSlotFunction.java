package me.virustotal.dynamicgui.objects.function;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import me.virustotal.dynamicgui.DynamicGUI;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.gui.Slot;
import me.virustotal.dynamicgui.nbt.NBTItem;
import me.virustotal.dynamicgui.objects.Function;

public class RemoveSlotFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -88925446185236878L;

	public RemoveSlotFunction(String name) 
	{
		super(name);
	}

	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		if(this.getData().equalsIgnoreCase("this"))
		{
			Slot slot = this.getOwner();
			if(slot != null)
			{
				if(playerWrapper.getOpenInventoryWrapper().getInventory() != null)
				{
					InventoryView inv = player.getOpenInventoryWrapper();
					if(inv != null)
					{
						for(int i = 0; i < inv.countSlots(); i++)
						{
							ItemStack item = inv.getItem(i);
							if(item != null)
							{
								try
								{
									NBTItem nbtItem = new NBTItem(item);
									if(nbtItem.hasKey(DynamicGUI.TAG))
									{
										UUID uuid = UUID.fromString(nbtItem.getString(DynamicGUI.TAG));
										if(slot.getUUID().equals(uuid))
										{
											inv.setItem(i, new ItemStack(Material.AIR));
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
		}
		return true;
	}	
	
}
