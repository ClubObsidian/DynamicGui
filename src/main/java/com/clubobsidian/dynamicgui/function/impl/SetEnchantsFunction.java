package com.clubobsidian.dynamicgui.function.impl;

import java.util.HashMap;
import java.util.Map;

import com.clubobsidian.dynamicgui.entity.player.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Gui2;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;
import com.clubobsidian.dynamicgui.manager.dynamicgui.GuiManager;
import com.clubobsidian.dynamicgui.objects.EnchantmentWrapper;

public class SetEnchantsFunction extends Function {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8291956007296368761L;

	public SetEnchantsFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper)
	{
		FunctionOwner owner = this.getOwner();
		if(owner != null)
		{
			if(owner instanceof Slot)
			{
				Slot slot = (Slot) owner;
				if(playerWrapper.getOpenInventoryWrapper() != null)
				{
					InventoryWrapper<?> inv = playerWrapper.getOpenInventoryWrapper();
					Gui2 gui = GuiManager.get().getCurrentGui(playerWrapper);
					if(inv != null)
					{
						for(Slot s : gui.getSlots())
						{
							ItemStackWrapper<?> item = inv.getItem(s.getIndex());
							if(item.getItemStack() != null)
							{
								if(slot.getIndex() == s.getIndex())
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

									inv.setItem(slot.getIndex(), item);
									return true;
								}

							}
						}
					}
				}
			}
		}
		return false;
	}	
}