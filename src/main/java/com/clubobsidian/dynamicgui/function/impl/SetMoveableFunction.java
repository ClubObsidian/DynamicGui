package com.clubobsidian.dynamicgui.function.impl;

import com.clubobsidian.dynamicgui.entity.PlayerWrapper;
import com.clubobsidian.dynamicgui.function.Function;
import com.clubobsidian.dynamicgui.gui.FunctionOwner;
import com.clubobsidian.dynamicgui.gui.Gui;
import com.clubobsidian.dynamicgui.gui.Slot;
import com.clubobsidian.dynamicgui.inventory.InventoryWrapper;
import com.clubobsidian.dynamicgui.inventory.ItemStackWrapper;

public class SetMoveableFunction extends Function {

	/**
	 * 
	 */
	private static final long serialVersionUID = 453447798953153174L;

	public SetMoveableFunction(String name) 
	{
		super(name);
	}

	@Override
	public boolean function(PlayerWrapper<?> playerWrapper) 
	{
		if(this.getOwner() instanceof Slot)
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
							slot.setMoveable(value);
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
}