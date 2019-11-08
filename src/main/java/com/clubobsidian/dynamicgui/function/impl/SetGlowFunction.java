package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.DynamicGui;
import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;

public class SetGlowFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3727112026677117024L;

	public SetGlowFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper) 
	{
		try
		{
			Boolean value = Boolean.valueOf(this.getData());
			if(value != null)
			{
				FunctionOwner owner = this.getOwner();
				if(owner != null)
				{
					if(owner instanceof Slot)
					{
						Slot slot = (Slot) owner;
						Gui gui = slot.getOwner();
						if(gui != null)
						{
							InventoryWrapper<?> inv = gui.getInventoryWrapper();
							if(inv != null)
							{
								ItemStackWrapper<?> item = slot.getItemStack();

								item.setGlowing(value);
								inv.setItem(slot.getIndex(), item);
								return true;
							}
						}
					}
				}
			}
		}
		catch(Exception ex)
		{
			DynamicGui.get().getLogger().info("Unable to parse + " + this.getData() + " as a glow");
			ex.printStackTrace();
		}
		
		return false;
	}
}