package me.virustotal.dynamicgui.function.impl;

import java.util.HashMap;
import java.util.Map;

import me.virustotal.dynamicgui.api.GuiApi;
import me.virustotal.dynamicgui.entity.player.PlayerWrapper;
import me.virustotal.dynamicgui.function.Function;
import me.virustotal.dynamicgui.gui.GUI;
import me.virustotal.dynamicgui.gui.Slot;
import me.virustotal.dynamicgui.inventory.InventoryWrapper;
import me.virustotal.dynamicgui.inventory.ItemStackWrapper;
import me.virustotal.dynamicgui.objects.EnchantmentWrapper;

public class SetEnchantsFunction extends Function {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8291956007296368761L;

	public SetEnchantsFunction(String name) 
	{
		super(name);
	}

	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		Slot slot = this.getOwner();
		if(slot != null)
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

								for(EnchantmentWrapper ench : item.getEnchants())
								{
									item.removeEnchant(ench);
								}

								for(String str : enchants.keySet())
								{
									item.addEnchant(new EnchantmentWrapper(str, enchants.get(str)));
								}

								inv.setItem(this.getOwner().getIndex(), item);
								break;
							}

						}
					}
				}
			}
		}
		return true;
	}	
}
